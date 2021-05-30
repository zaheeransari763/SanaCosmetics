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

public class Haircaremaybelline extends AppCompatActivity {
    public EditText proteinQtyText,spaQtyText,browQtyText,hairQtyText;

    public int proteinsingleprice= 705,proteinfinalprice,proteinprice;
    public int spasingleprice= 1090,spafinalprice,spaprice;
    public int browsingleprice= 945,browfinalprice,browprice;
    public int hairsingleprice= 1270,hairfinalprice,hairprice;

    Button buyProtein,buySpa,buyBrow,buyHair;

    DatabaseReference usersRefer, orderRef;
    FirebaseAuth mAuth;

    public String phone,address;
    public String proteinpricestring,productnameprotein;
    public String spapricestring,productnamespa;
    public String browpricestring,productnamebrow;
    public String hairpricestring,productnamehair;

    String proteinQtystr,spaQtystr,browQtystr,hairQtystr;
    String currentUerId, saveCurrentUserTime, saveCurrentUserDate, buyersRandomKeyGen;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_haircaremaybelline);

        mAuth = FirebaseAuth.getInstance();
        currentUerId = mAuth.getCurrentUser().getUid();

        usersRefer = FirebaseDatabase.getInstance().getReference().child("Clients").child(currentUerId);
        orderRef = FirebaseDatabase.getInstance().getReference().child("Order").child(currentUerId);

        proteinQtyText = findViewById(R.id.proteinQty);
        spaQtyText=findViewById(R.id.spaQty);
        browQtyText=findViewById(R.id.browQty);
        hairQtyText=findViewById(R.id.hairQty);


        buyHair=findViewById(R.id.buyHair);
        buyHair.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buyHair();
            }
        });
        buyBrow=findViewById(R.id.buyBrow);
        buyBrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buyBrow();
            }
        });
        buySpa=findViewById(R.id.buySpa);
        buySpa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buySpa();
            }
        });
        buyProtein=findViewById(R.id.buyProtein);
        buyProtein.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                proteinQtystr = proteinQtyText.getText().toString();
                proteinprice=Integer.parseInt(proteinQtystr);
                proteinfinalprice=proteinprice*proteinsingleprice;
                proteinpricestring=String.valueOf(proteinfinalprice);
                productnameprotein="Natural Protein Hair Serum";

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
                orderMap.put("OrderQty",proteinQtystr);
                orderMap.put("Price",proteinpricestring);
                orderMap.put("Item Name",productnameprotein);
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
                            Toast.makeText(Haircaremaybelline.this, "Order placed!", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

            }
        });

    }

    private void buyHair() {
        hairQtystr = hairQtyText.getText().toString();
        hairprice=Integer.parseInt(hairQtystr);
        hairfinalprice=hairprice*hairsingleprice;
        hairpricestring=String.valueOf(hairfinalprice);
        productnamehair="Hair foundation";

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
        orderMap.put("OrderQty",hairQtystr);
        orderMap.put("Price",hairpricestring);
        orderMap.put("Item Name",productnamehair);
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
                    Toast.makeText(Haircaremaybelline.this, "Order placed!", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    private void buyBrow() {
        browQtystr = browQtyText.getText().toString();
        browprice=Integer.parseInt(browQtystr);
        browfinalprice=browprice*browsingleprice;
        browpricestring=String.valueOf(browfinalprice);
        productnamebrow="Brow styling gel";

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
        orderMap.put("OrderQty",browQtystr);
        orderMap.put("Price",browpricestring);
        orderMap.put("Item Name",productnamebrow);
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
                    Toast.makeText(Haircaremaybelline.this, "Order placed!", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    private void buySpa() {
        spaQtystr = spaQtyText.getText().toString();
        spaprice=Integer.parseInt(spaQtystr);
        spafinalprice=spaprice*spasingleprice;
        spapricestring=String.valueOf(spafinalprice);
        productnamespa="Spa Hair Conditioning Serum";

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
        orderMap.put("OrderQty",spaQtystr);
        orderMap.put("Price",spapricestring);
        orderMap.put("Item Name",productnamespa);
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
                    Toast.makeText(Haircaremaybelline.this, "Order placed!", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
}
