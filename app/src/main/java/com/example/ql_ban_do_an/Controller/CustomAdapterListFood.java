package com.example.ql_ban_do_an.Controller;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.RoundedCorner;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ql_ban_do_an.Model.Foods;
import com.example.ql_ban_do_an.R;
import com.example.ql_ban_do_an.View.DetailFoodActivity;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class CustomAdapterListFood extends RecyclerView.Adapter<CustomAdapterListFood.myViewHolder> {

    ArrayList<Foods> lstFood = new ArrayList<>();
    Context context;
    private OnItemClickListener itemClickListener;
    public CustomAdapterListFood(ArrayList<Foods> lstFood) {
        this.lstFood = lstFood;
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.itemClickListener = listener;
    }

    @NonNull
    @Override
    public myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        View itemView = LayoutInflater.from(context).inflate(R.layout.layout_item_listfood, parent, false);
        return new myViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull myViewHolder holder, int position) {
        Foods foods = lstFood.get(position);
        holder.tvTitle.setText(foods.getTitle());
        holder.tvTime.setText(String.valueOf(foods.getTimeValue()) + " min");
        holder.tvPrice.setText("$" + String.valueOf(foods.getPrice()));
        holder.tvRate.setText(String.valueOf(foods.getStar()));

        Picasso.with(context)
                .load(foods.getImagePath())
                .into(holder.imgListFood);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, DetailFoodActivity.class);

                Bundle bundle = new Bundle();
                bundle.putString("des", foods.getDescription());
                bundle.putString("img", foods.getImagePath());
                bundle.putString("title", foods.getTitle());
                bundle.putDouble("price", foods.getPrice());
                bundle.putDouble("star", foods.getStar());
                bundle.putInt("time", foods.getTimeValue());

                intent.putExtra("object", bundle);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return lstFood.size();
    }

    public interface OnItemClickListener {
        void onItemClick(Foods foods);

    }

    public class myViewHolder extends RecyclerView.ViewHolder {

        ImageView imgListFood;
        TextView tvTitle, tvTime, tvPrice, tvRate;

        public myViewHolder(@NonNull View itemView) {
            super(itemView);
            imgListFood = (ImageView) itemView.findViewById(R.id.imgListFood);
            tvTitle = (TextView) itemView.findViewById(R.id.tvTitle);
            tvTime = (TextView) itemView.findViewById(R.id.tvTime);
            tvPrice = (TextView) itemView.findViewById(R.id.tvPrice);
            tvRate = (TextView) itemView.findViewById(R.id.tvRate);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (itemClickListener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            Foods foods = lstFood.get(position);
                            itemClickListener.onItemClick(foods);
                        }
                    }
                }
            });
        }
    }
}
