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

public class Haircareguerlain extends AppCompatActivity {
    public EditText royalQtyText,dailyQtyText,youthQtyText,superQtyText;

    public int royalsingleprice= 709,royalfinalprice,royalprice;
    public int dailysingleprice= 1800,dailyfinalprice,dailyprice;
    public int youthsingleprice= 650,youthfinalprice,youthprice;
    public int supersingleprice= 130,superfinalprice,superprice;

    Button buyRoyal,buyDaily,buyYouth,buySuper;

    DatabaseReference usersRefer, orderRef;
    FirebaseAuth mAuth;

    public String phone,address;
    public String royalpricestring,productnameroyal;
    public String dailypricestring,productnamedaily;
    public String youthpricestring,productnameyouth;
    public String superpricestring,productnamesuper;

    String royalQtystr,dailyQtystr,youthQtystr,superQtystr;
    String currentUerId, saveCurrentUserTime, saveCurrentUserDate, buyersRandomKeyGen;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_haircareguerlain);

        mAuth = FirebaseAuth.getInstance();
        currentUerId = mAuth.getCurrentUser().getUid();

        usersRefer = FirebaseDatabase.getInstance().getReference().child("Clients").child(currentUerId);
        orderRef = FirebaseDatabase.getInstance().getReference().child("Order").child(currentUerId);

        royalQtyText = findViewById(R.id.royalQty);
        dailyQtyText=findViewById(R.id.dailyQty);
        youthQtyText=findViewById(R.id.youthQty);
        superQtyText=findViewById(R.id.superQty);



        buySuper=findViewById(R.id.buySuper);
        buySuper.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buySuper();
            }
        });
        buyYouth=findViewById(R.id.buyYouth);
        buyYouth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buyYouth();
            }
        });
        buyDaily=findViewById(R.id.buyDaily);
        buyDaily.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buyDaily();
            }
        });
        buyRoyal=findViewById(R.id.buyRoyal);
        buyRoyal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                royalQtystr = royalQtyText.getText().toString();
                royalprice=Integer.parseInt(royalQtystr);
                royalfinalprice=royalprice*royalsingleprice;
                royalpricestring=String.valueOf(royalfinalprice);
                productnameroyal="Abeille Royale Repairing Honey Gel";

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
                orderMap.put("OrderQty",royalQtystr);
                orderMap.put("Price",royalpricestring);
                orderMap.put("Item Name",productnameroyal);
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
                            Toast.makeText(Haircareguerlain.this, "Order placed!", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
    }

    private void buySuper() {
        superQtystr = superQtyText.getText().toString();
        superprice=Integer.parseInt(superQtystr);
        superfinalprice=superprice*supersingleprice;
        superpricestring=String.valueOf(superfinalprice);
        productnamesuper="Super Aqua-Serum";

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
        orderMap.put("OrderQty",superQtystr);
        orderMap.put("Price",superpricestring);
        orderMap.put("Item Name",productnamesuper);
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
                    Toast.makeText(Haircareguerlain.this, "Order placed!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void buyYouth() {
        youthQtystr = youthQtyText.getText().toString();
        youthprice=Integer.parseInt(youthQtystr);
        youthfinalprice=youthprice*youthsingleprice;
        youthpricestring=String.valueOf(youthfinalprice);
        productnameyouth="FAbeille Royale Youth Watery Oil";

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
        orderMap.put("OrderQty",youthQtystr);
        orderMap.put("Price",youthpricestring);
        orderMap.put("Item Name",productnameyouth);
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
                    Toast.makeText(Haircareguerlain.this, "Order placed!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void buyDaily() {
        dailyQtystr = dailyQtyText.getText().toString();
        dailyprice=Integer.parseInt(dailyQtystr);
        dailyfinalprice=dailyprice*dailysingleprice;
        dailypricestring=String.valueOf(dailyfinalprice);
        productnamedaily="Abeille Royale Daily Repair Serum";

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
        orderMap.put("OrderQty",dailyQtystr);
        orderMap.put("Price",dailypricestring);
        orderMap.put("Item Name",productnamedaily);
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
                    Toast.makeText(Haircareguerlain.this, "Order placed!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
