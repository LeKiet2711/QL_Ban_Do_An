// com.example.ql_ban_do_an.Controller.OrderListAdapter.java
package com.example.ql_ban_do_an.Controller;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ql_ban_do_an.Model.Cart;

import com.example.ql_ban_do_an.R;
import com.example.ql_ban_do_an.View.DetailFoodActivity;
import com.example.ql_ban_do_an.View.DetailOrderActivity;
import com.example.ql_ban_do_an.View.ListFoodActivity;

import java.util.List;

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.viewHolder> {
    private List<Cart> orderList;

    Context context;
    public OrderAdapter(List<Cart> orderList) {
        this.orderList = orderList;
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context=parent.getContext();
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_item_list_order, parent, false);
        return new viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {
        Cart order = orderList.get(position);
        holder.tvOrderId.setText("Mã đơn hàng:"+String.valueOf(order.getId()));
        String trangThai="";
        if (order.getStatus()) {
            trangThai = "Đã duyệt";
            holder.tvStatus.setTextColor(ContextCompat.getColor(context, R.color.green));
        } else {
            trangThai = "Đang chờ";
            holder.tvStatus.setTextColor(ContextCompat.getColor(context, R.color.red));
        }

        holder.tvStatus.setText("Trạng thái:"+trangThai);
        holder.tvOrderDate.setText("Thời gian: "+order.getTime());
        holder.tvTotalAmount.setText("Tổng tiền: $"+String.valueOf(order.getTotalPrice()));
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, DetailOrderActivity.class);

                Bundle bundle = new Bundle();
                bundle.putInt("order_id", order.getId());
                bundle.putString("order_date",order.getTime());
                bundle.putBoolean("order_status",order.getStatus());
                bundle.putDouble("order_total",order.getTotalPrice());
                intent.putExtra("bundle_order", bundle);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return orderList.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder {
        TextView tvOrderId, tvStatus, tvTotalAmount, tvOrderDate;

        public viewHolder(@NonNull View itemView) {
            super(itemView);
            tvOrderId = itemView.findViewById(R.id.tvOrderId);
            tvStatus = itemView.findViewById(R.id.tvStatus);
            tvTotalAmount = itemView.findViewById(R.id.tvTotalPrice);
            tvOrderDate = itemView.findViewById(R.id.tvOrderDate);
        }


    }









}
