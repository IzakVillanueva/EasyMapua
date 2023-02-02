package com.example.easymapua;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class AdminNav extends AppCompatActivity {
    private Button addFood, addRate, addSc, viewMen, viewRat, viewSc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_nav);

        addFood = findViewById(R.id.button);
        addRate = findViewById(R.id.button2);
        addSc = findViewById(R.id.button3);
        viewMen = findViewById(R.id.button4);
        viewRat = findViewById(R.id.button5);
        viewSc = findViewById(R.id.button6);

        addFood.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), AddFood.class));
            }
        });

        addRate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), AddRate.class));
            }
        });

        addSc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), AddSched.class));
            }
        });

        viewMen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), ViewMenu.class));
            }
        });

        viewRat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), ViewRating.class));
            }
        });

        viewSc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), ViewSchedule.class));
            }
        });
    }
}