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

public class Suncareloreal extends AppCompatActivity {
    public EditText uvQtyText,dryQtyText,coolQtyText;


    public int uvsingleprice= 900,uvfinalprice,uvprice;
    public int drysingleprice= 1200,dryfinalprice,dryprice;
    public int coolsingleprice= 1050,coolfinalprice,coolprice;

    Button buyUv,buyDry,buyCool;

    DatabaseReference usersRefer, orderRef;
    FirebaseAuth mAuth;

    public String phone,address;
    public String uvpricestring,productnameuv;
    public String drypricestring,productnamedry;
    public String coolpricestring,productnamecool;

    String uvQtystr,dryQtystr,coolQtystr;
    String currentUerId, saveCurrentUserTime, saveCurrentUserDate, buyersRandomKeyGen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_suncareloreal);

        mAuth = FirebaseAuth.getInstance();
        currentUerId = mAuth.getCurrentUser().getUid();

        usersRefer = FirebaseDatabase.getInstance().getReference().child("Clients").child(currentUerId);
        orderRef = FirebaseDatabase.getInstance().getReference().child("Order").child(currentUerId);

        uvQtyText = findViewById(R.id.uvQty);
        dryQtyText=findViewById(R.id.dryQty);
        coolQtyText=findViewById(R.id.coolQty);



        buyCool=findViewById(R.id.buyCool);
        buyCool.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buyCool();
            }
        });
        buyDry=findViewById(R.id.buyDry);
        buyDry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buyDry();
            }
        });
        buyUv=findViewById(R.id.buyUv);
        buyUv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uvQtystr = uvQtyText.getText().toString();
                uvprice=Integer.parseInt(uvQtystr);
                uvfinalprice=uvprice*uvsingleprice;
               uvpricestring=String.valueOf(uvfinalprice);
                productnameuv="UV perfect";

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
                orderMap.put("OrderQty",uvQtystr);
                orderMap.put("Price",uvpricestring);
                orderMap.put("Item Name",productnameuv);
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
                            Toast.makeText(Suncareloreal.this, "Order placed!", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

            }
        });

    }

    private void buyCool() {
        coolQtystr = coolQtyText.getText().toString();
        coolprice=Integer.parseInt(coolQtystr);
        coolfinalprice=coolprice*coolsingleprice;
        coolpricestring=String.valueOf(coolfinalprice);
        productnamecool="Advanced suncare cool lotion";

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
        orderMap.put("OrderQty",coolQtystr);
        orderMap.put("Price",coolpricestring);
        orderMap.put("Item Name",productnamecool);
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
                    Toast.makeText(Suncareloreal.this, "Order placed!", Toast.LENGTH_SHORT).show();
                }
            }
        });


        }

    private void buyDry() {
        dryQtystr = dryQtyText.getText().toString();
        dryprice=Integer.parseInt(dryQtystr);
        dryfinalprice=dryprice*drysingleprice;
        drypricestring=String.valueOf(dryfinalprice);
        productnamedry="Invisible dry proect";

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
        orderMap.put("OrderQty",dryQtystr);
        orderMap.put("Price",drypricestring);
        orderMap.put("Item Name",productnamedry);
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
                    Toast.makeText(Suncareloreal.this, "Order placed!", Toast.LENGTH_SHORT).show();
                }
            }
        });


        }
}
