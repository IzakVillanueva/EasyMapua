package com.example.easymapua;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.content.Intent;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

public class Login extends AppCompatActivity {
    private Button buttonLogIn;
    private RadioGroup radGroup;
    private RadioButton radBut;
    private EditText user;
    private EditText password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        buttonLogIn = (Button) findViewById(R.id.signIn);
        radGroup = (RadioGroup) findViewById(R.id.radioGroup);

        buttonLogIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(radGroup.getCheckedRadioButtonId() == findViewById(R.id.StudentRbt).getId()){
                    startActivity(new Intent(Login.this, StudentNav.class));
                }
                else if(radGroup.getCheckedRadioButtonId() == findViewById(R.id.ProfessorRbt).getId()){
                    startActivity(new Intent(Login.this, ProfessorNav.class));
                }
                else if(radGroup.getCheckedRadioButtonId() == findViewById(R.id.VendorRbt).getId()){
                    startActivity(new Intent(Login.this, VendorNav.class));
                }
            }
        });
    }

    public void onRadioButtonClicked(View view) {
        user = (EditText) findViewById(R.id.loginEdit);
        password = (EditText) findViewById(R.id.editTextPassword);
        String sUser = user.getText().toString();
        String sPassword = user.getText().toString();

        buttonLogIn = (Button) findViewById(R.id.signIn);

        if(!sUser.matches("") && !sPassword.matches("")){
            buttonLogIn.setEnabled(true);
        }
    }
}