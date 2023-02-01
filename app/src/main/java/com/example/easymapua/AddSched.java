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

public class AddSched extends AppCompatActivity {
    private EditText prof, course, taskT, dateT, start, end, roomT;
    private Button add, cancel;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_sched);

        prof = findViewById(R.id.editTextName);
        course = findViewById(R.id.editTextCourse);
        taskT = findViewById(R.id.editTextTask);
        dateT = findViewById(R.id.editTextDate);
        start = findViewById(R.id.editTextStart);
        end = findViewById(R.id.editTextEnd);
        roomT = findViewById(R.id.editTextRoom);
        progressBar = (ProgressBar) findViewById(R.id.progress);
        add = findViewById(R.id.buttonAdd);
        cancel = findViewById(R.id.buttonCancel);

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = String.valueOf(prof.getText());
                String courseCode = String.valueOf(course.getText());
                String task = String.valueOf(taskT.getText());
                String date = String.valueOf(dateT.getText());
                String startTime = String.valueOf(start.getText());
                String endTime = String.valueOf(end.getText());
                String room = String.valueOf(roomT.getText());

                if(!name.equals("") && !courseCode.equals("") && !task.equals("") && !date.equals("") && !startTime.equals("") && !endTime.equals("") && !room.equals("")){
                    progressBar.setVisibility(View.VISIBLE);
                    Handler handler = new Handler(Looper.getMainLooper());
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            //Starting Write and Read data with URL
                            //Creating array for parameters
                            String[] field = new String[7];
                            field[0] = "professor";
                            field[1] = "courseCode";
                            field[2] = "task";
                            field[3] = "date";
                            field[4] = "startTime";
                            field[5] = "endTime";
                            field[6] = "room";
                            //Creating array for data
                            String[] data = new String[7];
                            data[0] = name;
                            data[1] = courseCode;
                            data[2] = task;
                            data[2] = date;
                            data[2] = startTime;
                            data[2] = endTime;
                            data[2] = room;
                            PutData putData = new PutData("http://192.168.1.7/LoginRegister/addsched.php", "POST", field, data);
                            if (putData.startPut()) {
                                if (putData.onComplete()) {
                                    progressBar.setVisibility(View.GONE);
                                    String result = putData.getResult();
                                    if(result.equals("Schedule Successfully Added")){
                                        Toast.makeText(getApplicationContext(),result,Toast.LENGTH_SHORT).show();
                                        Intent intent = new Intent(getApplicationContext(), ProfessorNav.class);
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
                startActivity(new Intent(AddSched.this, ProfessorNav.class));
            }
        });
    }
}