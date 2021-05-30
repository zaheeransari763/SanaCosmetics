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

public class Makeupmaybelline extends AppCompatActivity {
    public EditText matteQtyText,fitQtyText,darkQtyText,hyperQtyText,masterQtyText,pumpQtyText;


    public int mattesingleprice= 349,mattefinalprice,matteprice;
    public int fitsingleprice= 299,fitfinalprice,fitprice;
    public int darksingleprice= 496,darkfinalprice,darkprice;
    public int hypersingleprice= 248,hyperfinalprice,hyperprice;
    public int mastersingleprice= 440,masterfinalprice,masterprice;
    public int pumpsingleprice= 439,pumpfinalprice,pumpprice;

    Button buyMatte,buyFit,buyDark,buyHyper,buyMaster,buyPump;

    DatabaseReference usersRefer, orderRef;
    FirebaseAuth mAuth;

    public String phone,address;
    public String mattepricestring,productnamematte;
    public String fitpricestring,productnamefit;
    public String darkpricestring,productnamedark;
    public String hyperpricestring,productnamehyper;
    public String masterpricestring,productnamemaster;
    public String pumppricestring,productnamepump;

    String matteQtystr,fitQtystr,darkQtystr,hyperQtystr,masterQtystr,pumpQtystr;
    String currentUerId, saveCurrentUserTime, saveCurrentUserDate, buyersRandomKeyGen;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);//fullscreen
        setContentView(R.layout.activity_makeupmaybelline);

        mAuth = FirebaseAuth.getInstance();
        currentUerId = mAuth.getCurrentUser().getUid();

        usersRefer = FirebaseDatabase.getInstance().getReference().child("Clients").child(currentUerId);
        orderRef = FirebaseDatabase.getInstance().getReference().child("Order").child(currentUerId);

        matteQtyText = findViewById(R.id.matteQty);
        fitQtyText=findViewById(R.id.fitQty);
        darkQtyText=findViewById(R.id.darkQty);
        hyperQtyText=findViewById(R.id.hyperQty);
        masterQtyText=findViewById(R.id.masterQty);
        pumpQtyText=findViewById(R.id.pumpQty);


        buyPump=findViewById(R.id.buyPump);
        buyPump.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buyPump();
            }
        });
        buyMaster=findViewById(R.id.buyMaster);
        buyMaster.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buyMaster();
            }
        });

        buyHyper=findViewById(R.id.buyHyper);
        buyHyper.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buyHyper();

            }
        });
        
        buyDark=findViewById(R.id.buyDark);
        buyDark.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buyDark();
            }
        });
        buyFit=findViewById(R.id.buyFit);
        buyFit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buyFit();
            }
        });

        buyMatte=findViewById(R.id.buyMatte);
        buyMatte.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                matteQtystr = matteQtyText.getText().toString();
                matteprice=Integer.parseInt(matteQtystr);
                mattefinalprice=matteprice*mattesingleprice;
                mattepricestring=String.valueOf(mattefinalprice);
                productnamematte="Sensational Liquid Matte Lipstick";

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
                orderMap.put("OrderQty",matteQtystr);
                orderMap.put("Price",mattepricestring);
                orderMap.put("Item Name",productnamematte);
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
                            Toast.makeText(Makeupmaybelline.this, "Order placed!", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

            }
        });



    }

    private void buyPump() {
        pumpQtystr = pumpQtyText.getText().toString();
        pumpprice=Integer.parseInt(pumpQtystr);
        pumpfinalprice=pumpprice*pumpsingleprice;
        pumppricestring=String.valueOf(pumpfinalprice);
        productnamepump="Liquid Foundation With Pump";

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
        orderMap.put("OrderQty",pumpQtystr);
        orderMap.put("Price",pumppricestring);
        orderMap.put("Item Name",productnamepump);
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
                    Toast.makeText(Makeupmaybelline.this, "Order placed!", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    private void buyMaster() {
        masterQtystr = masterQtyText.getText().toString();
        masterprice=Integer.parseInt(masterQtystr);
        masterfinalprice=masterprice*mastersingleprice;
        masterpricestring=String.valueOf(masterfinalprice);
        productnamemaster="Master Chrome Metallic Highlighter";

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
        orderMap.put("OrderQty",masterQtystr);
        orderMap.put("Price",masterpricestring);
        orderMap.put("Item Name",productnamemaster);
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
                    Toast.makeText(Makeupmaybelline.this, "Order placed!", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    private void buyHyper() {
        hyperQtystr = hyperQtyText.getText().toString();
        hyperprice=Integer.parseInt(hyperQtystr);
        hyperfinalprice=hyperprice*hypersingleprice;
        hyperpricestring=String.valueOf(hyperfinalprice);
        productnamehyper="Volum Express Hyper Curl Mascara";

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
        orderMap.put("OrderQty",hyperQtystr);
        orderMap.put("Price",hyperpricestring);
        orderMap.put("Item Name",productnamehyper);
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
                    Toast.makeText(Makeupmaybelline.this, "Order placed!", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    private void buyDark() {
        darkQtystr = darkQtyText.getText().toString();
        darkprice=Integer.parseInt(darkQtystr);
        darkfinalprice=darkprice*darksingleprice;
        darkpricestring=String.valueOf(darkfinalprice);
        productnamedark="Dark Circles Treatment Concealer";

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
        orderMap.put("OrderQty",darkQtystr);
        orderMap.put("Price",darkpricestring);
        orderMap.put("Item Name",productnamedark);
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
                    Toast.makeText(Makeupmaybelline.this, "Order placed!", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    private void buyFit() {
        fitQtystr = fitQtyText.getText().toString();
        fitprice=Integer.parseInt(fitQtystr);
        fitfinalprice=fitprice*fitsingleprice;
        fitpricestring=String.valueOf(fitfinalprice);
        productnamefit="Fit Me Liquid Foundation Tube";

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
        orderMap.put("OrderQty",fitQtystr);
        orderMap.put("Price",fitpricestring);
        orderMap.put("Item Name",productnamefit);
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
                    Toast.makeText(Makeupmaybelline.this, "Order placed!", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
}
