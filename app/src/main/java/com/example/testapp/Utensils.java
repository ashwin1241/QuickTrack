package com.example.testapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

public class Utensils extends AppCompatActivity {

    private FirebaseFirestore dbuts = FirebaseFirestore.getInstance();
    private CollectionReference utnsRef = dbuts.collection("users");
    private FirebaseAuth fAuth;
    private EAdapter adapter;
    private FloatingActionButton savebutton;
    private String asd;

    private FloatingActionButton utensils_button;
    private String search;
    private EditText searchbar_utensils;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_utensils);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Utensils");

        fAuth = FirebaseAuth.getInstance();
        savebutton = findViewById(R.id.add_item_uts);
        savebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Utensils.this, NewItemUtensils.class));
            }
        });

        buildrecyclerview();

        searchbar_utensils=findViewById(R.id.searchbar_utensils);
        utensils_button=findViewById(R.id.utensils_button);

        utensils_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                search= searchbar_utensils.getText().toString();
                Query query = utnsRef.document(asd).collection("utsRef").orderBy("title").startAt(search).endAt(search + "\uf8ff");
                FirestoreRecyclerOptions<Set_item> options = new FirestoreRecyclerOptions.Builder<Set_item>().setQuery(query, Set_item.class).build();
                adapter.updateOptions(options);

            }
        });

    }

    private void buildrecyclerview(){

        asd = fAuth.getCurrentUser().getUid();
        Query query = utnsRef.document(asd).collection("utsRef");
        FirestoreRecyclerOptions<Set_item> options = new FirestoreRecyclerOptions.Builder<Set_item>().setQuery(query, Set_item.class).build();
        adapter = new EAdapter(options,this);
        RecyclerView recyclerView = findViewById(R.id.recviewuts);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);


        adapter.setOnItemClickListener(new EAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(DocumentSnapshot documentSnapshot, int position) {
                Set_item note = documentSnapshot.toObject(Set_item.class);
                shareitem(note);
            }
        });




    }

    private void shareitem(Set_item note) {
        Intent myIntent= new Intent(Intent.ACTION_SEND);
        myIntent.setType("text/plain");
        String shareBody=note.getTitle();
        String sharebody1=note.getInitqty();
        String img= note.getImgres();
        myIntent.putExtra(Intent.EXTRA_TEXT,"Item Name: "+shareBody+"\n"+ "Available Quantity: "+sharebody1+"\n"+ "image url: "+img);
        String shareSub="UTENSILS ITEM SHARING";
        myIntent.putExtra(Intent.EXTRA_SUBJECT,shareSub);



        startActivity(Intent.createChooser(myIntent,"Share using"));
    }

    @Override
    protected void onStart() {
        super.onStart();
        adapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        adapter.stopListening();
    }

}