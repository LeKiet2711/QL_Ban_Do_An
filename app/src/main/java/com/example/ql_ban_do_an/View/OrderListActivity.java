package com.example.ql_ban_do_an.View;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ql_ban_do_an.Controller.OrderAdapter;
import com.example.ql_ban_do_an.Model.Cart;
import com.example.ql_ban_do_an.R;
import com.example.ql_ban_do_an.databinding.ActivityOrderListBinding;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class OrderListActivity extends AppCompatActivity {
    ActivityOrderListBinding binding;
    ArrayList<Cart> orderList = new ArrayList<>();
    OrderAdapter orderListAdapter;
    RecyclerView rvOrderList;
    String customerEmail="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityOrderListBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        rvOrderList = findViewById(R.id.rvOrderList);
        SharedPreferences sharedPreferences = getSharedPreferences("user_email", Context.MODE_PRIVATE);
        customerEmail = sharedPreferences.getString("customerEmail", "");

        setupRecyclerView();

        getDataFromFirebase();
    }

    private void setupRecyclerView() {
        orderListAdapter = new OrderAdapter(orderList);
        rvOrderList.setLayoutManager(new LinearLayoutManager(this));
        rvOrderList.setItemAnimator(new DefaultItemAnimator());
        rvOrderList.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        rvOrderList.setAdapter(orderListAdapter);
    }

    private void getDataFromFirebase() {
        FirebaseDatabase database = FirebaseDatabase.getInstance("https://qlbandoan-6f252-default-rtdb.asia-southeast1.firebasedatabase.app/");
        DatabaseReference myRef = database.getReference("Cart");

        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                orderList.clear();
                for (DataSnapshot orderSnapshot : snapshot.getChildren()) {
                    Cart order = orderSnapshot.getValue(Cart.class);
                    if (order != null && order.getEmail() != null && order.getEmail().equals(customerEmail)) {
                        orderList.add(order);
                    }
                }
                orderListAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Handle possible errors.
            }
        });
    }
}
