package com.example.ql_ban_do_an.View;

import androidx.annotation.NonNull;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.ql_ban_do_an.databinding.ActivitySignupBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;

public class SignupActivity extends BaseActivity {

    ActivitySignupBinding binding;
    private String TAG= "CUAHANGDOAN";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySignupBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        setVariable();
    }

    private void setVariable(){
        binding.BtnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = binding.edtUser.getText().toString();
                String password = binding.edtPass.getText().toString();
                if (password.length() < 6) {
                    Toast.makeText(SignupActivity.this, "your password must be 6 character", Toast.LENGTH_SHORT).show();
                    return;
                }
                mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(SignupActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isComplete()){
                            Log.i(TAG, "on Complete: ");

                        }
                        else {
                            Log.i(TAG, "Failure: ",task.getException());
                            Toast.makeText(SignupActivity.this, "Authentication Failure", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
    }

}