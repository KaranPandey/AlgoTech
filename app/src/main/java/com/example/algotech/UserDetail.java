package com.example.algotech;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class UserDetail extends AppCompatActivity {
    EditText mfullname, memail, mpass, mphone;
    Button bsign;
    FirebaseAuth mAuth;
    ProgressBar progressBar;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_detail);

        mfullname = findViewById(R.id.fullname);
        memail = findViewById(R.id.email);
        mpass = findViewById(R.id.pass);
        mphone = findViewById(R.id.uphone);
        bsign =findViewById(R.id.signIn);

        mAuth = FirebaseAuth.getInstance();
        progressBar = findViewById(R.id.pB);

        bsign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = memail.getText().toString().trim();
                String password = mpass.getText().toString().trim();

                if (TextUtils.isEmpty(email)){
                    memail.setError("Email is required");
                    return;
                }

                if (TextUtils.isEmpty(password)){
                    mpass.setError("Password is required");
                    return;
                }

                if(password.length()<6){
                    mpass.setError("Must be atleast 6 char");
                    return;
                }

                progressBar.setVisibility(View.VISIBLE);

                //register the user
                mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            Toast.makeText(UserDetail.this, "User Created", Toast.LENGTH_SHORT).show();


                            //Direct to UserDetail
                            String uusername = mfullname.getText().toString();
                            String umail = memail.getText().toString();
                            String uphone = mphone.getText().toString();
                            Intent intent =  new Intent(UserDetail.this, Infous.class);
                            intent.putExtra("keyname",uusername);
                            intent.putExtra("keyemail", umail);
                            intent.putExtra("keyphone", uphone);
                            startActivity(intent);


                        }
                        else{
                            Toast.makeText(UserDetail.this,"Error" + task.getException(),Toast.LENGTH_SHORT).show();
                        }
                    }
                });

            }
        });



    }
}