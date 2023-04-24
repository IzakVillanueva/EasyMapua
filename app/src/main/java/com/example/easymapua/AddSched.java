
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

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class AddSched extends AppCompatActivity {
    private EditText prof, course, taskT, dateT, start, end, roomT;
    private Button add, cancel, close;
    private ProgressBar progressBar;
    private DatePicker datePicker;
    private String date;
    TimePickerDialog timePickerDialog;
    DatabaseReference databaseReference;
    private long maxIDSched = 0;

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
        databaseReference = FirebaseDatabase.getInstance("https://easymapuaauth-default-rtdb.asia-southeast1.firebasedatabase.app/").getReference().child("Schedule");

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
                addSchedToDB();
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

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                if(snapshot.exists()){
                    maxIDSched = (snapshot.getChildrenCount());
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {

            }
        });
    }

    private void addSchedToDB() {
        String name = String.valueOf(prof.getText());
        String courseCode = String.valueOf(course.getText());
        String task = String.valueOf(taskT.getText());
        String date = String.valueOf(dateT.getText());
        String startTime = String.valueOf(start.getText());
        String endTime = String.valueOf(end.getText());
        String room = String.valueOf(roomT.getText());

        if(!name.isEmpty() && !courseCode.isEmpty() && !task.isEmpty() && !date.isEmpty() && !startTime.isEmpty() && !endTime.isEmpty() && !room.isEmpty()){
            progressBar.setVisibility(View.VISIBLE);
            Schedule inputSched = new Schedule(
                    name,
                    courseCode,
                    task,
                    date,
                    startTime,
                    endTime,
                    room
            );

            databaseReference.child(String.valueOf(maxIDSched+1))
                    .setValue(inputSched).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(Task<Void> task) {
                    progressBar.setVisibility(View.GONE);
                    if(task.isSuccessful()){
                        if(Login.loggedClass.equals("Professor")){
                            Intent intent = new Intent(getApplicationContext(), ProfessorNav.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(intent);
                        }
                        else if(Login.loggedClass.equals("Admin")){
                            Intent intent = new Intent(getApplicationContext(), AdminNav.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(intent);
                        }
                        Toast.makeText(AddSched.this, "Added Schedule to Database", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                    else{
                        Toast.makeText(AddSched.this, "Error adding schedule to database", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
        else{
            Toast.makeText(getApplicationContext(), "All fields are required", Toast.LENGTH_SHORT).show();
        }
    }


}