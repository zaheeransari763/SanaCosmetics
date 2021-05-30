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

public class Suncareestee extends AppCompatActivity {
    public EditText bronzeQtyText,spfQtyText,perfectQtyText;


    public int bronzesingleprice= 9000,bronzefinalprice,bronzeprice;
    public int spfsingleprice= 1000,spffinalprice,spfprice;
    public int perfectsingleprice= 950,perfectfinalprice,perfectprice;

    Button buyBronze,buySpf,buyPerfect;

    DatabaseReference usersRefer, orderRef;
    FirebaseAuth mAuth;

    public String phone,address;
    public String bronzepricestring,productnamebronze;
    public String spfpricestring,productnamespf;
    public String perfectpricestring,productnameperfect;

    String bronzeQtystr,spfQtystr,perfectQtystr;
    String currentUerId, saveCurrentUserTime, saveCurrentUserDate, buyersRandomKeyGen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_suncareestee);

        mAuth = FirebaseAuth.getInstance();
        currentUerId = mAuth.getCurrentUser().getUid();

        usersRefer = FirebaseDatabase.getInstance().getReference().child("Clients").child(currentUerId);
        orderRef = FirebaseDatabase.getInstance().getReference().child("Order").child(currentUerId);

        bronzeQtyText = findViewById(R.id.bronzeQty);
        spfQtyText=findViewById(R.id.spfQty);
        perfectQtyText=findViewById(R.id.perfectQty);


        buyPerfect=findViewById(R.id.buyPerfect);
        buyPerfect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buyPerfect();
            }
        });
        buySpf=findViewById(R.id.buySpf);
        buySpf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buySpf();
            }
        });
        buyBronze=findViewById(R.id.buyBronze);
        buyBronze.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bronzeQtystr = bronzeQtyText.getText().toString();
                bronzeprice=Integer.parseInt(bronzeQtystr);
                bronzefinalprice=bronzeprice*bronzesingleprice;
                bronzepricestring=String.valueOf(bronzefinalprice);
                productnamebronze="Bronze Goddess collection summer";

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
                orderMap.put("OrderQty",bronzeQtystr);
                orderMap.put("Price",bronzepricestring);
                orderMap.put("Item Name",productnamebronze);
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
                            Toast.makeText(Suncareestee.this, "Order placed!", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

            }
        });
    }

    private void buyPerfect() {
        perfectQtystr = perfectQtyText.getText().toString();
        perfectprice=Integer.parseInt(perfectQtystr);
        perfectfinalprice=perfectprice*perfectsingleprice;
        perfectpricestring=String.valueOf(perfectfinalprice);
        productnameperfect="Age perfect anti-fatigue toner";

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
        orderMap.put("OrderQty",perfectQtystr);
        orderMap.put("Price",perfectpricestring);
        orderMap.put("Item Name",productnameperfect);
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
                    Toast.makeText(Suncareestee.this, "Order placed!", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }


    private void buySpf() {
        spfQtystr = spfQtyText.getText().toString();
        spfprice=Integer.parseInt(spfQtystr);
        spffinalprice=spfprice*spfsingleprice;
        spfpricestring=String.valueOf(spffinalprice);
        productnamespf="SPF suncream";

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
        orderMap.put("OrderQty",spfQtystr);
        orderMap.put("Price",spfpricestring);
        orderMap.put("Item Name",productnamespf);
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
                    Toast.makeText(Suncareestee.this, "Order placed!", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
}
