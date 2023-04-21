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
    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_menu);

        tbl = findViewById(R.id.table_menu);
        sort = findViewById(R.id.button7);
        alph = findViewById(R.id.button8);
        databaseReference = FirebaseDatabase.getInstance("https://easymapuaauth-default-rtdb.asia-southeast1.firebasedatabase.app/").getReference().child("Menu");

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
                Toast.makeText(ViewMenu.this, "No existing data", Toast.LENGTH_SHORT).show();
                clearTable();
                dataList.clear();
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        Query query = databaseReference.orderByChild("store").equalTo(item);
                        query.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot snapshot) {
                                if(snapshot.exists()){
                                    for(DataSnapshot foodDataSnap : snapshot.getChildren()){
                                        //Extract the food object from snapshot
                                        Food food = foodDataSnap.getValue(Food.class);
                                        dataList.add(new Data(food.getFood(), food.getPrice()));
                                        try{
                                            TableRow row = new TableRow(ViewMenu.this);
                                            TextView foodData = new TextView(ViewMenu.this);
                                            foodData.setTextAppearance(getApplicationContext(), R.style.menuTableText);
                                            foodData.setText(food.getFood());
                                            row.addView(foodData);

                                            TextView priceData = new TextView(ViewMenu.this);
                                            priceData.setTextAppearance(getApplicationContext(), R.style.menuTableText);
                                            priceData.setText("PHP " +  food.getPrice() + ".00");
                                            row.addView(priceData);

                                            tbl.addView(row);
                                        } catch (Exception e) {
                                            e.printStackTrace();
                                        }
                                    }
                                }
                                else{
                                    Toast.makeText(ViewMenu.this, "No existing data", Toast.LENGTH_SHORT).show();
                                }
                            }

                            @Override
                            public void onCancelled(DatabaseError error) {
                                Toast.makeText(ViewMenu.this, "Query cancelled", Toast.LENGTH_SHORT).show();
                            }
                        });
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