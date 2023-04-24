package com.example.easymapua;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.vishnusivadas.advanced_httpurlconnection.PutData;

public class AddRate extends AppCompatActivity {
    private String[] store = {"Jaymin's Kitchen", "Varda Burgers", "Food Hall"};
    private Button add, cancel;
    private EditText subjT, msgT, rating;
    private ProgressBar prog;
    private AutoCompleteTextView autoCompleteTextView;
    private ArrayAdapter<String> adapterItems;
    private String storeSelected = "";
    DatabaseReference databaseReference;
    private long maxIDRate = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_rate);

        add = findViewById(R.id.buttonAdd);
        cancel = findViewById(R.id.buttonCancel);
        subjT = findViewById(R.id.editTextSubject);
        msgT = findViewById(R.id.editTextMessage);
        rating = findViewById(R.id.editTextNumber);
        prog = findViewById(R.id.progressBarRate);
        autoCompleteTextView = findViewById(R.id.autocompleteTextViewRate);
        adapterItems = new ArrayAdapter<String>(this, R.layout.list_item_store, store);
        autoCompleteTextView.setAdapter(adapterItems);
        databaseReference = FirebaseDatabase.getInstance("https://easymapuaauth-default-rtdb.asia-southeast1.firebasedatabase.app/").getReference().child("Ratings");

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addRateToDB();
                /*if(!storeSelected.equals("") && !subject.equals("") && !message.equals("")){
                    prog.setVisibility(View.VISIBLE);
                    Handler handler = new Handler(Looper.getMainLooper());
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            String[] field = new String[5];
                            field[0] = "username";
                            field[1] = "classification";
                            field[2] = "store";
                            field[3] = "subject";
                            field[4] = "message";
                            //Creating array for data
                            String[] data = new String[5];
                            data[0] = Login.loggedUser;
                            data[1] = Login.loggedClass;
                            data[2] = storeSelected;
                            data[3] = subject;
                            data[4] = message;
                            PutData putData = new PutData("http://192.168.1.7/LoginRegister/addrating.php", "POST", field, data);
                            if (putData.startPut()) {
                                if (putData.onComplete()) {
                                    prog.setVisibility(View.GONE);
                                    String result = putData.getResult();
                                    if (result.equals("Successfully added rating")) {
                                        if(Login.loggedClass.equals("Student")){
                                            Toast.makeText(getApplicationContext(), result, Toast.LENGTH_SHORT).show();
                                            startActivity(new Intent(getApplicationContext(), StudentNav.class));
                                            finish();
                                        }
                                        else if(Login.loggedClass.equals("Professor")){
                                            Toast.makeText(getApplicationContext(), result, Toast.LENGTH_SHORT).show();
                                            startActivity(new Intent(getApplicationContext(), ProfessorNav.class));
                                            finish();
                                        }
                                        else if(Login.loggedClass.equals("Admin")){
                                            Toast.makeText(getApplicationContext(), result, Toast.LENGTH_SHORT).show();
                                            startActivity(new Intent(getApplicationContext(), AdminNav.class));
                                            finish();
                                        }
                                    } else {
                                        Toast.makeText(getApplicationContext(), result, Toast.LENGTH_SHORT).show();
                                    }
                                }
                            }
                        }
                    });
                }
                else{
                    Toast.makeText(getApplicationContext(), "All fields required", Toast.LENGTH_SHORT).show();
                }*/
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String a = Login.loggedClass;
                if(a.equals("Student")){
                    startActivity(new Intent(getApplicationContext(), StudentNav.class));
                }
                else if(a.equals("Professor")){
                    startActivity(new Intent(getApplicationContext(), ProfessorNav.class));
                }
                else if(a.equals("Admin")){
                    startActivity(new Intent(getApplicationContext(), AdminNav.class));
                }
            }
        });

        autoCompleteTextView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                storeSelected = parent.getItemAtPosition(position).toString();
            }
        });

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                if(snapshot.exists()){
                    maxIDRate = (snapshot.getChildrenCount());
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {

            }
        });
    }

    private void addRateToDB() {
        String subject = String.valueOf(subjT.getText());
        String message = String.valueOf(msgT.getText());
        String rate = String.valueOf(rating.getText());

        if(rate.isEmpty()){
            rating.setError("Add numerical value to rating");
        }
        else if(subject.isEmpty()){
            subjT.setError("Please add a subject");
        }
        else if(message.isEmpty()){
            msgT.setError("Please add a review");
        }
        else{
            prog.setVisibility(View.VISIBLE);
            Rating inputRate = new Rating(
                    Login.loggedUser,
                    Login.loggedClass,
                    storeSelected,
                    subject,
                    message,
                    rate
            );

            databaseReference.child(String.valueOf(maxIDRate+1))
                    .setValue(inputRate).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(Task<Void> task) {
                    prog.setVisibility(View.GONE);
                    if(task.isSuccessful()){
                        if(Login.loggedClass.equals("Student")){
                            Intent intent = new Intent(getApplicationContext(), StudentNav.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(intent);
                        }
                        else if(Login.loggedClass.equals("Professor")){
                            Intent intent = new Intent(getApplicationContext(), ProfessorNav.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(intent);
                        }
                        else if(Login.loggedClass.equals("Admin")){
                            Intent intent = new Intent(getApplicationContext(), AdminNav.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(intent);
                        }
                        Toast.makeText(AddRate.this, "Added Rating to Database", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                    else{
                        Toast.makeText(AddRate.this, "Error adding rating to database", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }
}