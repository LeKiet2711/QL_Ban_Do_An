package com.example.ql_ban_do_an.View;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.ql_ban_do_an.Controller.BestFoodsAdapter;
import com.example.ql_ban_do_an.Controller.CategoryAdapter;
import com.example.ql_ban_do_an.Model.Category;
import com.example.ql_ban_do_an.Model.Location;
import com.example.ql_ban_do_an.Model.Price;
import com.example.ql_ban_do_an.Model.Time;
import com.example.ql_ban_do_an.R;
import com.example.ql_ban_do_an.Model.Foods;
import com.example.ql_ban_do_an.databinding.ActivityMainBinding;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    ActivityMainBinding binding;
    //ListView lvKq;
    Spinner vitriSP, timeSP, giaSP;
    RecyclerView bestFoodView;
    //ArrayList<Foods> lstFood = new ArrayList<>();
    ArrayList<String> lsDataTime = new ArrayList<>();
    ArrayList<String> lsDataPrice = new ArrayList<>();
    ArrayList<String> lsDataLocation = new ArrayList<>();
    ArrayList<Location> listLocation = new ArrayList<>();
    ArrayList<Foods> listBestFoods = new ArrayList<>();
    ArrayList<Category> listCategory = new ArrayList<>();
    ArrayList<Time> listTime = new ArrayList<>();
    ArrayList<Price> listPrice = new ArrayList<>();
    ArrayAdapter<String> adapter;

    EditText edt, txtSearch;
    ImageView btnLogout, timkiem_btn, btn_order;
    TextView tvNameCustomer, tvBestFoods;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // EdgeToEdge.enable(this);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        addControls();

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        FirebaseApp.initializeApp(this);

        tvNameCustomer.setText(" ");

        initLocation();
        initTime();
        initPrice();
        initBestFood();
        initCategory();
        setVariable();
    }

    private void setVariable() {
        binding.giohangBtn.setOnClickListener(v -> startActivity(new Intent(MainActivity.this, CartActivity.class)));

        binding.btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(MainActivity.this, IntroActivity.class);
                startActivity(intent);
            }
        });
        timkiem_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, ListFoodActivity.class);
                intent.putExtra("text", txtSearch.getText().toString());
                intent.putExtra("isSearch", true);
                startActivity(intent);
            }
        });
        btn_order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, OrderListActivity.class);

                startActivity(intent);
            }
        });

        binding.tvBestFoods.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, BestFoodsActivity.class);
                startActivity(intent);
            }
        });
    }

    private void initCategory() {
        FirebaseDatabase database = FirebaseDatabase.getInstance("https://qlbandoan-6f252-default-rtdb.asia-southeast1.firebasedatabase.app/");
        DatabaseReference myRef = database.getReference("Category");
        binding.progressBarCategory.setVisibility(View.VISIBLE);

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                listCategory.clear();
                for (DataSnapshot bf : snapshot.getChildren()) {
                    Category cat = bf.getValue(Category.class);
                    listCategory.add(cat);
                }
                if (listCategory.size() > 0) {
                    binding.categoryView.setLayoutManager(new GridLayoutManager(MainActivity.this, 4));
                    RecyclerView.Adapter adapterCat = new CategoryAdapter(listCategory);
                    binding.categoryView.setAdapter(adapterCat);
                    adapterCat.notifyDataSetChanged();
                }
                binding.progressBarCategory.setVisibility(View.GONE);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Xử lý lỗi
            }
        });
    }

    private void initBestFood() {
        FirebaseDatabase database = FirebaseDatabase.getInstance("https://qlbandoan-6f252-default-rtdb.asia-southeast1.firebasedatabase.app/");
        DatabaseReference myRef = database.getReference("Foods");
        binding.progressBarBestFood.setVisibility(View.VISIBLE);

        Query query = myRef.orderByChild("BestFood").equalTo(true);
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                listBestFoods.clear();
                for (DataSnapshot bf : snapshot.getChildren()) {
                    listBestFoods.add(bf.getValue(Foods.class));
                }
                if (listBestFoods.size() > 0) {
                    binding.bestFoodView.setLayoutManager(new LinearLayoutManager(MainActivity.this, LinearLayoutManager.HORIZONTAL, false));
                    RecyclerView.Adapter adapterRV = new BestFoodsAdapter(listBestFoods);
                    binding.bestFoodView.setAdapter(adapterRV);
                    adapterRV.notifyDataSetChanged();
                }
                binding.progressBarBestFood.setVisibility(View.GONE);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Xử lý lỗi
            }
        });
    }


    private void initLocation() {
        FirebaseDatabase database = FirebaseDatabase.getInstance("https://qlbandoan-6f252-default-rtdb.asia-southeast1.firebasedatabase.app/");
        DatabaseReference myRef = database.getReference("Location");


        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                listLocation.clear();
                for (DataSnapshot locationSnapshot : dataSnapshot.getChildren()) {
                    Location location = locationSnapshot.getValue(Location.class);
                    listLocation.add(location);
                }
                lsDataLocation = convertLocationListToArrString(listLocation);
                adapter = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_list_item_1, lsDataLocation);
                binding.vitriSP.setAdapter(adapter);


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void initTime() {
        FirebaseDatabase database = FirebaseDatabase.getInstance("https://qlbandoan-6f252-default-rtdb.asia-southeast1.firebasedatabase.app/");
        DatabaseReference myRef = database.getReference("Time");


        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                listTime.clear();
                for (DataSnapshot timeSnapshot : dataSnapshot.getChildren()) {
                    Time t = timeSnapshot.getValue(Time.class);
                    listTime.add(t);
                }
                lsDataTime = convertTimeListToArrString(listTime);
                adapter = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_list_item_1, lsDataTime);
                binding.timeSP.setAdapter(adapter);


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void initPrice() {
        FirebaseDatabase database = FirebaseDatabase.getInstance("https://qlbandoan-6f252-default-rtdb.asia-southeast1.firebasedatabase.app/");
        DatabaseReference myRef = database.getReference("Price");


        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                listPrice.clear();
                for (DataSnapshot priceSnapshot : dataSnapshot.getChildren()) {
                    Price price = priceSnapshot.getValue(Price.class);
                    listPrice.add(price);
                }
                lsDataPrice = convertPriceListToArrString(listPrice);
                adapter = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_list_item_1, lsDataPrice);
                binding.giaSP.setAdapter(adapter);


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void addControls() {
        // lvKq = (ListView) findViewById(R.id.lvKq);
        vitriSP = (Spinner) findViewById(R.id.vitriSP);
        timeSP = (Spinner) findViewById(R.id.timeSP);
        giaSP = (Spinner) findViewById(R.id.giaSP);
        bestFoodView = (RecyclerView) findViewById(R.id.bestFoodView);
        btnLogout = (ImageView) findViewById(R.id.btnLogout);
        tvNameCustomer = (TextView) findViewById(R.id.tvNameCustomer);
        timkiem_btn = (ImageView) findViewById(R.id.timkiem_btn);
        btn_order = (ImageView) findViewById(R.id.btnOrder);
        txtSearch = (EditText) findViewById(R.id.txtSearch);
        tvBestFoods = (TextView) findViewById(R.id.tvBestFoods);
    }

    ArrayList<String> convertLocationListToArrString(ArrayList<Location> lstLocation) {
        ArrayList<String> lsString = new ArrayList<>();
        for (Location f : lstLocation) {
            String s = "Location: " + f.getLoc();
            lsString.add(s);
        }
        return lsString;
    }

    ArrayList<String> convertTimeListToArrString(ArrayList<Time> lstTime) {
        ArrayList<String> lsString = new ArrayList<>();
        for (Time f : lstTime) {
            String s = f.getValue();
            lsString.add(s);
        }
        return lsString;
    }

    ArrayList<String> convertPriceListToArrString(ArrayList<Price> lstPrice) {
        ArrayList<String> lsString = new ArrayList<>();
        for (Price f : lstPrice) {
            String s = f.getValue();
            lsString.add(s);
        }
        return lsString;
    }


}