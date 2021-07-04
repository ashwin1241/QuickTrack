package com.example.testapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

public class MainActivity extends AppCompatActivity {
    TextView fullName,email,phone,verifyMsg2;
    FirebaseAuth fAuth;
    FirebaseFirestore fStore;
    String userId;
    Button resendCode2;
    CardView devedit,todo;
    Button resetPassLocal,changeProfileImage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().setTitle("Dashboard");
        fAuth=FirebaseAuth.getInstance();
        fStore=FirebaseFirestore.getInstance();
        resendCode2=findViewById(R.id.resendCode);
        devedit=findViewById(R.id.InventoryCard);
        todo=findViewById(R.id.todo_id);
        devedit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                jumptoside();
            }
        });
        todo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                jump_todo();
            }
        });
        verifyMsg2=findViewById(R.id.verifyMsg);
        userId=fAuth.getCurrentUser().getUid();
        final FirebaseUser user=fAuth.getCurrentUser();
        if(!user.isEmailVerified()){
            resendCode2.setVisibility(View.VISIBLE);
            verifyMsg2.setVisibility(View.VISIBLE);
            resendCode2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(final View v) {
                    user.sendEmailVerification().addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Toast.makeText(v.getContext(),"Verification link has been sent.",Toast.LENGTH_SHORT).show();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.d("tag", "onFailure: Email not sent" + e.getMessage());
                        }
                    });
                }
            });
        }
        else{
            resendCode2.setVisibility(View.INVISIBLE);
            verifyMsg2.setVisibility(View.INVISIBLE);
        }

    }
    public void logout(View view) {
        FirebaseAuth.getInstance().signOut();//LOGOUT
        startActivity(new Intent(getApplicationContext(),login_activity.class));
        finish();
    }
    public void jumptoside(){
        Intent intent = new Intent(MainActivity.this,Inventory.class);
        startActivity(intent);
    }
    public void jump_todo(){
        Intent intent = new Intent(MainActivity.this,MainActivityReminder.class);
        startActivity(intent);
    }
}