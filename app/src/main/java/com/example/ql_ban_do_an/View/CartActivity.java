package com.example.ql_ban_do_an.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.ql_ban_do_an.Controller.CartAdapter;
import com.example.ql_ban_do_an.Controller.CategoryAdapter;
import com.example.ql_ban_do_an.Helper.ChangeNumberItemsListener;
import com.example.ql_ban_do_an.Helper.ManagmentCart;
import com.example.ql_ban_do_an.Model.Cart;
import com.example.ql_ban_do_an.Model.Category;
import com.example.ql_ban_do_an.Model.Foods;
import com.example.ql_ban_do_an.R;
import com.example.ql_ban_do_an.databinding.ActivityCartBinding;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class CartActivity extends AppCompatActivity {
    int idMax = 0;
    String customerEmail;
    private ActivityCartBinding binding;
    private RecyclerView.Adapter adapter;
    private ManagmentCart managementCart;
    private double tax;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCartBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        managementCart = new ManagmentCart(this);
        caculateCart();
        initList();
        setVariable();
    }

    private void initList() {
        if (managementCart.getListCart().isEmpty()) {
            binding.txtEmpty.setVisibility(View.VISIBLE);
            binding.scrollviewCart.setVisibility(View.GONE);
        } else {
            binding.txtEmpty.setVisibility(View.GONE);
            binding.scrollviewCart.setVisibility(View.VISIBLE);
        }

        LinearLayoutManager linearlayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        binding.cartView.setLayoutManager(linearlayoutManager);
        adapter = new CartAdapter(managementCart.getListCart(), this, () -> caculateCart());
        binding.cartView.setAdapter(adapter);
    }

    private void caculateCart() {
        double percentTax = 0.02;
        double delivery = 10;
        tax = Math.round(managementCart.getTotalFee() * percentTax * 100) / 100.0;
        double total = Math.round((managementCart.getTotalFee() + tax + delivery) * 100) / 100.0;
        double itemTotal = Math.round(managementCart.getTotalFee() * 100) / 100.0;
        binding.txtTotalFee.setText("$" + itemTotal);
        binding.txtTax.setText("$" + tax);
        binding.txtDelivery.setText("$" + delivery);
        binding.txtTotal.setText("$" + total);
    }

    private void setVariable() {
        binding.backBtn.setOnClickListener(v -> finish());
        binding.btnThanhToan.setOnClickListener(v ->
                getNewCartIdAndPlaceOrder()
        );
    }

    private void getNewCartIdAndPlaceOrder() {
        FirebaseDatabase database = FirebaseDatabase.getInstance("https://qlbandoan-6f252-default-rtdb.asia-southeast1.firebasedatabase.app/");
        DatabaseReference myRef = database.getReference("Cart");

        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot bf : snapshot.getChildren()) {
                    Cart cart = bf.getValue(Cart.class);
                    if (cart != null && cart.getId() > idMax)
                        idMax = cart.getId();
                }
                placeOrder(idMax + 1);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void placeOrder(int newId) {
        double percentTax = 0.02;
        double delivery = 10;

        tax = Math.round(managementCart.getTotalFee() * percentTax * 100) / 100.0;
        double total = Math.round((managementCart.getTotalFee() + tax + delivery) * 100) / 100.0;
        SharedPreferences sharedPreferences = getSharedPreferences("user_email", Context.MODE_PRIVATE);
        customerEmail = sharedPreferences.getString("customerEmail", "");
        // Tạo một đơn hàng mới
        Cart cart = new Cart();
        cart.setId(newId); // Đặt id mới
        cart.setListFood(managementCart.getListCart()); // Lấy danh sách món ăn từ giỏ hàng
        cart.setStatus(false); // Chưa hoàn thành
        cart.setTime(new SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault()).format(new Date())); // Thời gian hiện tại
        cart.setEmail(customerEmail); // Lấy email từ người dùng, hoặc từ thông tin đã đăng nhập
        cart.setTotalPrice(total); // Tổng giá tiền
        // Thêm đơn hàng vào Firebase Database
        FirebaseDatabase database = FirebaseDatabase.getInstance("https://qlbandoan-6f252-default-rtdb.asia-southeast1.firebasedatabase.app/");
        DatabaseReference ordersRef = database.getReference("Cart");
        ordersRef.child(String.valueOf(newId)).setValue(cart)

                .addOnSuccessListener(aVoid -> {
                    Toast.makeText(getApplicationContext(), "Successful", Toast.LENGTH_LONG).show();
                    adapter = new CartAdapter(managementCart.resetListCart(), this, () -> caculateCart());
                    binding.cartView.setAdapter(adapter);
                    binding.txtTotalFee.setText("$0");
                    binding.txtTax.setText("$0");
                    binding.txtDelivery.setText("$10");
                    binding.txtTotal.setText("$0");
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(getApplicationContext(), "Failed", Toast.LENGTH_LONG).show();
                });
    }


}