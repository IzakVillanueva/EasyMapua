package com.example.easymapua;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.vishnusivadas.advanced_httpurlconnection.PutData;

public class AddFood extends AppCompatActivity {
    private Button add, cancel;
    private EditText storeT, foodT, priceT;
    ProgressBar prog;
    DatabaseReference databaseReference;
    private long maxIDFood;


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
        databaseReference = FirebaseDatabase.getInstance("https://easymapuaauth-default-rtdb.asia-southeast1.firebasedatabase.app/").getReference().child("Menu");

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addFoodToMenu();
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Login.loggedClass.equals("Vendor")){
                    startActivity(new Intent(getApplicationContext(), VendorNav.class));
                }
                else if(Login.loggedClass.equals("Admin")){
                    startActivity(new Intent(getApplicationContext(), AdminNav.class));
                }
            }
        });

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                if(snapshot.exists()){
                    maxIDFood = (snapshot.getChildrenCount());
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {

            }
        });
    }

    private void addFoodToMenu() {
        String store = String.valueOf(storeT.getText());
        String food = String.valueOf(foodT.getText());
        String price = String.valueOf(priceT.getText());

        if(store.isEmpty()){
            storeT.setError("Please add a store!");
        }
        else if(food.isEmpty()){
            foodT.setError("Please add a food!");
        }
        else if(price.isEmpty()){
            priceT.setError("Please add a price!");
        }
        else{
            prog.setVisibility(View.VISIBLE);
            Food inputFood = new Food(
                    store,
                    food,
                    price
            );

            databaseReference.child(String.valueOf(maxIDFood+1))
                    .setValue(inputFood).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(Task<Void> task) {
                    prog.setVisibility(View.GONE);
                    if(task.isSuccessful()){
                        if(Login.loggedClass.equals("Vendor")){
                            Intent intent = new Intent(getApplicationContext(), VendorNav.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(intent);
                        }
                        else if(Login.loggedClass.equals("Admin")){
                            Intent intent = new Intent(getApplicationContext(), AdminNav.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(intent);
                        }
                        Toast.makeText(AddFood.this, "Added Food to Menu", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                }
            });
        }
    }
}