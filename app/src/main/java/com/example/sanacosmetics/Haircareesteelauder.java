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

public class Haircareesteelauder extends AppCompatActivity {
    public EditText liftQtyText,flowerQtyText,comfortQtyText,orangeQtyText;

    public int liftsingleprice= 720,liftfinalprice,liftprice;
    public int flowersingleprice= 190,flowerfinalprice,flowerprice;
    public int comfortsingleprice= 1045,comfortfinalprice,comfortprice;
    public int orangesingleprice= 170,orangefinalprice,orangeprice;

    Button buyLift,buyFlower,buyComfort,buyOrange;

    DatabaseReference usersRefer, orderRef;
    FirebaseAuth mAuth;

    public String phone,address;
    public String liftpricestring,productnamelift;
    public String flowerpricestring,productnameflower;
    public String comfortpricestring,productnamecomfort;
    public String orangepricestring,productnameorange;

    String liftQtystr,flowerQtystr,comfortQtystr,orangeQtystr;
    String currentUerId, saveCurrentUserTime, saveCurrentUserDate, buyersRandomKeyGen;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_haircareesteelauder);

        mAuth = FirebaseAuth.getInstance();
        currentUerId = mAuth.getCurrentUser().getUid();

        usersRefer = FirebaseDatabase.getInstance().getReference().child("Clients").child(currentUerId);
        orderRef = FirebaseDatabase.getInstance().getReference().child("Order").child(currentUerId);

        liftQtyText = findViewById(R.id.liftQty);
        flowerQtyText=findViewById(R.id.flowerQty);
        comfortQtyText=findViewById(R.id.comfortQty);
        orangeQtyText=findViewById(R.id.orangeQty);


        buyOrange=findViewById(R.id.buyOrange);
        buyOrange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buyOrange();
            }
        });
        buyComfort=findViewById(R.id.buyComfort);
        buyComfort.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buyComfort();

            }
        });
        buyFlower=findViewById(R.id.buyFlower);
        buyFlower.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buyFlower();
            }
        });

        buyLift=findViewById(R.id.buyLift);
        buyLift.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                liftQtystr = liftQtyText.getText().toString();
                liftprice=Integer.parseInt(liftQtystr);
                liftfinalprice=liftprice*liftsingleprice;
                liftpricestring=String.valueOf(liftfinalprice);
                productnamelift="Resilience lift cooling eye hair";

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
                orderMap.put("OrderQty",liftQtystr);
                orderMap.put("Price",liftpricestring);
                orderMap.put("Item Name",productnamelift);
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
                            Toast.makeText(Haircareesteelauder.this, "Order placed!", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

            }
        });
    }

    private void buyOrange() {
        orangeQtystr = orangeQtyText.getText().toString();
        orangeprice=Integer.parseInt(orangeQtystr);
        orangefinalprice=orangeprice*orangesingleprice;
        orangepricestring=String.valueOf(orangefinalprice);
        productnameorange="Flower orange Softening Mask";

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
        orderMap.put("OrderQty",orangeQtystr);
        orderMap.put("Price",orangepricestring);
        orderMap.put("Item Name",productnameorange);
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
                    Toast.makeText(Haircareesteelauder.this, "Order placed!", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    private void buyComfort() {
        comfortQtystr = comfortQtyText.getText().toString();
        comfortprice=Integer.parseInt(comfortQtystr);
        comfortfinalprice=comfortprice*comfortsingleprice;
        comfortpricestring=String.valueOf(comfortfinalprice);
        productnamecomfort="Comforting Conditioning HairMask";

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
        orderMap.put("OrderQty",comfortQtystr);
        orderMap.put("Price",comfortpricestring);
        orderMap.put("Item Name",productnamecomfort);
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
                    Toast.makeText(Haircareesteelauder.this, "Order placed!", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    private void buyFlower() {
        flowerQtystr = flowerQtyText.getText().toString();
        flowerprice=Integer.parseInt(flowerQtystr);
        flowerfinalprice=flowerprice*flowersingleprice;
        flowerpricestring=String.valueOf(flowerfinalprice);
        productnameflower="Flower Jasmine Softening Mask";

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
        orderMap.put("OrderQty",flowerQtystr);
        orderMap.put("Price",flowerpricestring);
        orderMap.put("Item Name",productnameflower);
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
                    Toast.makeText(Haircareesteelauder.this, "Order placed!", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
}
