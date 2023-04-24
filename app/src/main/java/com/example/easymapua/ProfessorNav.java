package com.example.easymapua;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.google.firebase.auth.FirebaseAuth;

public class ProfessorNav extends AppCompatActivity {
    private Button add, rate, viewSc, viewMe, viewRa;
    private ImageView ex;
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_professor_nav);

        add = findViewById(R.id.buttonAdd);
        rate = findViewById(R.id.buttonRate);
        ex = findViewById(R.id.imageViewExit);
        viewSc = findViewById(R.id.buttonViewSched);
        viewMe = findViewById(R.id.buttonViewMenu);
        viewRa = findViewById(R.id.buttonViewRating);

        viewRa.setOnClickListener(new View.OnClickListener() {
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

        viewMe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), ViewMenu.class));
            }
        });

        ex.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAuth.signOut();
                startActivity(new Intent(getApplicationContext(), Login.class));
                finish();
            }
        });

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ProfessorNav.this, AddSched.class));
            }
        });

        rate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ProfessorNav.this, AddRate.class));
            }
        });
    }
}