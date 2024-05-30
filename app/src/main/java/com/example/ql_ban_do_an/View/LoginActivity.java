package com.example.ql_ban_do_an.View;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import com.example.ql_ban_do_an.R;

public class LoginActivity extends AppCompatActivity {

    Button btnSignup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        btnSignup = findViewById(R.id.BtnSignup);

    }
}
