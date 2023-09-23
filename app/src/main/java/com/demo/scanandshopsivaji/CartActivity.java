package com.demo.scanandshopsivaji;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;

public class CartActivity extends AppCompatActivity {

    MyAdapter myAdapter;
    RecyclerView recyclerView;
    Button pay;
    String s=" ";
    Integer num=0;
    String GOOGLE_PAY_PACKAGE_NAME=
            "com.google.android.apps.nbu.paisa.user";
    int GOOGLE_PAY_REQUEST_CODE=123;
    ArrayList<productModal> list=new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_cart);
       Intent intent=this.getIntent();
        Bundle bundle=intent.getExtras();

        list=(ArrayList<productModal>)bundle.getSerializable("cartItems");

        recyclerView=findViewById(R.id.userlist);
        recyclerView.setHasFixedSize(true);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        myAdapter=new MyAdapter(this,list);
        recyclerView.setAdapter(myAdapter);
        myAdapter.notifyDataSetChanged();

        if (list != null) {
            for (productModal list1 : list) {
                num = num + Integer.parseInt(list1.getTotal());
            }
        } else {
            Toast.makeText(this, "Emptyyyyy  ", Toast.LENGTH_SHORT).show();
        }



        pay=(Button) findViewById(R.id.Pay);
        pay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                s=String.valueOf(num);
                if(s.isEmpty())
                {
                    Toast.makeText(CartActivity.this, "Enter da fool", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Uri uri=new Uri.Builder().scheme("upi").authority("pay").appendQueryParameter("pa","anbazhagan2004@okicici").appendQueryParameter("pn","Akash").
                            appendQueryParameter("mc","").appendQueryParameter("tr","12345678898").appendQueryParameter("tn","Demo pay").appendQueryParameter("am",s).appendQueryParameter("cu","INR").build();
                    Intent intent=new Intent(Intent.ACTION_VIEW);
                    intent.setData(uri);
                    intent.setPackage(GOOGLE_PAY_PACKAGE_NAME);
                    startActivityForResult(intent,GOOGLE_PAY_REQUEST_CODE);
                }
            }
        });
    }

}

