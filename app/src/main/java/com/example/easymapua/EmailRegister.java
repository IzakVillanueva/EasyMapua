package com.example.easymapua;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class EmailRegister extends AppCompatActivity {
    private Button buttonRegister, buttonSign;
    private EditText emailEdit, passwordEdit, passwordEdit2;
    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    private ProgressBar progressBar;
    FirebaseAuth mAuth;
    FirebaseUser mUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_email_register);

        buttonSign = (Button) findViewById(R.id.buttonSignIn);
        buttonRegister = (Button) findViewById(R.id.buttonRegister);
        emailEdit = (EditText) findViewById(R.id.loginEdit);
        passwordEdit = (EditText) findViewById(R.id.editTextPassword);
        passwordEdit2 = (EditText) findViewById(R.id.editTextPassword2);
        progressBar = (ProgressBar) findViewById(R.id.progress);
        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();

        buttonSign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(EmailRegister.this, Login.class));
            }
        });

        buttonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PerforAuth();
            }
        });
    }

    private void PerforAuth() {
        String email = emailEdit.getText().toString();
        String password = passwordEdit.getText().toString();
        String confirmPass = passwordEdit2.getText().toString();

        if(!email.matches(emailPattern)){
            emailEdit.setError("Invalid email entered!");
        }
        else if(password.isEmpty() || password.length()<6){
            passwordEdit.setError("Invalid Password");
        }
        else if(!password.equals(confirmPass)){
            passwordEdit2.setError("Password does not match");
        }
        else{
            progressBar.setVisibility(View.VISIBLE);

            mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful()){
                        progressBar.setVisibility(View.GONE);
                        Intent intent = new Intent(getApplicationContext(), Login.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                        Toast.makeText(EmailRegister.this, "Registration Successful", Toast.LENGTH_SHORT).show();
                    }
                    else{
                        progressBar.setVisibility(View.GONE);
                        Toast.makeText(EmailRegister.this, ""+task.getException(), Toast.LENGTH_SHORT).show();
                    }
                }
            });

        }
    }

    public void buttonOpenFile1(View view){
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT, MediaStore.Downloads.EXTERNAL_CONTENT_URI);
        intent.setType("*/*");
        this.startActivity(intent);

    }

    public void buttonOpenFile2(View view){
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT, MediaStore.Downloads.EXTERNAL_CONTENT_URI);
        intent.setType("*/*");
        this.startActivity(intent);

    }
}