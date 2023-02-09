package com.example.android_final;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.example.android_final.databinding.FragmentPetInfoBinding;

import java.util.ArrayList;
import java.util.List;


public class PetInfoFragment extends Fragment {

    FragmentPetInfoBinding binding;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
         binding = FragmentPetInfoBinding.inflate(inflater, container, false);
        Spinner spinner = binding.spinner;
        spinner.setPrompt("Select Gender");

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity(),R.array.gender, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);

        adapter.setDropDownViewResource(com.google.android.material.R.layout.support_simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String text = adapterView.getItemAtPosition(i).toString();
                System.out.println(text);

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });



         return binding.getRoot();
    }



}