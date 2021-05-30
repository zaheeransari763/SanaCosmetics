package com.example.sanacosmetics;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
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

public class Makeupesteelauder extends AppCompatActivity
{

   public EditText lauderQtyText,doubleQtyText,lipstickQtyText,wrinkleQtyText,camouflageQtyText,modernQtyText;
    public int daywearantioxidantsingleprice= 800,Daywearantioxidantfinalprice,Daywearantioxidantprice;
    public int doublewaresingleprice= 1200,doublewarefinalprice,doublewareprice;
    public int lipsticksingleprice= 1900,lipstickfinalprice,lipstickprice;
    public int wrinklesingleprice= 7000,wrinklefinalprice,wrinkleprice;
    public int camouflagesingleprice= 3300,camouflagefinalprice,camouflageprice;
    public int modernsingleprice= 5500,modernfinalprice,modernprice;

    Button buyLauder,buyDouble,buyLipstick,buyWrinkle,buyCamouflage,buyModern;
    DatabaseReference usersRefer, orderRef;
    FirebaseAuth mAuth;

    public String phone,address;
    public String daywearfinalpricestring,productnamelauder;
    public String doublewarepricestring,productnamedouble;
    public String lipstickpricestring,productnamelipstick;
    public String wrinklepricestring,productnamewrinkle;
    public String camouflagepricestring,productnamecamouflage;
    public String modernpricestring,productnamemodern;

    String lauderQtyStr,doubleQtystr,lipsickQtystr,wrinkleQtystr,camouflageQtystr,modernQtystr;
            String currentUerId, saveCurrentUserTime, saveCurrentUserDate, buyersRandomKeyGen;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_makeupesteelauder);

        mAuth = FirebaseAuth.getInstance();
        currentUerId = mAuth.getCurrentUser().getUid();

        usersRefer = FirebaseDatabase.getInstance().getReference().child("Clients").child(currentUerId);
        orderRef = FirebaseDatabase.getInstance().getReference().child("Order").child(currentUerId);


        lauderQtyText = findViewById(R.id.lauderQty);
        doubleQtyText=findViewById(R.id.doubleQty);
        lipstickQtyText=findViewById(R.id.lispstickQty);
        wrinkleQtyText=findViewById(R.id.wrinkleQty);
        camouflageQtyText=findViewById(R.id.camouflageQty);
        modernQtyText=findViewById(R.id.modernQty);


        buyLauder = findViewById(R.id.buyLauder);
        buyDouble=findViewById(R.id.buyDouble);
        buyModern=findViewById(R.id.buyModern);
        buyModern.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buyModernDataToDB();
            }
        });

        buyCamouflage=findViewById(R.id.buyCamouflage);
        buyCamouflage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buyCamouflageDataToDB();
            }
        });

        buyWrinkle=findViewById(R.id.buyWrinkle);
        buyWrinkle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buyWrinkleDataToDB();

            }
        });

        buyLipstick=findViewById(R.id.buyLipstick);
        buyLipstick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                buyLipstickDataToDB();

            }
        });


        buyDouble.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buyDoubleDataToDB();
            }
        });

        buyLauder.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                lauderQtyStr = lauderQtyText.getText().toString();
                Daywearantioxidantprice=Integer.parseInt(lauderQtyStr);
                Daywearantioxidantfinalprice=Daywearantioxidantprice*daywearantioxidantsingleprice;
                daywearfinalpricestring=String.valueOf(Daywearantioxidantfinalprice);
                productnamelauder="Estee Lauder Daywear Anti-Oxidant";

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
                orderMap.put("OrderQty",lauderQtyStr);
                orderMap.put("Price",daywearfinalpricestring);
                orderMap.put("Item Name",productnamelauder);
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
                            Toast.makeText(Makeupesteelauder.this, "Order placed!", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });

    }

    private void buyModernDataToDB() {
        modernQtystr = modernQtyText.getText().toString();
        modernprice=Integer.parseInt(modernQtystr);
        modernfinalprice=modernprice*modernsingleprice;
        modernpricestring=String.valueOf(modernfinalprice);
        productnamemodern="Modern Muse Eau De Parfum";

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
        orderMap.put("OrderQty",modernQtystr);
        orderMap.put("Price",modernpricestring);
        orderMap.put("Item Name",productnamemodern);
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
                    Toast.makeText(Makeupesteelauder.this, "Order placed!", Toast.LENGTH_SHORT).show();
                }
            }
        });



    }

    private void buyCamouflageDataToDB() {
        camouflageQtystr = camouflageQtyText.getText().toString();
        camouflageprice=Integer.parseInt(camouflageQtystr);
        camouflagefinalprice=camouflageprice*camouflagesingleprice;
        camouflagepricestring=String.valueOf(camouflagefinalprice);
        productnamecamouflage="Cover Camouflage Makeup SPF 15";

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
        orderMap.put("OrderQty",camouflageQtystr);
        orderMap.put("Price",camouflagepricestring);
        orderMap.put("Item Name",productnamecamouflage);
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
                    Toast.makeText(Makeupesteelauder.this, "Order placed!", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    private void buyWrinkleDataToDB() {
        wrinkleQtystr = wrinkleQtyText.getText().toString();
        wrinkleprice=Integer.parseInt(wrinkleQtystr);
        wrinklefinalprice=wrinkleprice*wrinklesingleprice;
       wrinklepricestring=String.valueOf(wrinklefinalprice);
        productnamewrinkle="Perfectionist CP+R Wrinkle Lifting";

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
        orderMap.put("OrderQty",wrinkleQtystr);
        orderMap.put("Price",wrinklepricestring);
        orderMap.put("Item Name",productnamewrinkle);
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
                    Toast.makeText(Makeupesteelauder.this, "Order placed!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void buyLipstickDataToDB() {

        lipsickQtystr = lipstickQtyText.getText().toString();
        lipstickprice=Integer.parseInt(lipsickQtystr);
        lipstickfinalprice=lipstickprice*lipsticksingleprice;
        lipstickpricestring=String.valueOf(lipstickfinalprice);
        productnamelipstick="Pure Color Love Lipstick";

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
        orderMap.put("OrderQty",lipsickQtystr);
        orderMap.put("Price",lipstickpricestring);
        orderMap.put("Item Name",productnamelipstick);
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
                    Toast.makeText(Makeupesteelauder.this, "Order placed!", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }


    //MAKEUP-KIT DOUBLE MATERIAL LOGIC
    private void buyDoubleDataToDB() {
        doubleQtystr = doubleQtyText.getText().toString();
        doublewareprice=Integer.parseInt(doubleQtystr);
        doublewarefinalprice=doublewareprice*doublewaresingleprice;
        doublewarepricestring=String.valueOf(doublewarefinalprice);
        productnamelauder="Double Wear Stay-In-Place";

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
        orderMap.put("OrderQty",doubleQtystr);
        orderMap.put("Price",doublewarepricestring);
        orderMap.put("Item Name",productnamedouble);
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
                    Toast.makeText(Makeupesteelauder.this, "Order placed!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

}

