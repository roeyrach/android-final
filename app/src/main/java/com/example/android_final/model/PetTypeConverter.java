package com.example.android_final.model;

import androidx.room.TypeConverter;

import com.google.gson.Gson;

public class PetTypeConverter {
    @TypeConverter
    public static Pet toPet(String petString) {
        Gson gson = new Gson();
        return gson.fromJson(petString, Pet.class);
    }

    @TypeConverter
    public static String fromPet(Pet pet) {
        Gson gson = new Gson();
        return gson.toJson(pet);
    }
}
