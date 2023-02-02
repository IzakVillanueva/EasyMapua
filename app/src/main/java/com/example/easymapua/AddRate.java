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

import com.vishnusivadas.advanced_httpurlconnection.PutData;

public class AddRate extends AppCompatActivity {
    private String[] store = {"Jaymin's Kitchen", "Varda Burgers", "Food Hall"};
    private Button add, cancel;
    private EditText subjT, msgT;
    private ProgressBar prog;
    private AutoCompleteTextView autoCompleteTextView;
    private ArrayAdapter<String> adapterItems;
    private String storeSelected = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_rate);

        add = findViewById(R.id.buttonAdd);
        cancel = findViewById(R.id.buttonCancel);
        subjT = findViewById(R.id.editTextSubject);
        msgT = findViewById(R.id.editTextMessage);
        prog = findViewById(R.id.progressBarRate);
        autoCompleteTextView = findViewById(R.id.autocompleteTextViewRate);
        adapterItems = new ArrayAdapter<String>(this, R.layout.list_item_store, store);
        autoCompleteTextView.setAdapter(adapterItems);

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String subject = String.valueOf(subjT.getText());
                String message = String.valueOf(msgT.getText());
                if(!storeSelected.equals("") && !subject.equals("") && !message.equals("")){
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
                }
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
    }
}