package com.example.ql_ban_do_an.Controller;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.example.ql_ban_do_an.Model.Foods;
import com.example.ql_ban_do_an.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class CustomAdapterDetailOrder extends RecyclerView.Adapter<CustomAdapterDetailOrder.OrderDetailViewHolder> {
    private List<Foods> foodItemList;
    Context context;
    public CustomAdapterDetailOrder(List<Foods> foodItemList) {
        this.foodItemList = foodItemList;
    }

    @NonNull
    @Override
    public OrderDetailViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context=parent.getContext();
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_item_order_detail, parent, false);
        return new OrderDetailViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderDetailViewHolder holder, int position) {
        Foods foodItem = foodItemList.get(position);
        Picasso.with(context)
                .load(foodItem.getImagePath())
                .into(holder.ivFoodImage);
        holder.tvFoodName.setText(foodItem.getTitle());
        holder.tvFoodPrice.setText("Giá: $" + String.valueOf(foodItem.getPrice()));
        holder.tvNumberInCart.setText("Số lượng: " + String.valueOf(foodItem.getNumberInCart()));
    }

    @Override
    public int getItemCount() {
        return foodItemList.size();
    }

    public static class OrderDetailViewHolder extends RecyclerView.ViewHolder {
        ImageView ivFoodImage;
        TextView tvFoodName, tvFoodPrice, tvNumberInCart;

        public OrderDetailViewHolder(@NonNull View itemView) {
            super(itemView);
            ivFoodImage = itemView.findViewById(R.id.ivFoodImage);
            tvFoodName = itemView.findViewById(R.id.tvFoodName);
            tvFoodPrice = itemView.findViewById(R.id.tvFoodPrice);
            tvNumberInCart = itemView.findViewById(R.id.tvNumberInCart);
        }
    }
}
