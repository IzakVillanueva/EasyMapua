package com.example.easymapua;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.vishnusivadas.advanced_httpurlconnection.PutData;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class ViewSchedule extends AppCompatActivity {
    private TableLayout tbl;
    private Button allB, consulB;
    private ProgressBar prog;
    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_schedule);

        tbl = findViewById(R.id.table_sched);
        allB = findViewById(R.id.buttonLoadAll);
        consulB = findViewById(R.id.buttonLoadConsul);
        prog = findViewById(R.id.progressBarSched);
        databaseReference = FirebaseDatabase.getInstance("https://easymapuaauth-default-rtdb.asia-southeast1.firebasedatabase.app/").getReference().child("Schedule");

        allB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                prog.setVisibility(View.VISIBLE);
                clearTable();
                Handler handler = new Handler(Looper.getMainLooper());
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot snapshot) {
                                if(snapshot.exists()){
                                    for(DataSnapshot schedDataSnap : snapshot.getChildren()){
                                        //Extract the sched object from snapshot
                                        Schedule sched = schedDataSnap.getValue(Schedule.class);
                                        try{
                                            //populate table
                                            TableRow row1 = new TableRow(ViewSchedule.this);

                                            TextView line1 = new TextView(ViewSchedule.this);
                                            line1.setTextAppearance(getApplicationContext(), R.style.userRateTbl);
                                            line1.setText(sched.getProfessor() +
                                                    " (" + sched.getCoursecode() + ")");
                                            line1.setSingleLine(false);
                                            line1.setWidth(TableRow.LayoutParams.WRAP_CONTENT);
                                            row1.addView(line1);

                                            tbl.addView(row1);

                                            TableRow row2 = new TableRow(ViewSchedule.this);

                                            TextView line2 = new TextView(ViewSchedule.this);
                                            line2.setTextAppearance(getApplicationContext(), R.style.subRateTbl);
                                            line2.setText("Task: " + sched.getTask() +
                                                    "   Room: " + sched.getRoom());
                                            line2.setSingleLine(false);
                                            line2.setWidth(TableRow.LayoutParams.WRAP_CONTENT);
                                            row2.addView(line2);

                                            tbl.addView(row2);

                                            TableRow row3 = new TableRow(ViewSchedule.this);

                                            TextView line3 = new TextView(ViewSchedule.this);
                                            line3.setTextAppearance(getApplicationContext(), R.style.msgRateTbl);
                                            line3.setText(sched.getDate() + " - " +
                                                    sched.getStartTime() + " - " +
                                                    sched.getEndTime());
                                            line3.setSingleLine(false);
                                            line3.setWidth(TableRow.LayoutParams.WRAP_CONTENT);
                                            row3.addView(line3);

                                            tbl.addView(row3);

                                            TableRow row4 = new TableRow(ViewSchedule.this);
                                            TextView blank = new TextView(ViewSchedule.this);
                                            blank.setText("");
                                            row4.addView(blank);

                                            tbl.addView(row4);
                                            prog.setVisibility(View.GONE);
                                        } catch (Exception e) {
                                            e.printStackTrace();
                                        }
                                    }
                                }
                            }

                            @Override
                            public void onCancelled(DatabaseError error) {
                                Toast.makeText(ViewSchedule.this, "Query cancelled", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                });
            }
        });

        consulB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                prog.setVisibility(View.VISIBLE);
                clearTable();
                Handler handler = new Handler(Looper.getMainLooper());
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        Query query = databaseReference.orderByChild("task").equalTo("Consultation");
                        query.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot snapshot) {
                                if(snapshot.exists()){
                                    for(DataSnapshot schedDataSnap : snapshot.getChildren()){
                                        //Extract the sched object from snapshot
                                        Schedule sched = schedDataSnap.getValue(Schedule.class);
                                        try{
                                            //populate table (same code as above)
                                            TableRow row1 = new TableRow(ViewSchedule.this);

                                            TextView line1 = new TextView(ViewSchedule.this);
                                            line1.setTextAppearance(getApplicationContext(), R.style.userRateTbl);
                                            line1.setText(sched.getProfessor() +
                                                    " (" + sched.getCoursecode() + ")");
                                            line1.setSingleLine(false);
                                            line1.setWidth(TableRow.LayoutParams.WRAP_CONTENT);
                                            row1.addView(line1);

                                            tbl.addView(row1);

                                            TableRow row2 = new TableRow(ViewSchedule.this);

                                            TextView line2 = new TextView(ViewSchedule.this);
                                            line2.setTextAppearance(getApplicationContext(), R.style.subRateTbl);
                                            line2.setText("Task: " + sched.getTask() +
                                                    "   Room: " + sched.getRoom());
                                            line2.setSingleLine(false);
                                            line2.setWidth(TableRow.LayoutParams.WRAP_CONTENT);
                                            row2.addView(line2);

                                            tbl.addView(row2);

                                            TableRow row3 = new TableRow(ViewSchedule.this);

                                            TextView line3 = new TextView(ViewSchedule.this);
                                            line3.setTextAppearance(getApplicationContext(), R.style.msgRateTbl);
                                            line3.setText(sched.getDate() + " - " +
                                                    sched.getStartTime() + " - " +
                                                    sched.getEndTime());
                                            line3.setSingleLine(false);
                                            line3.setWidth(TableRow.LayoutParams.WRAP_CONTENT);
                                            row3.addView(line3);

                                            tbl.addView(row3);

                                            TableRow row4 = new TableRow(ViewSchedule.this);
                                            TextView blank = new TextView(ViewSchedule.this);
                                            blank.setText("");
                                            row4.addView(blank);

                                            tbl.addView(row4);
                                            prog.setVisibility(View.GONE);
                                        } catch (Exception e) {
                                            e.printStackTrace();
                                        }
                                    }
                                }
                            }

                            @Override
                            public void onCancelled(DatabaseError error) {
                                Toast.makeText(ViewSchedule.this, "Query cancelled", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                });
            }
        });
    }

    private void clearTable(){
        tbl = findViewById(R.id.table_sched);
        if(!(tbl.getChildCount() <= 1)){
            tbl.removeViews(0, tbl.getChildCount()-1);
        }
        else if(tbl.getChildCount() == 1){
            tbl.removeViews(0, 1);
        }
    }
}