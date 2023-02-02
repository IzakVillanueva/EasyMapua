package com.example.easymapua;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class StudentNav extends AppCompatActivity {
    private Button menu, rate, viewRate, viewSched;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_nav);

        menu = findViewById(R.id.buttonShowMenu);
        rate = findViewById(R.id.buttonRate);
        viewRate = findViewById(R.id.buttonViewRating);
        viewSched = findViewById(R.id.buttonViewSched);

        rate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(StudentNav.this, AddRate.class));
            }
        });

        menu.setOnClickListener(new View.OnClickListener() {
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