package com.example.ql_ban_do_an.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import com.example.ql_ban_do_an.Controller.CustomAdapterListFood;
import com.example.ql_ban_do_an.Model.Customer;
import com.example.ql_ban_do_an.Model.Foods;
import com.example.ql_ban_do_an.R;
import com.example.ql_ban_do_an.databinding.ActivityBestFoodsBinding;
import com.example.ql_ban_do_an.databinding.ActivityListFoodBinding;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class BestFoodsActivity extends AppCompatActivity {

    ActivityBestFoodsBinding binding;
    ArrayList<Foods> lstBF = new ArrayList<>();
    CustomAdapterListFood adapter;
    ImageButton btnBackMain;
    RecyclerView rvBestFood;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityBestFoodsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        addControl();
        getDataFromFirebase();

        binding.btnBackMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    void addControl() {
        btnBackMain = (ImageButton) findViewById(R.id.btnBackMain);
        rvBestFood = (RecyclerView) findViewById(R.id.rvBestFoods);
    }

    void getDataFromFirebase() {
        FirebaseDatabase database = FirebaseDatabase.getInstance("https://qlbandoan-6f252-default-rtdb.asia-southeast1.firebasedatabase.app");
        DatabaseReference myRef = database.getReference("Foods");

        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                lstBF.clear();
                for (DataSnapshot userSnapshot : snapshot.getChildren()) {
                    Foods food = userSnapshot.getValue(Foods.class);
                    if (food.isBestFood())
                        lstBF.add(food);
                }

                adapter = new CustomAdapterListFood(lstBF);
                binding.rvBestFoods.addItemDecoration(new DividerItemDecoration(BestFoodsActivity.this, DividerItemDecoration.VERTICAL));
                RecyclerView.LayoutManager layoutManager = new GridLayoutManager(BestFoodsActivity.this, 2);

                binding.rvBestFoods.setLayoutManager(layoutManager);
                binding.rvBestFoods.setItemAnimator(new DefaultItemAnimator());
                binding.rvBestFoods.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}