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

public class Makeupnars extends AppCompatActivity {
    public EditText paletteQtyText,softQtyText,dolceQtyText,pureQtyText,blushQtyText,sheerQtyText;


    public int palettesingleprice= 720,palettefinalprice,paletteprice;
    public int softsingleprice= 1000,softfinalprice,softprice;
    public int dolcesingleprice= 1200,dolcefinalprice,dolceprice;
    public int puresingleprice= 990,purefinalprice,pureprice;
    public int blushsingleprice= 950,blushfinalprice,blushprice;
    public int sheersingleprice= 615,sheerfinalprice,sheerprice;

    Button buyPalette,buySoft,buyDolce,buyPure,buyBlush,buySheer;

    DatabaseReference usersRefer, orderRef;
    FirebaseAuth mAuth;

    public String phone,address;
    public String palettepricestring,productnamepalette;
    public String softpricestring,productnamesoft;
    public String dolcepricestring,productnamedolce;
    public String purepricestring,productnamepure;
    public String blushpricestring,productnameblush;
    public String sheerpricestring,productnamesheer;

    String paletteQtystr,softQtystr,dolceQtystr,pureQtystr,blushQtystr,sheerQtystr;
    String currentUerId, saveCurrentUserTime, saveCurrentUserDate, buyersRandomKeyGen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);//fullscreen
        setContentView(R.layout.activity_makeupnars);

        mAuth = FirebaseAuth.getInstance();
        currentUerId = mAuth.getCurrentUser().getUid();

        usersRefer = FirebaseDatabase.getInstance().getReference().child("Clients").child(currentUerId);
        orderRef = FirebaseDatabase.getInstance().getReference().child("Order").child(currentUerId);

        paletteQtyText = findViewById(R.id.paletteQty);
        softQtyText=findViewById(R.id.softQty);
        dolceQtyText=findViewById(R.id.dolceQty);
        pureQtyText=findViewById(R.id.pureQty);
        blushQtyText=findViewById(R.id.blushQty);
        sheerQtyText=findViewById(R.id.sheerQty);

        buySheer=findViewById(R.id.buySheer);
        buySheer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buySheer();
            }
        });

        buyBlush=findViewById(R.id.buyBlush);
        buyBlush.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buyBlush();
            }
        });
        buyPure=findViewById(R.id.buyPure);
        buyPure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buyPure();
            }
        });
        buyDolce=findViewById(R.id.buyDolce);
        buyDolce.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buyDolce();
            }
        });
        buySoft=findViewById(R.id.buySoft);
        buySoft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buySoft();
            }
        });

        buyPalette=findViewById(R.id.buyPalette);
        buyPalette.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                paletteQtystr = paletteQtyText.getText().toString();
                paletteprice=Integer.parseInt(paletteQtystr);
                palettefinalprice=paletteprice*palettesingleprice;
                palettepricestring=String.valueOf(palettefinalprice);
                productnamepalette="Afterglow Eyeshadow Palette";

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
                orderMap.put("OrderQty",paletteQtystr);
                orderMap.put("Price",palettepricestring);
                orderMap.put("Item Name",productnamepalette);
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
                            Toast.makeText(Makeupnars.this, "Order placed!", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

            }
        });


    }

    private void buySheer() {
        sheerQtystr = sheerQtyText.getText().toString();
        sheerprice=Integer.parseInt(sheerQtystr);
        sheerfinalprice=sheerprice*sheersingleprice;
        sheerpricestring=String.valueOf(sheerfinalprice);
        productnamesheer="Sheer Glow Foundation";

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
        orderMap.put("OrderQty",sheerQtystr);
        orderMap.put("Price",sheerpricestring);
        orderMap.put("Item Name",productnamesheer);
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
                    Toast.makeText(Makeupnars.this, "Order placed!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void buyBlush() {
        blushQtystr = blushQtyText.getText().toString();
        blushprice=Integer.parseInt(blushQtystr);
        blushfinalprice=blushprice*blushsingleprice;
        blushpricestring=String.valueOf(blushfinalprice);
        productnameblush="Blush deep coral with gold pearl";

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
        orderMap.put("OrderQty",blushQtystr);
        orderMap.put("Price",blushpricestring);
        orderMap.put("Item Name",productnameblush);
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
                    Toast.makeText(Makeupnars.this, "Order placed!", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    private void buyPure() {
        pureQtystr = pureQtyText.getText().toString();
        pureprice=Integer.parseInt(pureQtystr);
        purefinalprice=pureprice*puresingleprice;
        purepricestring=String.valueOf(purefinalprice);
        productnamepure="Pure Radiant Tinted Moisturizer";

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
        orderMap.put("OrderQty",pureQtystr);
        orderMap.put("Price",purepricestring);
        orderMap.put("Item Name",productnamepure);
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
                    Toast.makeText(Makeupnars.this, "Order placed!", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    private void buyDolce() {
        dolceQtystr = dolceQtyText.getText().toString();
        dolceprice=Integer.parseInt(dolceQtystr);
        dolcefinalprice=dolceprice*dolcesingleprice;
        dolcepricestring=String.valueOf(dolcefinalprice);
        productnamedolce="Lipstick Dolce Vita dusty rose sheer";

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
        orderMap.put("OrderQty",dolceQtystr);
        orderMap.put("Price",dolcepricestring);
        orderMap.put("Item Name",productnamedolce);
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
                    Toast.makeText(Makeupnars.this, "Order placed!", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    private void buySoft() {
        softQtystr = softQtyText.getText().toString();
        softprice=Integer.parseInt(softQtystr);
        softfinalprice=softprice*softsingleprice;
        softpricestring=String.valueOf(softfinalprice);
        productnamesoft="Soft Matte Complete Concealer";

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
        orderMap.put("OrderQty",softQtystr);
        orderMap.put("Price",softpricestring);
        orderMap.put("Item Name",productnamesoft);
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
                    Toast.makeText(Makeupnars.this, "Order placed!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
