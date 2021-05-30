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

public class Makeuplauramercier extends AppCompatActivity {
    public EditText fusionQtyText,infusionQtyText,looseQtyText,stickQtyText,tintQtyText,finishQtyText;


    public int fusionsingleprice= 700,fusionfinalprice,fusionprice;
    public int infusionsingleprice= 1090,infusionfinalprice,infusionprice;
    public int loosesingleprice= 1345,loosefinalprice,looseprice;
    public int sticksingleprice= 990,stickfinalprice,stickprice;
    public int tintsingleprice= 1050,tintfinalprice,tintprice;
    public int finishsingleprice= 715,finishfinalprice,finishprice;

    Button buyFusion,buyInfusion,buyLoose,buyStick,buyTint,buyFinish;

    DatabaseReference usersRefer, orderRef;
    FirebaseAuth mAuth;

    public String phone,address;
    public String fusionpricestring,productnamefusion;
    public String infusionpricestring,productnameinfusion;
    public String loosepricestring,productnameloose;
    public String stickpricestring,productnamestick;
    public String tintpricestring,productnametint;
    public String finishpricestring,productnamefinish;

    String fusionQtystr,infusionQtystr,looseQtystr,stickQtystr,tintQtystr,finishQtystr;
    String currentUerId, saveCurrentUserTime, saveCurrentUserDate, buyersRandomKeyGen;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);//fullscreen
        setContentView(R.layout.activity_makeuplauramercier);

        mAuth = FirebaseAuth.getInstance();
        currentUerId = mAuth.getCurrentUser().getUid();

        usersRefer = FirebaseDatabase.getInstance().getReference().child("Clients").child(currentUerId);
        orderRef = FirebaseDatabase.getInstance().getReference().child("Order").child(currentUerId);

        fusionQtyText = findViewById(R.id.fusionQty);
        infusionQtyText=findViewById(R.id.infusionQty);
        looseQtyText=findViewById(R.id.looseQty);
        stickQtyText=findViewById(R.id.stickQty);
        tintQtyText=findViewById(R.id.tintQty);
        finishQtyText=findViewById(R.id.finishQty);

        buyFinish=findViewById(R.id.buyFinish);
        buyFinish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buyFinish();
            }
        });
        buyTint=findViewById(R.id.buyTint);
        buyTint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buyTint();
            }
        });
        buyStick=findViewById(R.id.buyStick);
        buyStick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buyStick();
            }
        });
        buyLoose=findViewById(R.id.buyLoose);
        buyLoose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buyLoose();
            }
        });
        buyInfusion=findViewById(R.id.buyInfusion);
        buyInfusion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buyInfusion();
            }
        });
        buyFusion=findViewById(R.id.buyFusion);
        buyFusion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fusionQtystr = fusionQtyText.getText().toString();
                fusionprice=Integer.parseInt(fusionQtystr);
                fusionfinalprice=fusionprice*fusionsingleprice;
                fusionpricestring=String.valueOf(fusionfinalprice);
                productnamefusion="Fusion Ultra-Longwear Foundation";

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
                orderMap.put("OrderQty",fusionQtystr);
                orderMap.put("Price",fusionpricestring);
                orderMap.put("Item Name",productnamefusion);
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
                            Toast.makeText(Makeuplauramercier.this, "Order placed!", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

            }
        });

    }

    private void buyFinish() {
        finishQtystr = finishQtyText.getText().toString();
        finishprice=Integer.parseInt(finishQtystr);
        finishfinalprice=finishprice*finishsingleprice;
        finishpricestring=String.valueOf(finishfinalprice);
        productnamefinish="Smooth Finish Foundation Powder";

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
        orderMap.put("OrderQty",finishQtystr);
        orderMap.put("Price",finishpricestring);
        orderMap.put("Item Name",productnamefinish);
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
                    Toast.makeText(Makeuplauramercier.this, "Order placed!", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    private void buyTint() {
        tintQtystr = tintQtyText.getText().toString();
        tintprice=Integer.parseInt(tintQtystr);
        tintfinalprice=tintprice*tintsingleprice;
        tintpricestring=String.valueOf(tintfinalprice);
        productnametint="Tinted Moisturizer Broad Spectrum";

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
        orderMap.put("OrderQty",tintQtystr);
        orderMap.put("Price",tintpricestring);
        orderMap.put("Item Name",productnametint);
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
                    Toast.makeText(Makeuplauramercier.this, "Order placed!", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    private void buyStick() {
        stickQtystr = stickQtyText.getText().toString();
        stickprice=Integer.parseInt(stickQtystr);
        stickfinalprice=looseprice*sticksingleprice;
        stickpricestring=String.valueOf(stickfinalprice);
        productnamestick="Caviar Stick Eye Shadow";

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
        orderMap.put("OrderQty",stickQtystr);
        orderMap.put("Price",stickpricestring);
        orderMap.put("Item Name",productnamestick);
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
                    Toast.makeText(Makeuplauramercier.this, "Order placed!", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    private void buyLoose() {
        looseQtystr = looseQtyText.getText().toString();
        looseprice=Integer.parseInt(looseQtystr);
        loosefinalprice=looseprice*loosesingleprice;
        loosepricestring=String.valueOf(loosefinalprice);
        productnameloose="Translucent Loose Setting Powder";

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
        orderMap.put("OrderQty",looseQtystr);
        orderMap.put("Price",loosepricestring);
        orderMap.put("Item Name",productnameloose);
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
                    Toast.makeText(Makeuplauramercier.this, "Order placed!", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    private void buyInfusion() {
        infusionQtystr = infusionQtyText.getText().toString();
        infusionprice=Integer.parseInt(infusionQtystr);
        infusionfinalprice=infusionprice*infusionsingleprice;
        infusionpricestring=String.valueOf(infusionfinalprice);
        productnameinfusion="Blush Colour Infusion";

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
        orderMap.put("OrderQty",infusionQtystr);
        orderMap.put("Price",infusionpricestring);
        orderMap.put("Item Name",productnameinfusion);
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
                    Toast.makeText(Makeuplauramercier.this, "Order placed!", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
}
