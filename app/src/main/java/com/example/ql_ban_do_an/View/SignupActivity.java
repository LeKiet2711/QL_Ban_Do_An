package com.example.ql_ban_do_an.View;

import androidx.annotation.NonNull;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.ql_ban_do_an.Model.Customer;
import com.example.ql_ban_do_an.databinding.ActivitySignupBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class SignupActivity extends BaseActivity {

    ActivitySignupBinding binding;
    List<Customer> lstCus = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySignupBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        getDataFromFireBase();
        setVariable();
    }
    private void setVariable() {
        binding.signupBtn.setOnClickListener(v -> {
            String email = binding.edtUser.getText().toString();
            String password = binding.edtPass.getText().toString();
            if (password.length() < 6) {
                Toast.makeText(SignupActivity.this, "Your password must be 6 character", Toast.LENGTH_SHORT).show();
                return;
            }

            mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(SignupActivity.this, task -> {
                if (task.isComplete()) {
                    Log.i(TAG, "on Complete: ");
                    insertData();
                    startActivity(new Intent(SignupActivity.this, LoginActivity.class));
                } else {
                    Log.i(TAG, "Failure: ", task.getException());
                    Toast.makeText(SignupActivity.this, "Authentication Failure", Toast.LENGTH_SHORT).show();
                }
            });
        });
    }

    private void getDataFromFireBase() {
        FirebaseDatabase database = FirebaseDatabase.getInstance("https://qlbandoan-6f252-default-rtdb.asia-southeast1.firebasedatabase.app");
        DatabaseReference myRef = database.getReference("Customer");

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                lstCus.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    Customer cus = dataSnapshot.getValue(Customer.class);
                    lstCus.add(cus);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void insertData() {
        FirebaseDatabase database = FirebaseDatabase.getInstance("https://qlbandoan-6f252-default-rtdb.asia-southeast1.firebasedatabase.app");
        DatabaseReference myRef = database.getReference("Customer");

        int ma = getIdMax() + 1;
        String ten = binding.edtName.getText().toString();
        String sdt = binding.edtPhone.getText().toString();
        String email = binding.edtUser.getText().toString();
        String dc = binding.edtAddress.getText().toString();

        Customer cus = new Customer(ma, dc, email, ten, sdt);
        myRef.child(String.valueOf(ma)).setValue(cus);
    }

    private int getIdMax() {
        int maxId = -1;
        for (Customer cus : lstCus)
            if (cus.getId() >= maxId)
                maxId = cus.getId();

        return maxId;
    }
}