package com.example.android_final.model;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Query;

public interface AnimalApi {
    @Headers("X-Api-Key: 6C/qQCyL/lh2vUA27CZG1w==2xmVOXCjGNdlApth" )
    @GET("dogs?")
    Call<List<Animal>> searchAnimalByName(@Query("name") String name);

}
