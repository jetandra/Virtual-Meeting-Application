package com.example.virtualmeet;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {
    FirebaseAuth auth;
    EditText emailBox, passwordBox;
    Button loginBtn, signupBtn;
    ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        dialog = new ProgressDialog(this);
        dialog.setTitle("Virtual Meet");
        dialog.setMessage("Login Account...");
      auth = FirebaseAuth.getInstance();
     emailBox = findViewById(R.id.emailBox);
     passwordBox = findViewById(R.id.passwordBox);
     loginBtn = findViewById(R.id.loginBtn);
     signupBtn = findViewById(R.id.creatBtn);

     loginBtn.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View view) {


             if (emailBox.getText().toString().isEmpty()){
                 emailBox.setError("Please enter your email");
                 return;
             }
             if (passwordBox.getText().toString().isEmpty()){
                 passwordBox.setError("Please enter your password");
                 return;
             }


             dialog.show();
             String email, password;
             email = emailBox.getText().toString();
             password = passwordBox.getText().toString();
             auth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                 @Override
                 public void onComplete(@NonNull Task<AuthResult> task) {
                     dialog.dismiss();
                    if (task.isSuccessful()){
                        startActivity(new Intent(LoginActivity.this,dashboardActivity.class));
                        finish();
                        Toast.makeText(LoginActivity.this, "Login success", Toast.LENGTH_SHORT).show();
                    }else {
                        Toast.makeText(LoginActivity.this, task.getException().getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                    }
                 }
             });
         }
     });

     signupBtn.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View view) {
             startActivity(new Intent(LoginActivity.this,SignupActivity.class));
             finish();
         }
     });


        if (auth.getCurrentUser() != null) {
            Intent intent = new Intent(LoginActivity.this, dashboardActivity.class);
            startActivity(intent);
            finish();
        }



    }

}