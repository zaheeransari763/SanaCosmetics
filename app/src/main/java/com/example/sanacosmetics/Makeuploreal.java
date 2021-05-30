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

public class Makeuploreal extends AppCompatActivity {

    public EditText infallibleQtyText,pressedQtyText,foundationQtyText,geniusQtyText,moistQtyText,matchQtyText;

    public int infalliblesingleprice= 799,infalliblefinalprice,infallibleprice;
    public int pressedsingleprice= 999,pressedfinalprice,pressedprice;
    public int foundationsingleprice= 1200,foundationfinalprice,foundationprice;
    public int geniussingleprice= 910,geniusfinalprice,geniusprice;
    public int moistsingleprice= 750,moistfinalprice,moistprice;
    public int matchsingleprice= 725,matchfinalprice,matchprice;

    Button buyInfallible,buyPressed,buyFoundation,buyGenius,buyMoist,buyMatch;

    DatabaseReference usersRefer, orderRef;
    FirebaseAuth mAuth;

    public String phone,address;
    public String infalliblepricestring,productnameinfallible;
    public String pressedpricestring,productnamepressed;
    public String foundationpricestring,productnamefoundation;
    public String geniuspricestring,productnamegenius;
    public String moistpricestring,productnamemoist;
    public String matchpricestring,productnamematch;



    String infallibleQtyStr,pressedQtystr,foundationQtystr,geniusQtystr,moistQtystr,matchQtystr;
    String currentUerId, saveCurrentUserTime, saveCurrentUserDate, buyersRandomKeyGen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);//fullscreen
        setContentView(R.layout.activity_makeuploreal);

        mAuth = FirebaseAuth.getInstance();
        currentUerId = mAuth.getCurrentUser().getUid();

        usersRefer = FirebaseDatabase.getInstance().getReference().child("Clients").child(currentUerId);
        orderRef = FirebaseDatabase.getInstance().getReference().child("Order").child(currentUerId);

        infallibleQtyText = findViewById(R.id.infallibleQty);
        pressedQtyText=findViewById(R.id.pressedQty);
        foundationQtyText=findViewById(R.id.foundaionQty);
        geniusQtyText=findViewById(R.id.geniusQty);
        moistQtyText=findViewById(R.id.moistQty);
        matchQtyText=findViewById(R.id.matchQty);

        buyMatch=findViewById(R.id.buyMatch);
        buyMatch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buyMatch();
            }
        });

        buyMoist=findViewById(R.id.buyMoist);
        buyMoist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                buyMoist();
            }
        });

        buyGenius=findViewById(R.id.buyGenius);
        buyGenius.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buyGenius();
            }
        });
        buyFoundation=findViewById(R.id.buyFoundation);
        buyFoundation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buyFoundation();
            }
        });

        buyPressed=findViewById(R.id.buyPressed);
        buyPressed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buyPressed();
            }
        });

        buyInfallible = findViewById(R.id.buyInfallible);
        buyInfallible.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                infallibleQtyStr = infallibleQtyText.getText().toString();
                infallibleprice=Integer.parseInt(infallibleQtyStr);
                infalliblefinalprice=infallibleprice*infalliblesingleprice;
                infalliblepricestring=String.valueOf(infalliblefinalprice);
                productnameinfallible="Infallible Pro Matte Liquid Lipstick";

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
                orderMap.put("OrderQty",infallibleQtyStr);
                orderMap.put("Price",infalliblepricestring);
                orderMap.put("Item Name",productnameinfallible);
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
                            Toast.makeText(Makeuploreal.this, "Order placed!", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

            }
        });

    }

    private void buyMatch() {
        matchQtystr = matchQtyText.getText().toString();
        matchprice=Integer.parseInt(matchQtystr);
        matchfinalprice=matchprice*matchsingleprice;
        matchpricestring=String.valueOf(matchfinalprice);
        productnamematch="True Match Super Powder";

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
        orderMap.put("OrderQty",matchQtystr);
        orderMap.put("Price",matchpricestring);
        orderMap.put("Item Name",productnamematch);
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
                    Toast.makeText(Makeuploreal.this, "Order placed!", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    private void buyMoist() {
        moistQtystr = moistQtyText.getText().toString();
        moistprice=Integer.parseInt(moistQtystr);
        moistfinalprice=moistprice*moistsingleprice;
        moistpricestring=String.valueOf(moistfinalprice);
        productnamemoist="Moist Matte 214 Raspberry Syrup";

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
        orderMap.put("OrderQty",moistQtystr);
        orderMap.put("Price",moistpricestring);
        orderMap.put("Item Name",productnamemoist);
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
                    Toast.makeText(Makeuploreal.this, "Order placed!", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    private void buyGenius() {
        geniusQtystr = geniusQtyText.getText().toString();
        geniusprice=Integer.parseInt(geniusQtystr);
        geniusfinalprice=geniusprice*geniussingleprice;
        geniuspricestring=String.valueOf(geniusfinalprice);
        productnamegenius="Brow Artist Genius Kit Medium-Dark";

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
        orderMap.put("OrderQty",geniusQtystr);
        orderMap.put("Price",geniuspricestring);
        orderMap.put("Item Name",productnamegenius);
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
                    Toast.makeText(Makeuploreal.this, "Order placed!", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    private void buyFoundation() {
        foundationQtystr = foundationQtyText.getText().toString();
        foundationprice=Integer.parseInt(foundationQtystr);
        foundationfinalprice=foundationprice*foundationsingleprice;
        foundationpricestring=String.valueOf(foundationfinalprice);
        productnamefoundation="Infallible 24h Foundation";

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
        orderMap.put("OrderQty",foundationQtystr);
        orderMap.put("Price",foundationpricestring);
        orderMap.put("Item Name",productnamefoundation);
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
                    Toast.makeText(Makeuploreal.this, "Order placed!", Toast.LENGTH_SHORT).show();
                }
            }
        });



    }

    private void buyPressed() {
        pressedQtystr = pressedQtyText.getText().toString();
        pressedprice=Integer.parseInt(pressedQtystr);
        pressedfinalprice=pressedprice*pressedsingleprice;
        pressedpricestring=String.valueOf(pressedfinalprice);
        productnamepressed="Infallible ProMatte Pressed Powder";

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
        orderMap.put("OrderQty",pressedQtystr);
        orderMap.put("Price",pressedpricestring);
        orderMap.put("Item Name",productnamepressed);
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
                    Toast.makeText(Makeuploreal.this, "Order placed!", Toast.LENGTH_SHORT).show();
                }
            }
        });


    }
}
