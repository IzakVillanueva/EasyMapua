package com.example.easymapua;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class VendorNav extends AppCompatActivity {
    private Button add, viewRate;
    private ImageView ex;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vendor_nav);

        add = findViewById(R.id.buttonAddFood);
        viewRate = findViewById(R.id.buttonViewRate);
        ex = findViewById(R.id.imageViewExit);

        ex.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), Login.class));
            }
        });

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(VendorNav.this, AddFood.class));
            }
        });

        viewRate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(VendorNav.this, ViewRating.class));
            }
        });
    }
}