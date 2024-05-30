package com.example.ql_ban_do_an.Controller;

import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ql_ban_do_an.Helper.ChangeNumberItemsListener;
import com.example.ql_ban_do_an.Helper.ManagmentCart;
import com.example.ql_ban_do_an.Model.Foods;

import java.util.ArrayList;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.viewholder> {
    ArrayList<Foods> list;
    private ManagmentCart managmentCart;
    ChangeNumberItemsListener changeNumberItemsListener;

    public CartAdapter(ArrayList<Foods> list, ChangeNumberItemsListener changeNumberItemsListener) {
        this.list = list;
        this.changeNumberItemsListener = changeNumberItemsListener;
    }

    @NonNull
    @Override
    public CartAdapter.viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull CartAdapter.viewholder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class viewholder extends RecyclerView.ViewHolder{
        public viewholder(@NonNull View itemView) {
            super(itemView);
        }
    }

}
