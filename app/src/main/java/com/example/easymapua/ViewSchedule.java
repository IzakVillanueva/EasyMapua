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

import com.vishnusivadas.advanced_httpurlconnection.PutData;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class ViewSchedule extends AppCompatActivity {
    private TableLayout tbl;
    private Button allB, consulB;
    private ProgressBar prog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_schedule);

        tbl = findViewById(R.id.table_sched);
        allB = findViewById(R.id.buttonLoadAll);
        consulB = findViewById(R.id.buttonLoadConsul);
        prog = findViewById(R.id.progressBarSched);

        allB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                prog.setVisibility(View.VISIBLE);
                Handler handler = new Handler(Looper.getMainLooper());
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        String[] field = new String[1];
                        field[0] = "task";
                        String[] data = new String[1];
                        data[0] = "Consultation";
                        PutData putData = new PutData("http://192.168.1.7/LoginRegister/viewsched.php", "POST", field, data);
                        if(putData.startPut()){
                            if(putData.onComplete()){
                                String result = putData.getResult();
                                JSONArray jsonArray;
                                clearTable();
                                try {
                                    jsonArray = new JSONArray(result.toString());
                                    for(int i = 0; i < jsonArray.length(); i++){
                                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                                        //populate table
                                        TableRow row1 = new TableRow(ViewSchedule.this);

                                        TextView line1 = new TextView(ViewSchedule.this);
                                        line1.setTextAppearance(getApplicationContext(), R.style.userRateTbl);
                                        line1.setText(jsonObject.getString("professor") +
                                                " (" + jsonObject.getString("coursecode") + ")");
                                        line1.setSingleLine(false);
                                        line1.setWidth(TableRow.LayoutParams.WRAP_CONTENT);
                                        row1.addView(line1);

                                        tbl.addView(row1);

                                        TableRow row2 = new TableRow(ViewSchedule.this);

                                        TextView line2 = new TextView(ViewSchedule.this);
                                        line2.setTextAppearance(getApplicationContext(), R.style.subRateTbl);
                                        line2.setText("Task: " + jsonObject.getString("task") +
                                                "   Room: " + jsonObject.getString("room"));
                                        line2.setSingleLine(false);
                                        line2.setWidth(TableRow.LayoutParams.WRAP_CONTENT);
                                        row2.addView(line2);

                                        tbl.addView(row2);

                                        TableRow row3 = new TableRow(ViewSchedule.this);

                                        TextView line3 = new TextView(ViewSchedule.this);
                                        line3.setTextAppearance(getApplicationContext(), R.style.msgRateTbl);
                                        line3.setText(jsonObject.getString("date") + " - " +
                                                jsonObject.getString("starttime") + " - " +
                                                jsonObject.getString("endtime"));
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
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                    }
                });
            }
        });

        consulB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                prog.setVisibility(View.VISIBLE);
                Handler handler = new Handler(Looper.getMainLooper());
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        String[] field = new String[1];
                        field[0] = "task";
                        String[] data = new String[1];
                        data[0] = "Consultation";
                        PutData putData = new PutData("http://192.168.1.7/LoginRegister/viewConsultation.php", "POST", field, data);
                        if(putData.startPut()){
                            if(putData.onComplete()){
                                String result = putData.getResult();
                                JSONArray jsonArray;
                                clearTable();
                                try {
                                    jsonArray = new JSONArray(result.toString());
                                    for(int i = 0; i < jsonArray.length(); i++){
                                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                                        //populate table
                                        TableRow row1 = new TableRow(ViewSchedule.this);

                                        TextView line1 = new TextView(ViewSchedule.this);
                                        line1.setTextAppearance(getApplicationContext(), R.style.userRateTbl);
                                        line1.setText(jsonObject.getString("professor") +
                                                " (" + jsonObject.getString("coursecode") + ")");
                                        line1.setSingleLine(false);
                                        line1.setWidth(TableRow.LayoutParams.WRAP_CONTENT);
                                        row1.addView(line1);

                                        tbl.addView(row1);

                                        TableRow row2 = new TableRow(ViewSchedule.this);

                                        TextView line2 = new TextView(ViewSchedule.this);
                                        line2.setTextAppearance(getApplicationContext(), R.style.subRateTbl);
                                        line2.setText("Task: " + jsonObject.getString("task") +
                                                "   Room: " + jsonObject.getString("room"));
                                        line2.setSingleLine(false);
                                        line2.setWidth(TableRow.LayoutParams.WRAP_CONTENT);
                                        row2.addView(line2);

                                        tbl.addView(row2);

                                        TableRow row3 = new TableRow(ViewSchedule.this);

                                        TextView line3 = new TextView(ViewSchedule.this);
                                        line3.setTextAppearance(getApplicationContext(), R.style.msgRateTbl);
                                        line3.setText(jsonObject.getString("date") + " - " +
                                                jsonObject.getString("starttime") + " - " +
                                                jsonObject.getString("endtime"));
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
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        }
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