package com.example.testapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Button;

public class Inventory extends AppCompatActivity {
    private ImageButton gro;
    private ImageButton app;
    private ImageButton sta;
    private ImageButton fur;
    private ImageButton oth;
    private ImageButton maps;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inventory);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Inventory");

        maps=(ImageButton) findViewById(R.id.maps);
        maps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                opengmaps();
            }
        });

        gro = (ImageButton) findViewById(R.id.Groceries);
        gro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                opengro();
            }
        });
        app = (ImageButton) findViewById(R.id.Appliances);
        app.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openapp();
            }
        });
        sta = (ImageButton) findViewById(R.id.Stationery);
        sta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                opensta();
            }
        });
        fur = (ImageButton) findViewById(R.id.Furniture);
        fur.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openfur();
            }
        });
        oth = findViewById(R.id.Others);
        oth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openoth();
            }
        });



    }
    public void opengro(){
        Intent intent = new Intent(this, Groceries.class);
        startActivity(intent);
    }
    public void openapp(){
        Intent intent = new Intent(this, Appliances.class);
        startActivity(intent);
    }
    public void opensta(){
        Intent intent = new Intent(this, Stationery.class);
        startActivity(intent);
    }
    public void openfur(){
        Intent intent = new Intent(this, Furniture.class);
        startActivity(intent);
    }
    public void openoth(){
        Intent intent = new Intent(this,Others.class);
        startActivity(intent);
    }
    public void opengmaps(){
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse("geo:27.2046,77.4977"));
        Intent chooser= Intent.createChooser(intent,"Launch Maps");
        startActivity(chooser);
    }


}