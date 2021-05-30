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

public class Skincarenars extends AppCompatActivity {
    public EditText gentleQtyText,optimalQtyText,eyeQtyText,maskQtyText,motteQtyText;

    public int gentlesingleprice= 790,gentlefinalprice,gentleprice;
    public int optimalsingleprice= 1200,optimalfinalprice,optimalprice;
    public int eyesingleprice= 950,eyefinalprice,eyeprice;
    public int masksingleprice= 1130,maskfinalprice,maskprice;
    public int mottesingleprice= 430,mottefinalprice,motteprice;

    Button buyGentle,buyOptimal,buyEye,buyMask,buyMotte;

    DatabaseReference usersRefer, orderRef;
    FirebaseAuth mAuth;

    public String phone,address;
    public String gentlepricestring,productnamegentle;
    public String optimalpricestring,productnameoptimal;
    public String eyepricestring,productnameeye;
    public String maskpricestring,productnamemask;
    public String mottepricestring,productnamemotte;

    String gentleQtystr,optimalQtystr,eyeQtystr,maskQtystr,motteQtystr;
    String currentUerId, saveCurrentUserTime, saveCurrentUserDate, buyersRandomKeyGen;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_skincarenars);

        mAuth = FirebaseAuth.getInstance();
        currentUerId = mAuth.getCurrentUser().getUid();

        usersRefer = FirebaseDatabase.getInstance().getReference().child("Clients").child(currentUerId);
        orderRef = FirebaseDatabase.getInstance().getReference().child("Order").child(currentUerId);

        gentleQtyText = findViewById(R.id.gentleQty);
        optimalQtyText=findViewById(R.id.optimalQty);
        eyeQtyText=findViewById(R.id.eyeQty);
        maskQtyText=findViewById(R.id.maskQty);
        motteQtyText=findViewById(R.id.motteQty);



        buyMotte=findViewById(R.id.buyMotte);
        buyMotte.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buyMotte();
            }
        });
        buyMask=findViewById(R.id.buyMask);
        buyMask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buyMask();
            }
        });
        buyEye=findViewById(R.id.buyEye);
        buyEye.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buyEye();

            }
        });
        buyOptimal=findViewById(R.id.buyOptimal);
        buyOptimal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buyOptimal();
            }
        });
        buyGentle=findViewById(R.id.buyGentle);
        buyGentle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gentleQtystr = gentleQtyText.getText().toString();
                gentleprice=Integer.parseInt(gentleQtystr);
                gentlefinalprice=gentleprice*gentlesingleprice;
                gentlepricestring=String.valueOf(gentlefinalprice);
                productnamegentle="Gentle oil free eye makeup remover";

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
                orderMap.put("OrderQty",optimalQtystr);
                orderMap.put("Price",optimalpricestring);
                orderMap.put("Item Name",productnameoptimal);
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
                            Toast.makeText(Skincarenars.this, "Order placed!", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

            }
        });

    }

    private void buyMotte() {
        motteQtystr =motteQtyText.getText().toString();
        motteprice=Integer.parseInt(motteQtystr);
        mottefinalprice=motteprice*mottesingleprice;
        mottepricestring=String.valueOf(mottefinalprice);
        productnamemotte="Luminious moisture cream";

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
        orderMap.put("OrderQty",motteQtystr);
        orderMap.put("Price",mottepricestring);
        orderMap.put("Item Name",productnamemotte);
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
                    Toast.makeText(Skincarenars.this, "Order placed!", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    private void buyMask() {
        maskQtystr = maskQtyText.getText().toString();
        maskprice=Integer.parseInt(maskQtystr);
        maskfinalprice=maskprice*masksingleprice;
        maskpricestring=String.valueOf(maskfinalprice);
        productnamemask="Aqua gel luminious mask";

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
        orderMap.put("OrderQty",maskQtystr);
        orderMap.put("Price",maskpricestring);
        orderMap.put("Item Name",productnamemask);
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
                    Toast.makeText(Skincarenars.this, "Order placed!", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    private void buyEye() {
        eyeQtystr = eyeQtyText.getText().toString();
        eyeprice=Integer.parseInt(eyeQtystr);
        eyefinalprice=eyeprice*eyesingleprice;
        eyepricestring=String.valueOf(eyefinalprice);
        productnameeye="Total replinising eye cream";

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
        orderMap.put("OrderQty",eyeQtystr);
        orderMap.put("Price",eyepricestring);
        orderMap.put("Item Name",productnameeye);
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
                    Toast.makeText(Skincarenars.this, "Order placed!", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    private void buyOptimal() {
        optimalQtystr = optimalQtyText.getText().toString();
        optimalprice=Integer.parseInt(optimalQtystr);
        optimalfinalprice=optimalprice*optimalsingleprice;
        optimalpricestring=String.valueOf(optimalfinalprice);
        productnameoptimal="Optimal brightening concentrate";

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
        orderMap.put("OrderQty",optimalQtystr);
        orderMap.put("Price",optimalpricestring);
        orderMap.put("Item Name",productnameoptimal);
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
                    Toast.makeText(Skincarenars.this, "Order placed!", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
}
