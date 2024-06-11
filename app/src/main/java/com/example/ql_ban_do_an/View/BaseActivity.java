package com.example.ql_ban_do_an.View;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.ql_ban_do_an.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class BaseActivity extends AppCompatActivity {
    public String TAG = "CUAHANGDOAN";
    FirebaseAuth mAuth;
    FirebaseDatabase database;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_basic);

        database = FirebaseDatabase.getInstance();
        mAuth = FirebaseAuth.getInstance();
        getWindow().setStatusBarColor(getResources().getColor(R.color.white));
    }
}