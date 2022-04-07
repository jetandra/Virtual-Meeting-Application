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
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

public class SignupActivity extends AppCompatActivity {
    FirebaseAuth auth;
    FirebaseFirestore database;
    Button Already_account;
    EditText emailBox, passwordBox, nameBox;
    Button signupBtn;
    ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        database = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();

        dialog = new ProgressDialog(this);
        dialog.setTitle("Virtual Meet");
        dialog.setMessage("Creating Account...");

        Already_account = findViewById(R.id.Already_account);
        nameBox = findViewById(R.id.nameBox);
        emailBox = findViewById(R.id.emailBox);
        passwordBox = findViewById(R.id.passwordBox);
        signupBtn = findViewById(R.id.creatBtn);



        signupBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (nameBox.getText().toString().isEmpty()){
                    nameBox.setError("Please enter your email");
                    return;
                }

                if (emailBox.getText().toString().isEmpty()){
                    emailBox.setError("Please enter your email");
                    return;
                }
                if (passwordBox.getText().toString().isEmpty()){
                    passwordBox.setError("Please enter your password");
                    return;
                }


                dialog.show();
                String email, pass, name;
                email = emailBox.getText().toString();
                pass = passwordBox.getText().toString();
                name = nameBox.getText().toString();

//                Intent intent = new Intent(SignupActivity.this,dashboardActivity.class);
//                intent.putExtra("ProfileName",name);
//                startActivity(intent);
//                finish();

                User user = new User();
                user.setEmail(email);
                user.setPass(pass);
                user.setName(name);

                auth.createUserWithEmailAndPassword(email,pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        dialog.dismiss();
                         if (task.isSuccessful()){
                             database.collection("Users")
                                     .document().set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                                 @Override
                                 public void onSuccess(Void unused) {
                                     startActivity(new Intent(SignupActivity.this,dashboardActivity.class));
                                     finish();
                                 }
                             });
                             Toast.makeText(SignupActivity.this, "Account is created", Toast.LENGTH_SHORT).show();
                         }else {
                             Toast.makeText(SignupActivity.this, task.getException().getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                         }
                    }
                });
            }
        });

        Already_account.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SignupActivity.this,LoginActivity.class));
                finish();
            }
        });


    }
}