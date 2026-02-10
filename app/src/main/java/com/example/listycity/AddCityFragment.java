package com.example.listycity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;

public class AddCityFragment extends DialogFragment {

    private EditText cityEditText;
    private EditText provinceEditText;

    private OnFragmentInteractionListener listener;
    private City cityToEdit;

    public interface OnFragmentInteractionListener {
        void onOkPressed(City oldCity, City newCity);
    }

    public static AddCityFragment newInstance(City city) {
        AddCityFragment fragment = new AddCityFragment();
        if (city != null) {
            Bundle args = new Bundle();
            args.putSerializable("city_to_edit", city);
            fragment.setArguments(args);
        }
        return fragment;
    }

    @Override
    public void onAttach(@NonNull android.content.Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            listener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString() + " must implement OnFragmentInteractionListener");
        }
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_add_city, null);
        cityEditText = view.findViewById(R.id.city_input);
        provinceEditText = view.findViewById(R.id.province_input);

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

        cityToEdit = null;
        if (getArguments() != null) {
            cityToEdit = (City) getArguments().getSerializable("city_to_edit");
        }

        if (cityToEdit != null) {
            builder.setTitle("Edit City");
            cityEditText.setText(cityToEdit.getName());
            provinceEditText.setText(cityToEdit.getProvince());
        } else {
            builder.setTitle("Add City");
        }

        return builder.setView(view)
                .setNegativeButton("Cancel", null)
                .setPositiveButton("OK", (dialog, which) -> {
                    String cityName = cityEditText.getText().toString();
                    String provinceName = provinceEditText.getText().toString();
                    listener.onOkPressed(cityToEdit, new City(cityName, provinceName));
                })
                .create();
    }
}
