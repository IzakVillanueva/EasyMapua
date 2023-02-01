package com.example.easymapua;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;

public class ViewMenu extends AppCompatActivity {
    String[] store = {"test", "tite", "hooooy"};
    AutoCompleteTextView autoCompleteTextView;
    ArrayAdapter<String> adapterItems;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_menu);

        autoCompleteTextView = findViewById(R.id.autocompleteTextView);
        //adapterItems = new ArrayAdapter<String>(this, R.layout);
    }
}