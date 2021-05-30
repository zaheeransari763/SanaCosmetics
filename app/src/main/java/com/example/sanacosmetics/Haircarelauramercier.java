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

public class Haircarelauramercier extends AppCompatActivity {
    public EditText ultraQtyText,colorQtyText,settingQtyText,caviarQtyText;

    public int ultrasingleprice= 700,ultrafinalprice,ultraprice;
    public int colorsingleprice= 1090,colorfinalprice,colorprice;
    public int settingsingleprice= 1345,settingfinalprice,settingprice;
    public int caviarsingleprice= 970,caviarfinalprice,caviarprice;

    Button buyUltra,buyColor,buySetting,buyCaviar;

    DatabaseReference usersRefer, orderRef;
    FirebaseAuth mAuth;

    public String phone,address;
    public String ultrapricestring,productnameultra;
    public String colorpricestring,productnamecolor;
    public String settingpricestring,productnamesetting;
    public String caviarpricestring,productnamecaviar;

    String ultraQtystr,colorQtystr,settingQtystr,caviarQtystr;
    String currentUerId, saveCurrentUserTime, saveCurrentUserDate, buyersRandomKeyGen;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_haircarelauramercier);

        mAuth = FirebaseAuth.getInstance();
        currentUerId = mAuth.getCurrentUser().getUid();

        usersRefer = FirebaseDatabase.getInstance().getReference().child("Clients").child(currentUerId);
        orderRef = FirebaseDatabase.getInstance().getReference().child("Order").child(currentUerId);

        ultraQtyText = findViewById(R.id.ultraQty);
        colorQtyText=findViewById(R.id.colorQty);
        settingQtyText=findViewById(R.id.settingQty);
        caviarQtyText=findViewById(R.id.caviarQty);


        buyCaviar=findViewById(R.id.buyCaviar);
        buyCaviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buyCaviar();
            }
        });
        buySetting=findViewById(R.id.buySetting);
        buySetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buySetting();
            }
        });
        buyColor=findViewById(R.id.buyColor);
        buyColor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buyColor();
            }
        });
        buyUltra=findViewById(R.id.buyUltra);
        buyUltra.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ultraQtystr = ultraQtyText.getText().toString();
                ultraprice=Integer.parseInt(ultraQtystr);
                ultrafinalprice=ultraprice*ultrasingleprice;
                ultrapricestring=String.valueOf(ultrafinalprice);
                productnameultra="Fusion Ultra-Longwear Foundation";

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
                orderMap.put("OrderQty",ultraQtystr);
                orderMap.put("Price",ultrapricestring);
                orderMap.put("Item Name",productnameultra);
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
                            Toast.makeText(Haircarelauramercier.this, "Order placed!", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

            }
        });
    }

    private void buyCaviar() {
        caviarQtystr = caviarQtyText.getText().toString();
        caviarprice=Integer.parseInt(caviarQtystr);
        caviarfinalprice=caviarprice*caviarsingleprice;
        caviarpricestring=String.valueOf(caviarfinalprice);
        productnamecaviar="Caviar Stick Eye Shadow";

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
        orderMap.put("OrderQty",caviarQtystr);
        orderMap.put("Price",caviarpricestring);
        orderMap.put("Item Name",productnamecaviar);
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
                    Toast.makeText(Haircarelauramercier.this, "Order placed!", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    private void buySetting() {
        settingQtystr = settingQtyText.getText().toString();
        settingprice=Integer.parseInt(settingQtystr);
        settingfinalprice=settingprice*settingsingleprice;
        settingpricestring=String.valueOf(settingfinalprice);
        productnamesetting="Translucent Loose Setting Powder";

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
        orderMap.put("OrderQty",settingQtystr);
        orderMap.put("Price",settingpricestring);
        orderMap.put("Item Name",productnamesetting);
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
                    Toast.makeText(Haircarelauramercier.this, "Order placed!", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    private void buyColor()
    {
        colorQtystr = colorQtyText.getText().toString();
        colorprice=Integer.parseInt(colorQtystr);
        colorfinalprice=colorprice*colorsingleprice;
        colorpricestring=String.valueOf(colorfinalprice);
        productnamecolor="Blush Colour Infusion";

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
        orderMap.put("OrderQty",colorQtystr);
        orderMap.put("Price",colorpricestring);
        orderMap.put("Item Name",productnamecolor);
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
                    Toast.makeText(Haircarelauramercier.this, "Order placed!", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
}
