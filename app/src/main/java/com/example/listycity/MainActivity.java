package com.example.listycity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Arrays;


public class MainActivity extends AppCompatActivity {
    ListView cityList;
    ArrayAdapter<String> cityAdapter;
    ArrayList<String> dataList;
    Button addCityButton;
    Button deleteCityButton;
    Button confirmCityButton;
    EditText cityInput;
    int selectedPos=-1;
    boolean isAdding =false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        cityList=findViewById(R.id.city_list);
        addCityButton=findViewById(R.id.add_city);
        deleteCityButton=findViewById(R.id.delete_city);
        confirmCityButton=findViewById(R.id.confirm_city);
        cityInput =findViewById(R.id.city_input);
        String[]cities= {"Edmonton","Vancouver","Moscow","Sydney","Berlin","Vienna","Tokyo","Beijing","Osaka","New Delhi"};

        dataList=new ArrayList<>();
        dataList.addAll(Arrays.asList(cities));

        cityAdapter= new ArrayAdapter<>(this,R.layout.content,dataList);
        cityList.setAdapter(cityAdapter);

        cityList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                selectedPos= position;
            }
        });

        addCityButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isAdding=true;
                cityInput.setText("");
                cityInput.requestFocus();
            }
        });

        confirmCityButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isAdding) {
                    String cityName = cityInput.getText().toString();

                    if (!cityName.isEmpty()) {
                        boolean exists=false;

                        for (int i=0;i<dataList.size();i++) {
                            if (dataList.get(i).equalsIgnoreCase(cityName)) {
                                exists=true;
                                break;
                            }
                        }

                        if (!exists) {
                            dataList.add(cityName);
                        }

                        cityAdapter.notifyDataSetChanged();
                        cityInput.setText("");
                        isAdding=false;
                    }
                }
            }
        });

        deleteCityButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (selectedPos!=-1) {
                    dataList.remove(selectedPos);
                    cityAdapter.notifyDataSetChanged();
                    selectedPos=-1;
                }
            }
        });



    }
}