package com.example.easymapua;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;

public class Register extends AppCompatActivity {
    private Button buttonRegister;
    private Button buttonSign;
    private RadioGroup radGroup;
    private EditText user;
    private EditText password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        buttonSign = (Button) findViewById(R.id.buttonSignIn);
        buttonRegister = (Button) findViewById(R.id.buttonRegister);
        radGroup = (RadioGroup) findViewById(R.id.radioGroup);

        buttonSign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Register.this, Login.class));
            }
        });

        buttonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(radGroup.getCheckedRadioButtonId() == findViewById(R.id.StudentRbt).getId()){
                    startActivity(new Intent(Register.this, StudentNav.class));
                }
                else if(radGroup.getCheckedRadioButtonId() == findViewById(R.id.ProfessorRbt).getId()){
                    startActivity(new Intent(Register.this, ProfessorNav.class));
                }
                else if(radGroup.getCheckedRadioButtonId() == findViewById(R.id.VendorRbt).getId()){
                    startActivity(new Intent(Register.this, VendorNav.class));
                }
            }
        });
    }

    public void onRadioButtonClicked(View view) {
        user = (EditText) findViewById(R.id.loginEdit);
        password = (EditText) findViewById(R.id.editTextPassword);
        String sUser = user.getText().toString();
        String sPassword = user.getText().toString();

        buttonRegister = (Button) findViewById(R.id.buttonRegister);

        if(!sUser.matches("") && !sPassword.matches("")){
            buttonRegister.setEnabled(true);
        }
    }
}