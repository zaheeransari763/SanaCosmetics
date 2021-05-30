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

public class Haircarenars extends AppCompatActivity {
    public EditText iluminatorQtyText,perfectorQtyText,gelQtyText,defineQtyText;

    public int iluminatorsingleprice= 805,iluminatorfinalprice,iluminatorprice;
    public int perfectorsingleprice= 190,perfectorfinalprice,perfectorprice;
    public int gelsingleprice= 945,gelfinalprice,gelprice;
    public int definesingleprice= 1070,definefinalprice,defineprice;

    Button buyIluminator,buyPerfector,buyGel,buyDefine;

    DatabaseReference usersRefer, orderRef;
    FirebaseAuth mAuth;

    public String phone,address;
    public String iluminatorpricestring,productnameiluminator;
    public String perfectorpricestring,productnameperfector;
    public String gelpricestring,productnamegel;
    public String definepricestring,productnamedefine;

    String iluminatorQtystr,perfectorQtystr,gelQtystr,defineQtystr;
    String currentUerId, saveCurrentUserTime, saveCurrentUserDate, buyersRandomKeyGen;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_haircarenars);

        mAuth = FirebaseAuth.getInstance();
        currentUerId = mAuth.getCurrentUser().getUid();

        usersRefer = FirebaseDatabase.getInstance().getReference().child("Clients").child(currentUerId);
        orderRef = FirebaseDatabase.getInstance().getReference().child("Order").child(currentUerId);

        iluminatorQtyText = findViewById(R.id.iluminatorQty);
        perfectorQtyText=findViewById(R.id.perfectorQty);
        gelQtyText=findViewById(R.id.gelQty);
        defineQtyText=findViewById(R.id.defineQty);


        buyDefine=findViewById(R.id.buyDefine);
        buyDefine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buyDefine();
            }
        });
        buyGel=findViewById(R.id.buyGel);
        buyGel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buyGel();
            }
        });
        buyPerfector=findViewById(R.id.buyPerfector);
        buyPerfector.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buyPerfectr();
            }
        });
        buyIluminator=findViewById(R.id.buyIluminator);
        buyIluminator.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                iluminatorQtystr = iluminatorQtyText.getText().toString();
                iluminatorprice=Integer.parseInt(iluminatorQtystr);
                iluminatorfinalprice=iluminatorprice*iluminatorsingleprice;
                iluminatorpricestring=String.valueOf(iluminatorfinalprice);
                productnameiluminator="Cosmetic iluminator";

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
                orderMap.put("OrderQty",iluminatorQtystr);
                orderMap.put("Price",iluminatorpricestring);
                orderMap.put("Item Name",productnameiluminator);
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
                            Toast.makeText(Haircarenars.this, "Order placed!", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

            }
        });
    }

    private void buyDefine() {
        defineQtystr = defineQtyText.getText().toString();
        defineprice=Integer.parseInt(defineQtystr);
        definefinalprice=defineprice*definesingleprice;
        definepricestring=String.valueOf(definefinalprice);
        productnamedefine="Brow defining cream";

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
        orderMap.put("OrderQty",defineQtystr);
        orderMap.put("Price",definepricestring);
        orderMap.put("Item Name",productnamedefine);
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
                    Toast.makeText(Haircarenars.this, "Order placed!", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    private void buyGel() {
        gelQtystr = gelQtyText.getText().toString();
       gelprice=Integer.parseInt(gelQtystr);
        gelfinalprice=gelprice*gelsingleprice;
        gelpricestring=String.valueOf(gelfinalprice);
        productnamegel="Brow gel";

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
        orderMap.put("OrderQty",gelQtystr);
        orderMap.put("Price",gelpricestring);
        orderMap.put("Item Name",productnamegel);
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
                    Toast.makeText(Haircarenars.this, "Order placed!", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    private void buyPerfectr() {
        perfectorQtystr = perfectorQtyText.getText().toString();
        perfectorprice=Integer.parseInt(perfectorQtystr);
        perfectorfinalprice=perfectorprice*perfectorsingleprice;
        perfectorpricestring=String.valueOf(perfectorfinalprice);
        productnameperfector="Brow perfector";

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
        orderMap.put("OrderQty",perfectorQtystr);
        orderMap.put("Price",perfectorpricestring);
        orderMap.put("Item Name",productnameperfector);
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
                    Toast.makeText(Haircarenars.this, "Order placed!", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
}
