package com.example.easymapua;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.Button;
import android.content.Intent;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.vishnusivadas.advanced_httpurlconnection.PutData;

import org.json.JSONException;
import org.json.JSONObject;

public class Login extends AppCompatActivity {
    private Button buttonLogIn, buttonReg;
    private EditText userEdit;
    private EditText passwordEdit;
    private ProgressBar progressBar;
    public static String loggedUser, loggedClass;
    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    FirebaseAuth mAuth;
    FirebaseUser mUser;
    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        buttonLogIn = (Button) findViewById(R.id.signIn);
        userEdit = findViewById(R.id.loginEdit);
        passwordEdit = findViewById(R.id.editTextPassword);
        progressBar = findViewById(R.id.progress);
        buttonReg = findViewById(R.id.buttonRegister);

        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();
        databaseReference = FirebaseDatabase.getInstance("https://easymapuaauth-default-rtdb.asia-southeast1.firebasedatabase.app/").getReference();

        buttonReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Login.this, EmailRegister.class));
            }
        });

        buttonLogIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = String.valueOf(userEdit.getText());
                String password = String.valueOf(passwordEdit.getText());

                /*if(!username.equals("") && !password.equals("")) {
                    progressBar.setVisibility(View.VISIBLE);
                    //Start ProgressBar first (Set visibility VISIBLE)
                    Handler handler = new Handler(Looper.getMainLooper());
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            //Starting Write and Read data with URL
                            //Creating array for parameters
                            String[] field = new String[2];
                            field[0] = "username";
                            field[1] = "password";
                            //Creating array for data
                            String[] data = new String[2];
                            data[0] = username;
                            data[1] = password;
                            PutData putData = new PutData("http://192.168.1.7/LoginRegister/login.php", "POST", field, data);
                            if (putData.startPut()) {
                                if (putData.onComplete()) {
                                    progressBar.setVisibility(View.GONE);
                                    String result = putData.getResult();
                                    String classification = "";
                                    String loginMsg = "";
                                    try {
                                        JSONObject jsonObject = new JSONObject(result);
                                        loginMsg = jsonObject.getString("loginMessage");
                                        classification = jsonObject.getString("classification");
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }

                                    if(loginMsg.equals("Login Success")){
                                        Toast.makeText(getApplicationContext(),loginMsg,Toast.LENGTH_SHORT).show();
                                        loggedUser = username;
                                        loggedClass = classification;
                                        if(classification.equals("Student")){
                                            startActivity(new Intent(Login.this, StudentNav.class));
                                            finish();
                                        }
                                        else if(classification.equals("Professor")){
                                            startActivity(new Intent(Login.this, ProfessorNav.class));
                                            finish();
                                        }
                                        else if(classification.equals("Vendor")){
                                            startActivity(new Intent(Login.this, VendorNav.class));
                                            finish();
                                        }
                                        else if(classification.equals("Admin")){
                                            startActivity(new Intent(Login.this, AdminNav.class));
                                            finish();
                                        }
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
                }*/

                performLogin();
            }
        });
    }

    private void performLogin() {
        String email = userEdit.getText().toString();
        String password = passwordEdit.getText().toString();

        if(!email.matches(emailPattern)){
            userEdit.setError("Invalid email entered!");
        }
        else if(password.isEmpty() || password.length()<6){
            passwordEdit.setError("Invalid Password");
        }
        else {
            progressBar.setVisibility(View.VISIBLE);

            mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(Task<AuthResult> task) {
                    if(task.isSuccessful()){
                        progressBar.setVisibility(View.GONE);

                        Query query = databaseReference.child("Users").orderByChild("email").equalTo(email);
                        query.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot snapshot) {
                                if(snapshot.exists()){
                                    DataSnapshot userSnap = snapshot.getChildren().iterator().next();
                                    User user = userSnap.getValue(User.class);
                                    loggedUser = user.getUsername();
                                    loggedClass = user.getClassification();
                                    Toast.makeText(Login.this, "" + loggedClass + loggedUser, Toast.LENGTH_SHORT).show();
                                }
                                else{
                                    Toast.makeText(Login.this, "No existing data", Toast.LENGTH_SHORT).show();
                                }
                            }

                            @Override
                            public void onCancelled(DatabaseError error) {

                            }
                        });

                        Intent intent = new Intent(getApplicationContext(), StudentNav.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                        Toast.makeText(Login.this, "Login Successful", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                    else{
                        progressBar.setVisibility(View.GONE);
                        Toast.makeText(Login.this, ""+task.getException(), Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }

    /*public void onRadioButtonClicked(View view) {
        user = (EditText) findViewById(R.id.loginEdit);
        password = (EditText) findViewById(R.id.editTextPassword);
        String sUser = user.getText().toString();
        String sPassword = user.getText().toString();

        buttonLogIn = (Button) findViewById(R.id.signIn);

        if(!sUser.matches("") && !sPassword.matches("")){
            buttonLogIn.setEnabled(true);
        }
    }*/
}