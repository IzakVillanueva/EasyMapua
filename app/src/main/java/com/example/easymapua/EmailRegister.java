package com.example.easymapua;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class EmailRegister extends AppCompatActivity {
    private Button buttonRegister, buttonSign;
    private EditText emailEdit, passwordEdit, passwordEdit2, userEdit;
    private RadioGroup radGroup;
    private RadioButton selectedRadBut;
    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    private ProgressBar progressBar;
    private FirebaseAuth mAuth;
    FirebaseUser mUser;
    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_email_register);

        buttonSign = (Button) findViewById(R.id.buttonSignIn);
        buttonRegister = (Button) findViewById(R.id.buttonRegister);
        emailEdit = (EditText) findViewById(R.id.loginEdit);
        passwordEdit = (EditText) findViewById(R.id.editTextPassword);
        passwordEdit2 = (EditText) findViewById(R.id.editTextPassword2);
        userEdit = (EditText) findViewById(R.id.userEdit);
        radGroup = (RadioGroup) findViewById(R.id.radioGroup);
        progressBar = (ProgressBar) findViewById(R.id.progress);

        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();
        databaseReference = FirebaseDatabase.getInstance("https://easymapuaauth-default-rtdb.asia-southeast1.firebasedatabase.app/").getReference();

        buttonSign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(EmailRegister.this, Login.class));
            }
        });

        buttonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PerformAuth();
            }
        });

        ActivityCompat.requestPermissions(this,
                new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.READ_EXTERNAL_STORAGE},
                PackageManager.PERMISSION_GRANTED);
    }

    /*selectedRadBut = (RadioButton) findViewById(radGroup.getCheckedRadioButtonId());
    String classification = String.valueOf(selectedRadBut.getText());*/

    private void PerformAuth() {
        String email = emailEdit.getText().toString();
        String password = passwordEdit.getText().toString();
        String confirmPass = passwordEdit2.getText().toString();
        String username = userEdit.getText().toString();
        selectedRadBut = (RadioButton) findViewById(radGroup.getCheckedRadioButtonId());
        String classification = String.valueOf(selectedRadBut.getText());

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
                public void onComplete(Task<AuthResult> task) {
                    if(task.isSuccessful()){
                        User user = new User(
                                username,
                                email,
                                classification
                        );

                        databaseReference.child("Users")
                                .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(Task<Void> task) {
                                progressBar.setVisibility(View.GONE);
                                if(task.isSuccessful()){
                                    Intent intent = new Intent(getApplicationContext(), Login.class);
                                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                                    startActivity(intent);
                                    Toast.makeText(EmailRegister.this, "Registration Successful", Toast.LENGTH_SHORT).show();
                                    finish();
                                }
                            }
                        });
                    }
                    else{
                        if(task.getException() instanceof FirebaseAuthUserCollisionException){
                            Toast.makeText(EmailRegister.this, "You are already registered", Toast.LENGTH_SHORT).show();
                        }
                        else{
                            Toast.makeText(EmailRegister.this, ""+task.getException(), Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            });
        }
    }

    @Override
    protected void onStart() {
        super.onStart();

        if(mAuth.getCurrentUser() != null){
            FirebaseAuth.getInstance().signOut();
        }
    }

    public void onRadioButtonClicked(View view) {
        emailEdit = (EditText) findViewById(R.id.loginEdit);
        passwordEdit = (EditText) findViewById(R.id.editTextPassword);
        userEdit = (EditText) findViewById(R.id.userEdit);
        String sEmail = emailEdit.getText().toString();
        String sPassword = passwordEdit.getText().toString();
        String sUser = userEdit.getText().toString();

        buttonRegister = (Button) findViewById(R.id.buttonRegister);

        if(!sEmail.matches("") && !sPassword.matches("") && !sUser.matches("")){
            buttonRegister.setEnabled(true);
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