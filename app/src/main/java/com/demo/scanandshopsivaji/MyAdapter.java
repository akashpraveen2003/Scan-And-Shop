package com.demo.scanandshopsivaji;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Locale;

import de.hdodenhof.circleimageview.CircleImageView;

public class MyAdapter  extends RecyclerView.Adapter<MyAdapter.MyviewHolder> {

    Context context;
    ArrayList<productModal> list;

    public MyAdapter(Context context, ArrayList<productModal> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public MyviewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.cart_items,parent,false);
        return new MyviewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyviewHolder holder, int position) {
        productModal user1 = list.get(position);

        holder.price.setText("PRICE : "+user1.getPrice());
        holder.name.setText("NAME : "+user1.getName().toUpperCase());

        holder.discount.setText("DISCOUNT : "+user1.getDiscount());
        holder.total.setText("FINAL PRICE : "+user1.getTotal());
        Picasso.get().load(user1.getImage()).into(holder.image);


    }




    @Override
    public int getItemCount() {
        return list != null ? list.size() : 0;
    }


    public class MyviewHolder extends RecyclerView.ViewHolder
    {
        TextView name,price,total,discount;
        CircleImageView image;

        public MyviewHolder(@NonNull View itemView) {
            super(itemView);
            name =(TextView) itemView.findViewById(R.id.name_textview);
            price=(TextView) itemView.findViewById(R.id.price_textview);
            discount=(TextView) itemView.findViewById(R.id.discount_textview);
            total=(TextView) itemView.findViewById(R.id.total_textview);
            image=(CircleImageView) itemView.findViewById(R.id.imageview);
        }
    }
}