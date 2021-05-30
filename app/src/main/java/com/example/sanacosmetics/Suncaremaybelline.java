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

public class Suncaremaybelline extends AppCompatActivity {
    public EditText careQtyText,bbQtyText,faceQtyText,andQtyText,allQtyText;


    public int caresingleprice= 700,carefinalprice,careprice;
    public int bbsingleprice= 1300,bbfinalprice,bbprice;
    public int facesingleprice= 550,facefinalprice,faceprice;
    public int andsingleprice= 6130,andfinalprice,andprice;
    public int allsingleprice= 400,allfinalprice,allprice;

    Button buyCare,buyBb,buyFace,buyAnd,buyAll;

    DatabaseReference usersRefer, orderRef;
    FirebaseAuth mAuth;

    public String phone,address;
    public String carepricestring,productnamecare;
    public String bbpricestring,productnamebb;
    public String facepricestring,productnameface;
    public String andpricestring,productnameand;
    public String allpricestring,productnameall;

    String careQtystr,bbQtystr,faceQtystr,andQtystr,allQtystr;
    String currentUerId, saveCurrentUserTime, saveCurrentUserDate, buyersRandomKeyGen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_suncaremaybelline);

        mAuth = FirebaseAuth.getInstance();
        currentUerId = mAuth.getCurrentUser().getUid();

        usersRefer = FirebaseDatabase.getInstance().getReference().child("Clients").child(currentUerId);
        orderRef = FirebaseDatabase.getInstance().getReference().child("Order").child(currentUerId);

        careQtyText = findViewById(R.id.careQty);
        bbQtyText=findViewById(R.id.bbQty);
        faceQtyText=findViewById(R.id.faceQty);
        andQtyText=findViewById(R.id.andQty);
        allQtyText=findViewById(R.id.allQty);



        buyAll=findViewById(R.id.buyAll);
        buyAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buyAll();
            }
        });
        buyAnd=findViewById(R.id.buyAnd);
        buyAnd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buyAnd();

            }
        });
        buyFace=findViewById(R.id.buyFace);
        buyFace.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buyFace();
            }
        });
        buyBb=findViewById(R.id.buyBb);
        buyBb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buyBb();
            }
        });
        buyCare=findViewById(R.id.buyCare);
        buyCare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                careQtystr = careQtyText.getText().toString();
                careprice=Integer.parseInt(careQtystr);
                carefinalprice=careprice*caresingleprice;
                carepricestring=String.valueOf(carefinalprice);
                productnamecare="Care and correct cc cream";

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
                orderMap.put("OrderQty",careQtystr);
                orderMap.put("Price",carepricestring);
                orderMap.put("Item Name",productnamecare);
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
                            Toast.makeText(Suncaremaybelline.this, "Order placed!", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

            }
        });
    }

    private void buyAll() {
        allQtystr = allQtyText.getText().toString();
        allprice=Integer.parseInt(allQtystr);
        allfinalprice=allprice*allsingleprice;
        allpricestring=String.valueOf(allfinalprice);
        productnameall="Clear and glow all in one";

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
        orderMap.put("OrderQty",allQtystr);
        orderMap.put("Price",allpricestring);
        orderMap.put("Item Name",productnameall);
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
                    Toast.makeText(Suncaremaybelline.this, "Order placed!", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    private void buyAnd() {
        andQtystr = andQtyText.getText().toString();
        andprice=Integer.parseInt(andQtystr);
        andfinalprice=andprice*andsingleprice;
        andpricestring=String.valueOf(andfinalprice);
        productnameand="Clear and Glow";

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
        orderMap.put("OrderQty",andQtystr);
        orderMap.put("Price",andpricestring);
        orderMap.put("Item Name",productnameand);
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
                    Toast.makeText(Suncaremaybelline.this, "Order placed!", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    private void buyFace() {
        faceQtystr = faceQtyText.getText().toString();
        faceprice=Integer.parseInt(faceQtystr);
        facefinalprice=faceprice*facesingleprice;
        facepricestring=String.valueOf(facefinalprice);
        productnameface="Face studio master prime cream";

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
        orderMap.put("OrderQty",faceQtystr);
        orderMap.put("Price",facepricestring);
        orderMap.put("Item Name",productnameface);
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
                    Toast.makeText(Suncaremaybelline.this, "Order placed!", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    private void buyBb() {
        bbQtystr = bbQtyText.getText().toString();
        bbprice=Integer.parseInt(bbQtystr);
       bbfinalprice=bbprice*bbsingleprice;
        bbpricestring=String.valueOf(bbfinalprice);
        productnamebb="Super BB cream natural";

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
        orderMap.put("OrderQty",bbQtystr);
        orderMap.put("Price",bbpricestring);
        orderMap.put("Item Name",productnamebb);
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
                    Toast.makeText(Suncaremaybelline.this, "Order placed!", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
}
