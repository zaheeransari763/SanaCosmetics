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

public class Skincareloreal extends AppCompatActivity {
    public EditText ageQtyText,tripleQtyText,antiQtyText,clayQtyText,lzrQtyText;

    public int agesingleprice= 300,agefinalprice,ageprice;
    public int triplesingleprice= 1100,triplefinalprice,tripleprice;
    public int antisingleprice= 650,antifinalprice,antiprice;
    public int claysingleprice= 6230,clayfinalprice,clayprice;
    public int lzrsingleprice= 4200,lzrfinalprice,lzrprice;

    Button buyAge,buyTriple,buyAnti,buyClay,buyLzr;

    DatabaseReference usersRefer, orderRef;
    FirebaseAuth mAuth;

    public String phone,address;
    public String agepricestring,productnameage;
    public String triplepricestring,productnametriple;
    public String antipricestring,productnameanti;
    public String claypricestring,productnameclay;
    public String lzrpricestring,productnamelzr;


    String ageQtystr,tripleQtystr,antiQtystr,clayQtystr,lzrQtystr;
    String currentUerId, saveCurrentUserTime, saveCurrentUserDate, buyersRandomKeyGen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_skincareloreal);

        mAuth = FirebaseAuth.getInstance();
        currentUerId = mAuth.getCurrentUser().getUid();

        usersRefer = FirebaseDatabase.getInstance().getReference().child("Clients").child(currentUerId);
        orderRef = FirebaseDatabase.getInstance().getReference().child("Order").child(currentUerId);

        ageQtyText = findViewById(R.id.ageQty);
        tripleQtyText=findViewById(R.id.tripleQty);
        antiQtyText=findViewById(R.id.antiQty);
        clayQtyText=findViewById(R.id.clayQty);
        lzrQtyText=findViewById(R.id.lzrQty);


        buyLzr=findViewById(R.id.buyLzr);
        buyLzr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buyLzr();
            }
        });
        buyClay=findViewById(R.id.buyClay);
        buyClay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buyClay();
            }
        });
        buyAnti=findViewById(R.id.buyAnti);
        buyAnti.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buyAnti();
            }
        });
        buyTriple=findViewById(R.id.buyTriple);
        buyTriple.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buyTriple();
            }
        });
        buyAge=findViewById(R.id.buyAge);
        buyAge.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ageQtystr = ageQtyText.getText().toString();
                ageprice=Integer.parseInt(ageQtystr);
                agefinalprice=ageprice*agesingleprice;
                agepricestring=String.valueOf(agefinalprice);
                productnameage="Age perfect anti-sagging anti-age";

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
                orderMap.put("OrderQty",ageQtystr);
                orderMap.put("Price",agepricestring);
                orderMap.put("Item Name",productnameage);
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
                            Toast.makeText(Skincareloreal.this, "Order placed!", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

            }
        });
    }

    private void buyLzr() {
        lzrQtystr = lzrQtyText.getText().toString();
        lzrprice=Integer.parseInt(lzrQtystr);
        lzrfinalprice=lzrprice*lzrsingleprice;
        lzrpricestring=String.valueOf(lzrfinalprice);
        productnamelzr="Triple power lzr day moisturizer";

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
        orderMap.put("OrderQty",lzrQtystr);
        orderMap.put("Price",lzrpricestring);
        orderMap.put("Item Name",productnamelzr);
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
                    Toast.makeText(Skincareloreal.this, "Order placed!", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    private void buyClay() {
        clayQtystr = clayQtyText.getText().toString();
        clayprice=Integer.parseInt(clayQtystr);
        clayfinalprice=clayprice*claysingleprice;
        claypricestring=String.valueOf(clayfinalprice);
        productnameclay="Pure-clay cleansing mask for skin";

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
        orderMap.put("OrderQty",clayQtystr);
        orderMap.put("Price",claypricestring);
        orderMap.put("Item Name",productnameclay);
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
                    Toast.makeText(Skincareloreal.this, "Order placed!", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    private void buyAnti() {
        antiQtystr = antiQtyText.getText().toString();
        antiprice=Integer.parseInt(antiQtystr);
        antifinalprice=antiprice*antisingleprice;
        antipricestring=String.valueOf(antifinalprice);
        productnameanti="Age perfect anti-fatigue toner";

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
        orderMap.put("OrderQty",antiQtystr);
        orderMap.put("Price",antipricestring);
        orderMap.put("Item Name",productnameanti);
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
                    Toast.makeText(Skincareloreal.this, "Order placed!", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    private void buyTriple() {
        tripleQtystr = tripleQtyText.getText().toString();
        tripleprice=Integer.parseInt(tripleQtystr);
        triplefinalprice=tripleprice*triplesingleprice;
        triplepricestring=String.valueOf(triplefinalprice);
        productnametriple="Revitalift triple power lzr";

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
        orderMap.put("OrderQty",tripleQtystr);
        orderMap.put("Price",triplepricestring);
        orderMap.put("Item Name",productnametriple);
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
                    Toast.makeText(Skincareloreal.this, "Order placed!", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
}
