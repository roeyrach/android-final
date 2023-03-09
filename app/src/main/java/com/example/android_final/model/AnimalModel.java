package com.example.android_final.model;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class AnimalModel {
    final public static AnimalModel _instance = new AnimalModel();
    final public MutableLiveData<LoadingState> EventAnimalListLoadingState = new MutableLiveData<>(LoadingState.NOT_LOADING);


    final String BASE_URL = "https://api.api-ninjas.com/v1/";
    Retrofit retrofit;
    AnimalApi animalApi;
    public static AnimalModel instance(){ return _instance;}
    private AnimalModel(){
        retrofit = new Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create()).build();
        animalApi = retrofit.create(AnimalApi.class);
    }

    public LiveData<List<Animal>> searchAnimalByName(String name){
        EventAnimalListLoadingState.setValue(LoadingState.LOADING);
        MutableLiveData<List<Animal>> data = new MutableLiveData<>();
        Call<List<Animal>> call = animalApi.searchAnimalByName(name);
        call.enqueue(new Callback<List<Animal>>() {
            @Override
            public void onResponse(Call<List<Animal>> call, Response<List<Animal>> response) {
                if (response.isSuccessful()){
                    List<Animal> res = response.body();
                    data.setValue(res);
                    EventAnimalListLoadingState.setValue(LoadingState.NOT_LOADING);

                }else{
                    Log.d("TAG","----- searchAnimalByName response error");
                    EventAnimalListLoadingState.setValue(LoadingState.NOT_LOADING);

                }
            }

            @Override
            public void onFailure(Call<List<Animal>> call, Throwable t) {
                Log.d("TAG","----- searchAnimalByName response failed");
                EventAnimalListLoadingState.setValue(LoadingState.NOT_LOADING);

            }
        });

        return data;
    }



    public enum LoadingState{
        LOADING,
        NOT_LOADING
    }



}


