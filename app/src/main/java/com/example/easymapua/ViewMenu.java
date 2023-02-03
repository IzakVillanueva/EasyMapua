package com.example.easymapua;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.vishnusivadas.advanced_httpurlconnection.PutData;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class ViewMenu extends AppCompatActivity {
    String[] store = {"Jaymin's Kitchen", "Varda Burgers", "Food Hall"};
    AutoCompleteTextView autoCompleteTextView;
    ArrayAdapter<String> adapterItems;
    TableLayout tbl;
    Button sort, alph;
    List<Data> dataList = new ArrayList<>();
    int priceSorter = 0, foodSorter = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_menu);

        tbl = findViewById(R.id.table_menu);
        sort = findViewById(R.id.button7);
        alph = findViewById(R.id.button8);

        autoCompleteTextView = findViewById(R.id.autocompleteTextView);
        adapterItems = new ArrayAdapter<String>(this, R.layout.list_item_store, store);
        autoCompleteTextView.setAdapter(adapterItems);
        autoCompleteTextView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                autoCompleteTextView.dismissDropDown();
                String item = parent.getItemAtPosition(position).toString();
                autoCompleteTextView.setAdapter(adapterItems);
                Handler handler = new Handler(Looper.getMainLooper());
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        String[] field = new String[1];
                        field[0] = "store";
                        String[] data = new String[1];
                        data[0] = item;
                        PutData putData = new PutData("http://192.168.11.156/LoginRegister/viewmenu.php", "POST", field, data);
                        if(putData.startPut()){
                            if(putData.onComplete()){
                                String result = putData.getResult();
                                JSONArray jsonArray;
                                clearTable();
                                try {
                                    jsonArray = new JSONArray(result.toString());
                                    dataList.clear();
                                    for(int i = 0; i < jsonArray.length(); i++){
                                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                                        //store it in data list for sorting
                                        dataList.add(new Data(jsonObject.getString("food"), jsonObject.getString("price")));
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

        sort.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                priceSorter++;
                if(!(tbl.getChildCount() <= 1)){
                    clearTable();
                    if(priceSorter%2 == 1){
                        Collections.sort(dataList, new Comparator<Data>() { //sort the values asc
                            @Override
                            public int compare(Data o1, Data o2) {
                                return Integer.compare(Integer.parseInt(o1.getPrice()), Integer.parseInt(o2.getPrice()));
                            }
                        });
                    }
                    else{
                        Collections.sort(dataList, new Comparator<Data>() { //sort the values desc
                            @Override
                            public int compare(Data o1, Data o2) {
                                return Integer.compare(Integer.parseInt(o2.getPrice()), Integer.parseInt(o1.getPrice()));
                            }
                        });
                    }
                    clearTable();
                    //populate the table with the datalist
                    for(Data data : dataList){
                        TableRow row = new TableRow(ViewMenu.this);
                        TextView foodData = new TextView(ViewMenu.this);
                        foodData.setTextAppearance(getApplicationContext(), R.style.menuTableText);
                        foodData.setText(data.getFood());
                        row.addView(foodData);

                        TextView priceData = new TextView(ViewMenu.this);
                        priceData.setTextAppearance(getApplicationContext(), R.style.menuTableText);
                        priceData.setText("PHP " +  data.getPrice() + ".00");
                        row.addView(priceData);

                        tbl.addView(row);
                    }
                }
            }
        });

        alph.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                foodSorter++;
                if(!(tbl.getChildCount() <= 1)){
                    clearTable();
                    if(foodSorter%2 == 1){
                        Collections.sort(dataList, new Comparator<Data>() { //sort the values asc
                            @Override
                            public int compare(Data o1, Data o2) {
                                return o1.getFood().compareTo(o2.getFood());
                            }
                        });
                    }
                    else{
                        Collections.sort(dataList, new Comparator<Data>() { //sort the values desc
                            @Override
                            public int compare(Data o1, Data o2) {
                                return o2.getFood().compareTo(o1.getFood());
                            }
                        });
                    }
                    clearTable();
                    //populate the table with the datalist
                    for(Data data : dataList){
                        TableRow row = new TableRow(ViewMenu.this);
                        TextView foodData = new TextView(ViewMenu.this);
                        foodData.setTextAppearance(getApplicationContext(), R.style.menuTableText);
                        foodData.setText(data.getFood());
                        row.addView(foodData);

                        TextView priceData = new TextView(ViewMenu.this);
                        priceData.setTextAppearance(getApplicationContext(), R.style.menuTableText);
                        priceData.setText("PHP " +  data.getPrice() + ".00");
                        row.addView(priceData);

                        tbl.addView(row);
                    }
                }
            }
        });
    }

    private void clearTable(){
        tbl = findViewById(R.id.table_menu);
        tbl.removeViews(1, tbl.getChildCount()-1);
    }
}

class Data{
    private String food;
    private String price;

    public Data(String text1, String text2){
        this.food = text1;
        this.price = text2;
    }

    public String getFood(){
        return food;
    }

    public String getPrice(){
        return price;
    }
}