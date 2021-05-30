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

public class Skincareesteelauder extends AppCompatActivity {
    public EditText rapidQtyText,whiteQtyText,barQtyText,fluidQtyText,lotionQtyText;

    public int rapidsingleprice= 3800,rapidfinalprice,rapidprice;
    public int whitesingleprice= 1000,whitefinalprice,whiteprice;
    public int barsingleprice= 600,barfinalprice,barprice;
    public int fluidsingleprice= 6200,fluidfinalprice,fluidprice;
    public int lotionsingleprice= 4300,lotionfinalprice,lotionprice;

    Button buyRapid,buyWhite,buyBar,buyFluid,buyLotion;

    DatabaseReference usersRefer, orderRef;
    FirebaseAuth mAuth;

    public String phone,address;
    public String rapidpricestring,productnamerapid;
    public String whitepricestring,productnamewhite;
    public String barpricestring,productnamebar;
    public String fluidpricestring,productnamefluid;
    public String lotionpricestring,productnamelotion;
    

    String rapidQtystr,whiteQtystr,barQtystr,fluidQtystr,lotionQtystr;
    String currentUerId, saveCurrentUserTime, saveCurrentUserDate, buyersRandomKeyGen;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_skincareesteelauder);

        mAuth = FirebaseAuth.getInstance();
        currentUerId = mAuth.getCurrentUser().getUid();

        usersRefer = FirebaseDatabase.getInstance().getReference().child("Clients").child(currentUerId);
        orderRef = FirebaseDatabase.getInstance().getReference().child("Order").child(currentUerId);

        rapidQtyText = findViewById(R.id.rapidQty);
        whiteQtyText=findViewById(R.id.whiteQty);
        barQtyText=findViewById(R.id.barQty);
        fluidQtyText=findViewById(R.id.fluidQty);
        lotionQtyText=findViewById(R.id.lotionQty);


        buyLotion=findViewById(R.id.buyLotion);
        buyLotion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buyLotion();
            }
        });
        buyFluid=findViewById(R.id.buyFluid);
        buyFluid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buyFluid();
            }
        });
        buyBar=findViewById(R.id.buyBar);
        buyBar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buyBar();
            }
        });
        buyWhite=findViewById(R.id.buyWhite);
        buyWhite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buyWhite();
            }
        });
        buyRapid=findViewById(R.id.buyRapid);
        buyRapid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rapidQtystr = rapidQtyText.getText().toString();
               rapidprice=Integer.parseInt(rapidQtystr);
                rapidfinalprice=rapidprice*rapidsingleprice;
                rapidpricestring=String.valueOf(rapidfinalprice);
                productnamerapid="Rapid Brightening with Vitamin C";

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
                orderMap.put("OrderQty",rapidQtystr);
                orderMap.put("Price",rapidpricestring);
                orderMap.put("Item Name",productnamerapid);
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
                            Toast.makeText(Skincareesteelauder.this, "Order placed!", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

            }
        });
    }

    private void buyLotion() {
        lotionQtystr = lotionQtyText.getText().toString();
        lotionprice=Integer.parseInt(lotionQtystr);
        lotionfinalprice=lotionprice*lotionsingleprice;
        lotionpricestring=String.valueOf(lotionfinalprice);
        productnamelotion="Radiant Energy Lotion Fresh Moist";

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
        orderMap.put("OrderQty",lotionQtystr);
        orderMap.put("Price",lotionpricestring);
        orderMap.put("Item Name",productnamelotion);
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
                    Toast.makeText(Skincareesteelauder.this, "Order placed!", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    private void buyFluid() {
        fluidQtystr = fluidQtyText.getText().toString();
        fluidprice=Integer.parseInt(fluidQtystr);
        fluidfinalprice=fluidprice*fluidsingleprice;
        fluidpricestring=String.valueOf(fluidfinalprice);
        productnamefluid="UV Fluid SPF 45 with Anti-Oxidants";

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
        orderMap.put("OrderQty",fluidQtystr);
        orderMap.put("Price",fluidpricestring);
        orderMap.put("Item Name",productnamewhite);
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
                    Toast.makeText(Skincareesteelauder.this, "Order placed!", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    private void buyBar() {
        barQtystr = barQtyText.getText().toString();
        barprice=Integer.parseInt(barQtystr);
        barfinalprice=barprice*barsingleprice;
        barpricestring=String.valueOf(barfinalprice);
        productnamebar="Cleansing Bar";

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
        orderMap.put("OrderQty",barQtystr);
        orderMap.put("Price",barpricestring);
        orderMap.put("Item Name",productnamebar);
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
                    Toast.makeText(Skincareesteelauder.this, "Order placed!", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    private void buyWhite() {
        whiteQtystr = whiteQtyText.getText().toString();
        whiteprice=Integer.parseInt(whiteQtystr);
        whitefinalprice=whiteprice*whitesingleprice;
        whitepricestring=String.valueOf(whitefinalprice);
        productnamewhite="White Brightening Youth Creme";

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
        orderMap.put("OrderQty",whiteQtystr);
        orderMap.put("Price",whitepricestring);
        orderMap.put("Item Name",productnamewhite);
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
                    Toast.makeText(Skincareesteelauder.this, "Order placed!", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
}
