package com.example.sanacosmetics;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
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

public class Makeupguerlain extends AppCompatActivity {
    public EditText cilsQtyText,beautyQtyText,glowQtyText,baseQtyText,lightQtyText,goldQtyText;


    public int cilssingleprice= 999,cilsfinalprice,cilsprice;
    public int beautysingleprice= 10000,beautyfinalprice,beautyprice;
    public int glowsingleprice= 1300,glowfinalprice,glowprice;
    public int basesingleprice= 900,basefinalprice,baseprice;
    public int lightsingleprice= 950,lightfinalprice,lightprice;
    public int goldsingleprice= 625,goldfinalprice,goldprice;

    Button buyCils,buyBeauty,buyGlow,buyBase,buyLight,buyGold;

    DatabaseReference usersRefer, orderRef;
    FirebaseAuth mAuth;

    public String phone,address;
    public String cilspricestring,productnamecils;
    public String beautypricestring,productnamebeauty;
    public String glowpricestring,productnameglow;
    public String basepricestring,productnamebase;
    public String lightpricestring,productnamelight;
    public String goldpricestring,productnamegold;

    String cilsQtystr,beautyQtystr,glowQtystr,baseQtystr,lightQtystr,goldQtystr;
    String currentUerId, saveCurrentUserTime, saveCurrentUserDate, buyersRandomKeyGen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);//fullscreen
        setContentView(R.layout.activity_makeupguerlain);


        mAuth = FirebaseAuth.getInstance();
        currentUerId = mAuth.getCurrentUser().getUid();

        usersRefer = FirebaseDatabase.getInstance().getReference().child("Clients").child(currentUerId);
        orderRef = FirebaseDatabase.getInstance().getReference().child("Order").child(currentUerId);

        cilsQtyText = findViewById(R.id.cilsQty);
        beautyQtyText=findViewById(R.id.beautyQty);
        glowQtyText=findViewById(R.id.glowQty);
        baseQtyText=findViewById(R.id.baseQty);
        lightQtyText=findViewById(R.id.lightQty);
        goldQtyText=findViewById(R.id.goldQty);

        buyGold=findViewById(R.id.buyGold);
        buyGold.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buyGold();
            }
        });
        buyLight=findViewById(R.id.buyLight);
        buyLight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buyLight();
            }
        });
        buyBase=findViewById(R.id.buyBase);
        buyBase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buyBase();
            }
        });
        buyGlow=findViewById(R.id.buyGlow);
        buyGlow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buyGlow();
            }
        });
        buyBeauty=findViewById(R.id.buyBeauty);
        buyBeauty.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buyBeauty();
            }
        });
        buyCils=findViewById(R.id.buyCils);
        buyCils.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cilsQtystr = cilsQtyText.getText().toString();
                cilsprice=Integer.parseInt(cilsQtystr);
                cilsfinalprice=cilsprice*cilssingleprice;
                cilspricestring=String.valueOf(cilsfinalprice);
                productnamecils="Cils d'enfer maxi lash";

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
                orderMap.put("OrderQty",cilsQtystr);
                orderMap.put("Price",cilspricestring);
                orderMap.put("Item Name",productnamecils);
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
                            Toast.makeText(Makeupguerlain.this, "Order placed!", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

            }
        });
    }

    private void buyGold() {
        goldQtystr = goldQtyText.getText().toString();
        goldprice=Integer.parseInt(goldQtystr);
        goldfinalprice=goldprice*goldsingleprice;
        goldpricestring=String.valueOf(goldfinalprice);
        productnamegold="Radiance with pure gold";

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
        orderMap.put("OrderQty",goldQtystr);
        orderMap.put("Price",goldpricestring);
        orderMap.put("Item Name",productnamegold);
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
                    Toast.makeText(Makeupguerlain.this, "Order placed!", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    private void buyLight() {
        lightQtystr = lightQtyText.getText().toString();
        lightprice=Integer.parseInt(lightQtystr);
        lightfinalprice=lightprice*lightsingleprice;
        lightpricestring=String.valueOf(lightfinalprice);
        productnamelight="Light perfecting white booster";

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
        orderMap.put("OrderQty",lightQtystr);
        orderMap.put("Price",lightpricestring);
        orderMap.put("Item Name",productnamelight);
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
                    Toast.makeText(Makeupguerlain.this, "Order placed!", Toast.LENGTH_SHORT).show();
                }
            }
        });


    }

    private void buyBase() {
        baseQtystr = baseQtyText.getText().toString();
        baseprice=Integer.parseInt(baseQtystr);
        basefinalprice=baseprice*basesingleprice;
        basepricestring=String.valueOf(basefinalprice);
        productnamebase="Base Perfecting pearl anti dullness";

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
        orderMap.put("OrderQty",baseQtystr);
        orderMap.put("Price",basepricestring);
        orderMap.put("Item Name",productnamebase);
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
                    Toast.makeText(Makeupguerlain.this, "Order placed!", Toast.LENGTH_SHORT).show();
                }
            }
        });


    }

    private void buyGlow() {
        glowQtystr = glowQtyText.getText().toString();
        glowprice=Integer.parseInt(glowQtystr);
        glowfinalprice=glowprice*glowsingleprice;
        glowpricestring=String.valueOf(glowfinalprice);
        productnameglow="Météorites glow pearls cushion";

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
        orderMap.put("OrderQty",glowQtystr);
        orderMap.put("Price",glowpricestring);
        orderMap.put("Item Name",productnameglow);
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
                    Toast.makeText(Makeupguerlain.this, "Order placed!", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    private void buyBeauty() {
        beautyQtystr = beautyQtyText.getText().toString();
        beautyprice=Integer.parseInt(beautyQtystr);
        beautyfinalprice=beautyprice*beautysingleprice;
        beautypricestring=String.valueOf(beautyfinalprice);
        productnamebeauty="My beauty essentials";

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
        orderMap.put("OrderQty",beautyQtystr);
        orderMap.put("Price",beautypricestring);
        orderMap.put("Item Name",productnamebeauty);
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
                    Toast.makeText(Makeupguerlain.this, "Order placed!", Toast.LENGTH_SHORT).show();
                }
            }
        });


    }
}
