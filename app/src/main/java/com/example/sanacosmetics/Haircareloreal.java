package com.example.sanacosmetics;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

public class Haircareloreal extends AppCompatActivity {
    public EditText instantQtyText,goldenQtyText,blowQtyText,sprayQtyText;

    public int instantsingleprice= 1720,instantfinalprice,instantprice;
    public int goldensingleprice= 1190,goldenfinalprice,goldenprice;
    public int blowsingleprice= 645,blowfinalprice,blowprice;
    public int spraysingleprice= 1170,sprayfinalprice,sprayprice;

    Button buyInstant,buyGolden,buyBlow,buySpray;

    DatabaseReference usersRefer, orderRef;
    FirebaseAuth mAuth;

    public String phone,address;
    public String instantpricestring,productnameinstant;
    public String goldenpricestring,productnamegolden;
    public String blowpricestring,productnameblow;
    public String spraypricestring,productnamespray;

    String instantQtystr,goldenQtystr,blowQtystr,sprayQtystr;
    String currentUerId, saveCurrentUserTime, saveCurrentUserDate, buyersRandomKeyGen;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_haircareloreal);
        mAuth = FirebaseAuth.getInstance();
        currentUerId = mAuth.getCurrentUser().getUid();

        usersRefer = FirebaseDatabase.getInstance().getReference().child("Clients").child(currentUerId);
        orderRef = FirebaseDatabase.getInstance().getReference().child("Order").child(currentUerId);

        instantQtyText = findViewById(R.id.instantQty);
        goldenQtyText=findViewById(R.id.goldenQty);
        blowQtyText=findViewById(R.id.blowQty);
        sprayQtyText=findViewById(R.id.sprayQty);





        buySpray=findViewById(R.id.buySpray);
        buySpray.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buySpray();
            }
        });
        buyBlow=findViewById(R.id.buyBlow);
        buyBlow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buyBlow();
            }
        });
        buyGolden=findViewById(R.id.buyGolden);
        buyGolden.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buyGolden();
            }
        });
        buyInstant=findViewById(R.id.buyInstant);
        buyInstant.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                instantQtystr = instantQtyText.getText().toString();
                instantprice=Integer.parseInt(instantQtystr);
                instantfinalprice=instantprice*instantsingleprice;
                instantpricestring=String.valueOf(instantfinalprice);
                productnameinstant="Instant Resurfacing Conditioner";

                usersRefer.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if (dataSnapshot.exists())
                        {
                            phone = dataSnapshot.child("Contact").getValue().toString();
                            address = dataSnapshot.child("Address").getValue().toString();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

                Calendar calendar = Calendar.getInstance();
                SimpleDateFormat currentDate = new SimpleDateFormat("MMM dd, yyyy");
                saveCurrentUserDate = currentDate.format(calendar.getTime());

                SimpleDateFormat currentTime = new SimpleDateFormat("HH:mm:ss a");
                saveCurrentUserTime = currentTime.format(calendar.getTime());

                buyersRandomKeyGen = saveCurrentUserDate + saveCurrentUserTime;

                HashMap<String, Object> orderMap = new HashMap<>();
                orderMap.put("Contact",phone);
                orderMap.put("Address",address);
                orderMap.put("OrderQty",instantQtystr);
                orderMap.put("Price",instantpricestring);
                orderMap.put("Item Name",productnameinstant);
                orderMap.put("time",saveCurrentUserTime);
                orderMap.put("date",saveCurrentUserDate);
                orderMap.put("buyerOrderId",buyersRandomKeyGen);
                orderRef.child(buyersRandomKeyGen).updateChildren(orderMap).addOnCompleteListener(new OnCompleteListener<Void>()
                {
                    @Override
                    public void onComplete(@NonNull Task<Void> task)
                    {
                        if (task.isSuccessful())
                        {
                            Toast.makeText(Haircareloreal.this, "Order placed!", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

            }
        });
    }

    private void buySpray() {
        sprayQtystr = sprayQtyText.getText().toString();
        sprayprice=Integer.parseInt(sprayQtystr);
        sprayfinalprice=sprayprice*spraysingleprice;
        spraypricestring=String.valueOf(sprayfinalprice);
        productnamespray="10 1 Perfecting Multipurpose Spray";

        usersRefer.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists())
                {
                    phone = dataSnapshot.child("Contact").getValue().toString();
                    address = dataSnapshot.child("Address").getValue().toString();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat currentDate = new SimpleDateFormat("MMM dd, yyyy");
        saveCurrentUserDate = currentDate.format(calendar.getTime());

        SimpleDateFormat currentTime = new SimpleDateFormat("HH:mm:ss a");
        saveCurrentUserTime = currentTime.format(calendar.getTime());

        buyersRandomKeyGen = saveCurrentUserDate + saveCurrentUserTime;

        HashMap<String, Object> orderMap = new HashMap<>();
        orderMap.put("Contact",phone);
        orderMap.put("Address",address);
        orderMap.put("OrderQty",sprayQtystr);
        orderMap.put("Price",spraypricestring);
        orderMap.put("Item Name",productnamespray);
        orderMap.put("time",saveCurrentUserTime);
        orderMap.put("date",saveCurrentUserDate);
        orderMap.put("buyerOrderId",buyersRandomKeyGen);
        orderRef.child(buyersRandomKeyGen).updateChildren(orderMap).addOnCompleteListener(new OnCompleteListener<Void>()
        {
            @Override
            public void onComplete(@NonNull Task<Void> task)
            {
                if (task.isSuccessful())
                {
                    Toast.makeText(Haircareloreal.this, "Order placed!", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    private void buyBlow() {
        blowQtystr = blowQtyText.getText().toString();
        blowprice=Integer.parseInt(blowQtystr);
        blowfinalprice=blowprice*blowsingleprice;
        blowpricestring=String.valueOf(blowfinalprice);
        productnameblow="Blow-dry Cream";

        usersRefer.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists())
                {
                    phone = dataSnapshot.child("Contact").getValue().toString();
                    address = dataSnapshot.child("Address").getValue().toString();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat currentDate = new SimpleDateFormat("MMM dd, yyyy");
        saveCurrentUserDate = currentDate.format(calendar.getTime());

        SimpleDateFormat currentTime = new SimpleDateFormat("HH:mm:ss a");
        saveCurrentUserTime = currentTime.format(calendar.getTime());

        buyersRandomKeyGen = saveCurrentUserDate + saveCurrentUserTime;

        HashMap<String, Object> orderMap = new HashMap<>();
        orderMap.put("Contact",phone);
        orderMap.put("Address",address);
        orderMap.put("OrderQty",blowQtystr);
        orderMap.put("Price",blowpricestring);
        orderMap.put("Item Name",productnameblow);
        orderMap.put("time",saveCurrentUserTime);
        orderMap.put("date",saveCurrentUserDate);
        orderMap.put("buyerOrderId",buyersRandomKeyGen);
        orderRef.child(buyersRandomKeyGen).updateChildren(orderMap).addOnCompleteListener(new OnCompleteListener<Void>()
        {
            @Override
            public void onComplete(@NonNull Task<Void> task)
            {
                if (task.isSuccessful())
                {
                    Toast.makeText(Haircareloreal.this, "Order placed!", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    private void buyGolden() {
        goldenQtystr = goldenQtyText.getText().toString();
        goldenprice=Integer.parseInt(goldenQtystr);
        goldenfinalprice=goldenprice*goldensingleprice;
        goldenpricestring=String.valueOf(goldenfinalprice);
        productnamegolden="Resurfacing Golden Masque";

        usersRefer.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists())
                {
                    phone = dataSnapshot.child("Contact").getValue().toString();
                    address = dataSnapshot.child("Address").getValue().toString();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat currentDate = new SimpleDateFormat("MMM dd, yyyy");
        saveCurrentUserDate = currentDate.format(calendar.getTime());

        SimpleDateFormat currentTime = new SimpleDateFormat("HH:mm:ss a");
        saveCurrentUserTime = currentTime.format(calendar.getTime());

        buyersRandomKeyGen = saveCurrentUserDate + saveCurrentUserTime;

        HashMap<String, Object> orderMap = new HashMap<>();
        orderMap.put("Contact",phone);
        orderMap.put("Address",address);
        orderMap.put("OrderQty",goldenQtystr);
        orderMap.put("Price",goldenpricestring);
        orderMap.put("Item Name",productnamegolden);
        orderMap.put("time",saveCurrentUserTime);
        orderMap.put("date",saveCurrentUserDate);
        orderMap.put("buyerOrderId",buyersRandomKeyGen);
        orderRef.child(buyersRandomKeyGen).updateChildren(orderMap).addOnCompleteListener(new OnCompleteListener<Void>()
        {
            @Override
            public void onComplete(@NonNull Task<Void> task)
            {
                if (task.isSuccessful())
                {
                    Toast.makeText(Haircareloreal.this, "Order placed!", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
}
