package com.demo.scanandshopsivaji;

//import androidx.activity.result.ActivityResultLauncher;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.journeyapps.barcodescanner.ScanContract;
import com.journeyapps.barcodescanner.ScanOptions;

import java.util.ArrayList;

public class HomeActivity extends AppCompatActivity  implements View.OnClickListener{

    Button ScanButton, CartButton, ProfileButton;
    TextView tv1;
    IntentResult result;
    DatabaseReference reference;
    AlertDialog.Builder builder;
    IntentIntegrator intent;
    ArrayList<productModal> cartItems = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        getSupportActionBar().setTitle("Scan And Shop");
        ScanButton = (Button) findViewById(R.id.ScanButton);

        CartButton = (Button) findViewById(R.id.CartButton);
        ScanButton.setOnClickListener(this);
        CartButton.setOnClickListener(this);
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ScanButton:
                scancode();
                break;
            case R.id.CartButton:
                Intent i=new Intent(HomeActivity.this,CartActivity.class);
                i.putExtra("cartItems", cartItems);
                startActivity(i);
                break;

        }
    }


    private void scancode() {

        intent = new IntentIntegrator(HomeActivity.this);
        intent.setPrompt("Scan the barcode");
        intent.setBeepEnabled(true);
        intent.setOrientationLocked(true);
        intent.setCaptureActivity(BarcodeScanner.class);
        Scan(intent);

    }
    void Scan(IntentIntegrator intt)
    {
        intt.initiateScan();
    }


    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        IntentResult result = IntentIntegrator.parseActivityResult(
                requestCode, resultCode, data
        );
     //   progressBar.setVisibility(View.VISIBLE);

      if (result.getContents() != null) {
            builder   = new AlertDialog.Builder(
                    HomeActivity.this
            );

            String s = result.getContents();
        }
        reference= FirebaseDatabase.getInstance().getReference("Barcode").child(result.getContents());
        reference.addValueEventListener(new ValueEventListener()
        {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot)
            {
                if (snapshot.exists())
                    {
                String  display_price=snapshot.child("price").getValue(String.class).toUpperCase();
                String display_name=snapshot.child("name").getValue(String.class).toUpperCase();
                String display_discount=snapshot.child("discount").getValue(String.class).toUpperCase();
                String price=snapshot.child("price").getValue(String.class).toUpperCase();
                String total=snapshot.child("total").getValue(String.class).toUpperCase();
                String display_image=snapshot.child("image").getValue(String.class);
                builder.setTitle("FINAL RESULT");


                builder.setMessage("PRODUCT NAME : "+display_name+" \n "+"PRODUCT PRICE : "+display_price+"\n"+"PRODUCT DISCOUNT :"+display_discount+"\n"+"PRICE :"+price+" \n"+"Total :"+total);


                builder.setPositiveButton("Scan Again", new DialogInterface.OnClickListener()
                            {
                            @Override

                            public void onClick(DialogInterface dialog, int which) {

                                dialog.dismiss();
                                intent.initiateScan();
                            }
                               }).
                        setNegativeButton("Add To Cart", new DialogInterface.OnClickListener()
                        {
                            @Override
                            public void onClick(DialogInterface dialog, int which)
                            {

                                productModal product = new productModal(display_name,display_price,display_image,display_discount,total);

                                // Add the selected product to the cart
                                cartItems.add(product);

                                Toast.makeText(getApplicationContext(), "Item added to cart  ", Toast.LENGTH_SHORT).show();
                                dialog.dismiss();

                            }

                        }).show();
                }
                else
                {
                    builder.setTitle("FINAL RESULT");
                    builder.setMessage("The scanned barcode is not present in our Database");
                    builder.setPositiveButton("Scan Again", new DialogInterface.OnClickListener()
                    {
                        @Override

                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                            intent.initiateScan();
                        }
                    }).show();
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error)
            {
                System.out.println("Failed "+ error.getDetails());
            }

        });

    }
}
