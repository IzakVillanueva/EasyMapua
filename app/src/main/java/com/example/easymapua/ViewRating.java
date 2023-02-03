package com.example.easymapua;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.vishnusivadas.advanced_httpurlconnection.PutData;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class ViewRating extends AppCompatActivity {
    String[] store = {"Jaymin's Kitchen", "Varda Burgers", "Food Hall"};
    AutoCompleteTextView autoCompleteTextView;
    ArrayAdapter<String> adapterItems;
    TableLayout tbl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_rating);

        tbl = findViewById(R.id.table_rating);

        autoCompleteTextView = findViewById(R.id.autocompleteTextView);
        adapterItems = new ArrayAdapter<String>(this, R.layout.list_item_store, store);
        autoCompleteTextView.setAdapter(adapterItems);

        autoCompleteTextView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String selected = parent.getItemAtPosition(position).toString();
                Handler handler = new Handler(Looper.getMainLooper());
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        String[] field = new String[1];
                        field[0] = "store";
                        String[] data = new String[1];
                        data[0] = selected;
                        PutData putData = new PutData("http://192.168.11.156/LoginRegister/viewratings.php", "POST", field, data);
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
                                        TableRow row1 = new TableRow(ViewRating.this);

                                        TextView user = new TextView(ViewRating.this);
                                        user.setTextAppearance(getApplicationContext(), R.style.userRateTbl);
                                        user.setText("By: " + jsonObject.getString("username") +
                                                " (" + jsonObject.getString("classification") + "),  " +
                                                "on: " + jsonObject.getString("store"));
                                        user.setSingleLine(false);
                                        user.setWidth(TableRow.LayoutParams.WRAP_CONTENT);
                                        row1.addView(user);

                                        tbl.addView(row1);

                                        TableRow row2 = new TableRow(ViewRating.this);

                                        TextView sub = new TextView(ViewRating.this);
                                        sub.setTextAppearance(getApplicationContext(), R.style.subRateTbl);
                                        sub.setText("Subject: " + jsonObject.getString("subject"));
                                        sub.setSingleLine(false);
                                        sub.setWidth(TableRow.LayoutParams.WRAP_CONTENT);
                                        row2.addView(sub);

                                        tbl.addView(row2);

                                        TableRow row3 = new TableRow(ViewRating.this);

                                        TextView msg = new TextView(ViewRating.this);
                                        msg.setTextAppearance(getApplicationContext(), R.style.msgRateTbl);
                                        msg.setText(jsonObject.getString("message"));
                                        msg.setSingleLine(false);
                                        msg.setWidth(TableRow.LayoutParams.WRAP_CONTENT);
                                        row3.addView(msg);

                                        tbl.addView(row3);

                                        TableRow row4 = new TableRow(ViewRating.this);
                                        TextView blank = new TextView(ViewRating.this);
                                        blank.setText("");
                                        row4.addView(blank);

                                        tbl.addView(row4);
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
        tbl = findViewById(R.id.table_rating);
        if(!(tbl.getChildCount() <= 1)){
            tbl.removeViews(0, tbl.getChildCount()-1);
        }
        else if(tbl.getChildCount() == 1){
            tbl.removeViews(0, 1);
        }
    }
}