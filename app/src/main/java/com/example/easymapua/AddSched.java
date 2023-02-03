
package com.example.easymapua;



import androidx.appcompat.app.AppCompatActivity;

import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TimePicker;
import android.widget.Toast;

import com.vishnusivadas.advanced_httpurlconnection.PutData;

public class AddSched extends AppCompatActivity {
    private EditText prof, course, taskT, dateT, start, end, roomT;
    private Button add, cancel, close;
    private ProgressBar progressBar;
    private DatePicker datePicker;
    private String date;
    TimePickerDialog timePickerDialog;

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
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        add = findViewById(R.id.buttonAdd);
        cancel = findViewById(R.id.buttonCancel);
        datePicker = findViewById(R.id.datePicker);
        close = findViewById(R.id.buttonClose);

        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                timePickerDialog = new TimePickerDialog(AddSched.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        start.setText(String.format("%02d:%02d", hourOfDay, minute));
                    }
                }, 0, 0, true);
                timePickerDialog.show();
            }
        });

        end.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                timePickerDialog = new TimePickerDialog(AddSched.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        end.setText(String.format("%02d:%02d", hourOfDay, minute));
                    }
                }, 0, 0, true);
                timePickerDialog.show();
            }
        });

        dateT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                datePicker.setVisibility(View.VISIBLE);
                close.setVisibility(View.VISIBLE);
            }
        });

        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                date =  datePicker.getMonth() + "/" + datePicker.getDayOfMonth() + "/" + datePicker.getYear();
                dateT.setText(date);

                datePicker.setVisibility(View.GONE);
                close.setVisibility(View.GONE);
            }
        });

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
                            field[1] = "coursecode";
                            field[2] = "task";
                            field[3] = "date";
                            field[4] = "starttime";
                            field[5] = "endtime";
                            field[6] = "room";
                            //Creating array for data
                            String[] data = new String[7];
                            data[0] = name;
                            data[1] = courseCode;
                            data[2] = task;
                            data[3] = date;
                            data[4] = startTime;
                            data[5] = endTime;
                            data[6] = room;
                            PutData putData = new PutData("http://192.168.11.156/LoginRegister/addsched.php", "POST", field, data);
                            if (putData.startPut()) {
                                if (putData.onComplete()) {
                                    progressBar.setVisibility(View.GONE);
                                    String result = putData.getResult();
                                    if(result.equals("Schedule Successfully Added")){
                                        if(Login.loggedClass.equals("Professor")){
                                            Toast.makeText(getApplicationContext(),result,Toast.LENGTH_SHORT).show();
                                            Intent intent = new Intent(getApplicationContext(), ProfessorNav.class);
                                            startActivity(intent);
                                            finish();
                                        }
                                        else if(Login.loggedClass.equals("Admin")){
                                            Toast.makeText(getApplicationContext(),result,Toast.LENGTH_SHORT).show();
                                            Intent intent = new Intent(getApplicationContext(), AdminNav.class);
                                            startActivity(intent);
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

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Login.loggedClass.equals("Professor")){
                    startActivity(new Intent(AddSched.this, ProfessorNav.class));
                }
                else if(Login.loggedClass.equals("Admin")){
                    startActivity(new Intent(AddSched.this, AdminNav.class));
                }
            }
        });
    }
}