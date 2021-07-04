package com.example.testapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Register extends AppCompatActivity {
    public static final String TAG = "TAG";
    EditText mUsername,mEmail,mPassword,mPhone,mProfession;
    Button mRegisterBtn;
    TextView mLoginBtn;
    FirebaseAuth fAAuth;
    ProgressBar progressBar;
    FirebaseFirestore fstore;
    String userID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        getSupportActionBar().setTitle("Register");
        mUsername=findViewById(R.id.username);
        mEmail=findViewById(R.id.gmail_id);
        mPassword=findViewById(R.id.password);
        mPhone=findViewById(R.id.phno);
        mProfession=findViewById(R.id.profession);
        mRegisterBtn=findViewById(R.id.reg_but);
        mLoginBtn=findViewById(R.id.login_but);
        fAAuth=FirebaseAuth.getInstance();
        fstore=FirebaseFirestore.getInstance();
        progressBar=findViewById(R.id.progressBar);
        if(fAAuth.getCurrentUser()!=null)
        {
            startActivity(new Intent(getApplicationContext(),MainActivity.class));
            finish();
        }
        mLoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),login_activity.class));
                finish();
            }
        });
        mRegisterBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email=mEmail.getText().toString().trim();
                String password=mPassword.getText().toString().trim();
                String Username=mUsername.getText().toString().trim();
                String phone="+91"+mPhone.getText().toString().trim();
                String prof=mProfession.getText().toString().trim();
                if(TextUtils.isEmpty(Username))
                {
                    mUsername.setError("Username is required");
                    return;
                }
                String regex = "^[A-Za-z]\\w{5,29}$";
                Pattern p = Pattern.compile(regex);
                Matcher m = p.matcher(Username);
                if(!m.matches())
                {
                    mUsername.setError("Invalid Username");
                    return;
                }
                if(TextUtils.isEmpty(email))
                {
                    mEmail.setError("Gmail-ID is required");
                    return;
                }
                if(password.length()<8)
                {
                    mPassword.setError("Password of length >=8 is required");
                    return;
                }
                progressBar.setVisibility(View.VISIBLE);
                // register the user in firebase
                fAAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){

                            //sending verification link
                            FirebaseUser fuser=fAAuth.getCurrentUser();
                            fuser.sendEmailVerification().addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Toast.makeText(Register.this,"Verification link has been sent.",Toast.LENGTH_SHORT).show();
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Log.d(TAG, "onFailure: Email not sent" + e.getMessage());
                                }
                            });
                            Toast.makeText(Register.this, "User Created", Toast.LENGTH_SHORT).show();
                            userID=fAAuth.getCurrentUser().getUid();
                            DocumentReference documentReference=fstore.collection("users").document(userID);
                            Map<String,Object> user_data=new HashMap<>();
                            user_data.put("username",Username);
                            user_data.put("email",email);
                            user_data.put("phno",phone);
                            user_data.put("profession",prof);
                            documentReference.set(user_data).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Log.d(TAG, "onSuccess: user profile is created for "+userID);
                                }
                            });
                            Intent phone2 = new Intent(getApplicationContext(),VerifyPhone.class);
                            phone2.putExtra("phone",phone);
                            startActivity(phone2);
                        }
                        else{
                            Toast.makeText(Register.this, "Error ! "+ task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            progressBar.setVisibility(View.INVISIBLE);
                        }
                    }
                });

            }
        });
    }
}