package com.example.easymapua;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.vishnusivadas.advanced_httpurlconnection.PutData;

public class AddFood extends AppCompatActivity {
    private Button add, cancel;
    private EditText storeT, foodT, priceT;
    ProgressBar prog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_food);

        add = findViewById(R.id.buttonAdd);
        cancel = findViewById(R.id.buttonCancel);
        storeT = findViewById(R.id.editTextStore);
        foodT = findViewById(R.id.editTextFood);
        priceT = findViewById(R.id.editTextPrice);
        prog = findViewById(R.id.progressBarFood);

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String store = String.valueOf(storeT.getText());
                String food = String.valueOf(foodT.getText());
                String price = String.valueOf(priceT.getText());

                if(!store.equals("") && !food.equals("") && !price.equals("")){
                    prog.setVisibility(View.VISIBLE);
                    Handler handler = new Handler(Looper.getMainLooper());
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            String[] field = new String[3];
                            field[0] = "store";
                            field[1] = "food";
                            field[2] = "price";
                            //Creating array for data
                            String[] data = new String[3];
                            data[0] = store;
                            data[1] = food;
                            data[2] = price;
                            PutData putData = new PutData("http://192.168.1.7/LoginRegister/addfood.php", "POST", field, data);
                            if (putData.startPut()) {
                                if (putData.onComplete()) {
                                    prog.setVisibility(View.GONE);
                                    String result = putData.getResult();
                                    if(result.equals("Successfully added food")){
                                        Toast.makeText(getApplicationContext(),result,Toast.LENGTH_SHORT).show();
                                        Intent intent = new Intent(getApplicationContext(), VendorNav.class);
                                        startActivity(intent);
                                        finish();
                                    }
                                    else{
                                        Toast.makeText(getApplicationContext(),result,Toast.LENGTH_SHORT).show();
                                    }
                                }
                            }
                        }
                    });
                }
                else{
                    Toast.makeText(getApplicationContext(), "All fields required", Toast.LENGTH_SHORT).show();
                }
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AddFood.this, VendorNav.class));
            }
        });
    }
}