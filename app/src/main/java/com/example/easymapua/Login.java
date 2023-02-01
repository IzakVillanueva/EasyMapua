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

import com.vishnusivadas.advanced_httpurlconnection.PutData;

import org.json.JSONException;
import org.json.JSONObject;

public class Login extends AppCompatActivity {
    private Button buttonLogIn, buttonReg;
    private EditText userEdit;
    private EditText passwordEdit;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        buttonLogIn = (Button) findViewById(R.id.signIn);
        userEdit = findViewById(R.id.loginEdit);
        passwordEdit = findViewById(R.id.editTextPassword);
        progressBar = findViewById(R.id.progress);
        buttonReg = findViewById(R.id.buttonRegister);

        buttonReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Login.this, Register.class));
            }
        });

        buttonLogIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = String.valueOf(userEdit.getText());
                String password = String.valueOf(passwordEdit.getText());

                if(!username.equals("") && !password.equals("")) {
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

                                    if(loginMsg.equals("Login Success")){ //==========================================add if for classification
                                        Toast.makeText(getApplicationContext(),loginMsg,Toast.LENGTH_SHORT).show();
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