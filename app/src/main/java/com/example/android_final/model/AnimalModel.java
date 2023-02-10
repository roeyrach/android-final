package com.example.android_final.model;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class AnimalModel {
    final public static AnimalModel instance = new AnimalModel();

    final String BASE_URL = "https://api.api-ninjas.com/v1/";
    Retrofit retrofit;
    AnimalApi animalApi;

    private AnimalModel(){
        JsonArray gs = new JsonArray();
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();
        retrofit = new Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create()).build();
        animalApi = retrofit.create(AnimalApi.class);
    }

    public LiveData<List<Animal>> searchAnimalByName(String name){
        MutableLiveData<List<Animal>> data = new MutableLiveData<>();
        Call<List<Animal>> call = animalApi.searchAnimalByName(name);
        call.enqueue(new Callback<List<Animal>>() {
            @Override
            public void onResponse(Call<List<Animal>> call, Response<List<Animal>> response) {
                if (response.isSuccessful()){
                    List<Animal> res = response.body();
                    data.setValue(res);
                }else{
                    Log.d("TAG","----- searchAnimalByName response error");
                }
            }

            @Override
            public void onFailure(Call<List<Animal>> call, Throwable t) {
                Log.d("TAG","----- searchAnimalByName response failed");
            }
        });

        return data;
    }

}


