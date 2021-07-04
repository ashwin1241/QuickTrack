package com.example.testapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Groceries extends AppCompatActivity {

    private Button opentabFood;
    private Button opentabCleaning;
    private Button opentabUtensils;
    private Button opentabCosmetics;
    private Button opentabBabycare;
    private Button opentabOthers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_groceries);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Groceries");

        opentabFood = findViewById(R.id.Food);
        opentabFood.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Groceries.this,Food.class));
            }
        });
        opentabCleaning = findViewById(R.id.Cleaners);
        opentabCleaning.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Groceries.this,Cleaning.class));
            }
        });
        opentabUtensils = findViewById(R.id.Utensils);
        opentabUtensils.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Groceries.this,Utensils.class));
            }
        });
        opentabBabycare = findViewById(R.id.Babycare);
        opentabBabycare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Groceries.this,Babycare.class));
            }
        });
        opentabCosmetics = findViewById(R.id.Cosmetics);
        opentabCosmetics.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Groceries.this,Cosmetics.class));
            }
        });
        opentabOthers = findViewById(R.id.Others_groceries);
        opentabOthers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Groceries.this,Others_Groceries.class));
            }
        });
    }
}