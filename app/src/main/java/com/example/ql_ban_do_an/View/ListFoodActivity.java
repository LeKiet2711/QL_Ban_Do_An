package com.example.ql_ban_do_an.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.ql_ban_do_an.Controller.CustomAdapterListFood;
import com.example.ql_ban_do_an.Model.Foods;
import com.example.ql_ban_do_an.R;
import com.example.ql_ban_do_an.databinding.ActivityListFoodBinding;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ListFoodActivity extends AppCompatActivity {
    ActivityListFoodBinding binding;
    ArrayList<Foods> lstFood = new ArrayList<>();
    CustomAdapterListFood adapterListFood;
    ImageButton btnBack;
    TextView tvTitleA;
    RecyclerView rvListFood;
    private int categoryId;
    private String categoryName;
    private String searchText;
    private boolean isSearch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityListFoodBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        addControl();
        getIntentExtra();
        getDataFromFirebase();
    }

    void addControl() {
        btnBack = (ImageButton) findViewById(R.id.btnBack);
        tvTitleA = (TextView) findViewById(R.id.tvTitleA);
        rvListFood = (RecyclerView) findViewById(R.id.rvListFood);
    }

    void getIntentExtra() {
        categoryId = getIntent().getIntExtra("CategoryId", 0);
        categoryName = getIntent().getStringExtra("CategoryName");
        searchText = getIntent().getStringExtra("text");
        isSearch = getIntent().getBooleanExtra("isSearch", false);

        binding.tvTitleA.setText(categoryName);
        binding.btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    void getDataFromFirebase() {
        FirebaseDatabase database = FirebaseDatabase.getInstance("https://qlbandoan-6f252-default-rtdb.asia-southeast1.firebasedatabase.app");
        DatabaseReference myRef = database.getReference("Foods");

        Query query;

        if (isSearch) {
            query = myRef.orderByChild("Title").startAt(searchText).endAt(searchText + '\uf8ff');
        } else {
            query = myRef.orderByChild("CategoryId").equalTo(categoryId);
        }

        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                lstFood.clear();
                for (DataSnapshot userSnapshot : snapshot.getChildren()) {
                    Foods food = userSnapshot.getValue(Foods.class);
                    lstFood.add(food);
                }

                adapterListFood = new CustomAdapterListFood(lstFood);
                rvListFood.addItemDecoration(new DividerItemDecoration(ListFoodActivity.this, DividerItemDecoration.VERTICAL));
                RecyclerView.LayoutManager layoutManager = new GridLayoutManager(ListFoodActivity.this, 2);

                binding.rvListFood.setLayoutManager(layoutManager);
                binding.rvListFood.setItemAnimator(new DefaultItemAnimator());
                rvListFood.setAdapter(adapterListFood);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}