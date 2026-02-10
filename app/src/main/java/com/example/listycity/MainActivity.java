package com.example.listycity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements AddCityFragment.OnFragmentInteractionListener{

    ListView cityList;
    CityArrayAdapter cityAdapter;
    ArrayList<City> dataList;

    Button addCityButton;
    Button deleteCityButton;

    int selectedPos = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        cityList = findViewById(R.id.city_list);
        addCityButton = findViewById(R.id.add_city);
        deleteCityButton = findViewById(R.id.delete_city);


        String[] cities = {
                "Edmonton",
                "Vancouver",
                "Toronto"
        };

        String[] provinces = {
                "AB",
                "BC",
                "ON"
        };
        dataList = new ArrayList<>();

        for (int i = 0; i < cities.length; i++) {
            dataList.add(new City(cities[i], provinces[i]));
        }

        cityAdapter = new CityArrayAdapter(this, dataList);
        cityList.setAdapter(cityAdapter);

        cityList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                selectedPos = position;
                AddCityFragment.newInstance(dataList.get(position)).show(getSupportFragmentManager(), "EDIT_CITY");
            }
        });

        addCityButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AddCityFragment().show(getSupportFragmentManager(), "ADD_CITY");
            }
        });

        deleteCityButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (selectedPos != -1) {
                    dataList.remove(selectedPos);
                    cityAdapter.notifyDataSetChanged();
                    selectedPos = -1;
                }
            }
        });
    }

    @Override
    public void onOkPressed(City oldCity, City newCity) {
        if (newCity.getName().trim().isEmpty()) {
            return;
        }

        String province = newCity.getProvince().trim().toUpperCase();
        if (province.isEmpty()) {
            newCity.setProvince("N/A");
        } else if (province.length() > 3) {
            newCity.setProvince(province.substring(0, 3));
        } else {
            newCity.setProvince(province);
        }

        if (oldCity != null) {

            int index = -1;
            for (int i = 0; i < dataList.size(); i++) {
                if (dataList.get(i).getName().equalsIgnoreCase(oldCity.getName()) && dataList.get(i).getProvince().equalsIgnoreCase(oldCity.getProvince())) {
                    index = i;
                    break;
                }
            }

            if (index != -1) {

                if (!oldCity.getName().equalsIgnoreCase(newCity.getName())) {
                    for (City existingCity : dataList) {
                        if (existingCity.getName().equalsIgnoreCase(newCity.getName())) {
                            return;
                        }
                    }
                }
                dataList.set(index, newCity);
            }
        } else {

            boolean exists = false;
            for (City existingCity : dataList) {
                if (existingCity.getName().equalsIgnoreCase(newCity.getName())) {
                    exists = true;
                    break;
                }
            }
            if (!exists) {
                dataList.add(newCity);
            }
        }
        cityAdapter.notifyDataSetChanged();
    }
}
