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
import android.widget.Toast;

import com.vishnusivadas.advanced_httpurlconnection.PutData;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class ViewMenu extends AppCompatActivity {
    String[] store = {"Jaymin's Kitchen", "Varda Burgers", "Food Hall"};
    AutoCompleteTextView autoCompleteTextView;
    ArrayAdapter<String> adapterItems;
    TableLayout tbl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_menu);

        tbl = findViewById(R.id.table_menu);

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
                        PutData putData = new PutData("http://192.168.1.7/LoginRegister/viewmenu.php", "POST", field, data);
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
                                        TableRow row = new TableRow(ViewMenu.this);

                                        TextView foodData = new TextView(ViewMenu.this);
                                        foodData.setTextAppearance(getApplicationContext(), R.style.menuTableText);
                                        foodData.setText(jsonObject.getString("food"));
                                        row.addView(foodData);

                                        TextView priceData = new TextView(ViewMenu.this);
                                        priceData.setTextAppearance(getApplicationContext(), R.style.menuTableText);
                                        priceData.setText("PHP " +  jsonObject.getString("price") + ".00");
                                        row.addView(priceData);

                                        tbl.addView(row);
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
        tbl = findViewById(R.id.table_menu);
        tbl.removeViews(1, tbl.getChildCount()-1);
    }
}