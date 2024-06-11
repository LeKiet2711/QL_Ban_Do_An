package com.example.ql_ban_do_an.View;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ql_ban_do_an.Controller.CategoryAdapter;

import com.example.ql_ban_do_an.Controller.CustomAdapterDetailOrder;
import com.example.ql_ban_do_an.Helper.ManagmentCart;
import com.example.ql_ban_do_an.Model.Cart;

import com.example.ql_ban_do_an.Model.Foods;
import com.example.ql_ban_do_an.R;
import com.example.ql_ban_do_an.databinding.ActivityDetailOrderBinding;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class DetailOrderActivity extends AppCompatActivity {
    TextView tvOrderID, tvTime, tvTotalPrice, tvStatus, tvOrderDetailTax, tvOrderDetailFee;
    int orderID;
    ActivityDetailOrderBinding binding;
    ImageButton btnBackOrder;
    private RecyclerView rvOrderDetailItems;
    private CustomAdapterDetailOrder adapter;
    private ArrayList<Foods> foodItemList; // Retrieve this list based on the order id
    private Cart order;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDetailOrderBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        tvOrderID = (TextView) findViewById(R.id.tvOrderDetailId);
        tvTime = (TextView) findViewById(R.id.tvOrderDetailDate);
        tvStatus = (TextView) findViewById(R.id.tvOrderDetailStatus);
        tvTotalPrice = (TextView) findViewById(R.id.tvOrderDetailTotal);
        btnBackOrder = (ImageButton) findViewById(R.id.btnBackOrder);
        tvOrderDetailTax = (TextView) findViewById(R.id.tvOrderDetailTax);
        tvOrderDetailFee = (TextView) findViewById(R.id.tvOrderDetailFee);


        foodItemList = new ArrayList<>();
        rvOrderDetailItems = findViewById(R.id.rvOrderDetailItems);
        Bundle bundle = getIntent().getBundleExtra("bundle_order");
        orderID = bundle.getInt("order_id");
        tvOrderID.setText("Mã đơn hàng: " + String.valueOf(orderID));
        tvTime.setText(bundle.getString("order_date"));

        String trangThai = "";
        if (bundle.getBoolean("order_status") == true)
            trangThai = "Đã duyệt";
        else
            trangThai = "Đang chờ";
        tvStatus.setText("Trạng thái: " + trangThai);
        tvTotalPrice.setText("Tổng tiền: $" + bundle.getDouble("order_total"));

        // Retrieve the order_id from the intent safely
        getDataFromFirebase();
        binding.btnBackOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }

    void getDataFromFirebase() {
        FirebaseDatabase database = FirebaseDatabase.getInstance("https://qlbandoan-6f252-default-rtdb.asia-southeast1.firebasedatabase.app");
        DatabaseReference myRef = database.getReference("Cart");


        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                foodItemList.clear();
                for (DataSnapshot userSnapshot : snapshot.getChildren()) {

                    Cart c = userSnapshot.getValue(Cart.class);

                    if (c.getId() == orderID) {

                        foodItemList = c.getListFood();
                        double percentTax = 0.02;
                        double delivery = 10;
                        double sum = 0;
                        for (Foods foods : foodItemList)
                            sum += foods.getPrice();
                        double tax = Math.round(sum * percentTax * 100) / 100.0;

                        tvOrderDetailFee.setText("Phí vận chuyển: $" + String.valueOf(delivery));
                        tvOrderDetailTax.setText("Thuế: $" + String.valueOf(tax));

                        Log.d("OrderDetailActivity", "Order ID: " + c.getId());
                        Log.d("OrderDetailActivity", "Food item list size: " + c.getListFood().size());

                        break;
                    }


                }

                binding.rvOrderDetailItems.setLayoutManager(new GridLayoutManager(DetailOrderActivity.this, 1));
                CustomAdapterDetailOrder adapter1 = new CustomAdapterDetailOrder(foodItemList);
                binding.rvOrderDetailItems.setAdapter(adapter1);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}
