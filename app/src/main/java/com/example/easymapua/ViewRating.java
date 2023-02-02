package com.example.easymapua;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

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
                String item = parent.getItemAtPosition(position).toString();
                Handler handler = new Handler(Looper.getMainLooper());
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        String[] field = new String[1];
                        field[0] = "store";
                        String[] data = new String[1];
                        data[0] = item;
                        PutData putData = new PutData("http://192.168.1.7/LoginRegister/viewratings.php", "POST", field, data);
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
                                        user.setTextAppearance(getApplicationContext(), R.style.menuTableText);
                                        user.setText(jsonObject.getString("username"));
                                        row1.addView(user);

                                        TextView classData = new TextView(ViewRating.this);
                                        classData.setTextAppearance(getApplicationContext(), R.style.menuTableText);
                                        classData.setText("(" + jsonObject.getString("classification") + ")");
                                        row1.addView(classData);

                                        TextView storeD = new TextView(ViewRating.this);
                                        storeD.setTextAppearance(getApplicationContext(), R.style.menuTableText);
                                        storeD.setText("on: " + jsonObject.getString("store"));
                                        row1.addView(storeD);

                                        tbl.addView(row1);

                                        TableRow row2 = new TableRow(ViewRating.this);

                                        TextView sub = new TextView(ViewRating.this);
                                        sub.setTextAppearance(getApplicationContext(), R.style.menuTableText);
                                        sub.setText(jsonObject.getString("subject"));
                                        row1.addView(sub);

                                        tbl.addView(row2);

                                        TableRow row3 = new TableRow(ViewRating.this);

                                        TextView msg = new TextView(ViewRating.this);
                                        msg.setTextAppearance(getApplicationContext(), R.style.menuTableText);
                                        msg.setText(jsonObject.getString("message"));
                                        row1.addView(sub);

                                        tbl.addView(row3);
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
        tbl.removeViews(1, tbl.getChildCount()-1);
    }
}