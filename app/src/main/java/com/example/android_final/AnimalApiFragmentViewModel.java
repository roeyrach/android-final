package com.example.android_final;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.android_final.model.Animal;
import com.example.android_final.model.AnimalModel;

import java.util.ArrayList;
import java.util.List;

public class AnimalApiFragmentViewModel  extends ViewModel {
    private LiveData<List<Animal>> data = AnimalModel.instance().searchAnimalByName("a");

    LiveData<List<Animal>> getData(){
        return data;
    }

}
