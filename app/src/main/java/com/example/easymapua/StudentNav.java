package com.example.easymapua;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class StudentNav extends AppCompatActivity {
    private Button viewMenu, addRate, viewRate, viewSched;
    private ImageView ex;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_nav);

        viewMenu = findViewById(R.id.buttonShowMenu);
        addRate = findViewById(R.id.buttonRate);
        viewRate = findViewById(R.id.buttonViewRating);
        viewSched = findViewById(R.id.buttonViewSched);
        ex = findViewById(R.id.imageViewExit);

        ex.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), Login.class));
            }
        });

        addRate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(StudentNav.this, AddRate.class));
            }
        });

        viewMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(StudentNav.this, ViewMenu.class));
            }
        });

        viewRate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(StudentNav.this, ViewRating.class));
            }
        });

        viewSched.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(StudentNav.this, ViewSchedule.class));
            }
        });
    }
}