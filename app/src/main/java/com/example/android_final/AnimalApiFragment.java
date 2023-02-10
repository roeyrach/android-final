package com.example.android_final;

import android.os.Build;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.android_final.databinding.FragmentAnimalApiBinding;
import com.example.android_final.model.Animal;
import com.example.android_final.model.AnimalModel;

import java.util.ArrayList;
import java.util.List;

public class AnimalApiFragment extends Fragment {

   FragmentAnimalApiBinding binding;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
       binding = FragmentAnimalApiBinding.inflate(inflater,container, false);
        LiveData<List<Animal>> data = AnimalModel.instance.searchAnimalByName("dog");
        data.observe(getViewLifecycleOwner(),list->{
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                list.forEach(item-> {
                    Log.d("Tag", "getAnimal: " + item.getName() + "characteristics:  " + item.getCharacteristics().toString());
                });
            }

        });
        return binding.getRoot();
    }
}