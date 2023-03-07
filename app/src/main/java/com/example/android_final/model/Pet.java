package com.example.android_final.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import org.checkerframework.checker.units.qual.A;

import java.util.HashMap;
import java.util.Map;

public class Pet {

    private int id;

    String petName;
    String petImageUrl;
    String petAge;
    String petGender;
    public Pet(){

    }

    public Pet(String petName, String petImageUrl, String petAge, String petGender) {
        this.petName = petName;
        this.petImageUrl = petImageUrl;
        this.petAge = petAge;
        this.petGender = petGender;
    }

    static final String NAME = "petName";
    static final String Gender = "petGender";
    static final String Age = "petAge";
    static final String URL = "petImageUrl";

    public static Pet fromJson(Map<String,Object> json){
        String name = (String) json.get(NAME);
        String url = (String) json.get(URL);
        String gender = (String) json.get(Gender);
        String age = (String) json.get(Age);
        return new Pet(name,url,age,gender);
    }

    public Map<String,Object> toJson(){
        Map<String,Object> map = new HashMap<>();
        map.put(NAME,petName);
        map.put(Gender,petGender);
        map.put(Age,petAge);
        map.put(URL,petImageUrl);

        return map;
    }


    public String getPetName() {
        return petName;
    }

    public void setPetName(String petName) {
        this.petName = petName;
    }

    public String getPetImageUrl() {
        return petImageUrl;
    }

    public void setPetImageUrl(String petImageUrl) {
        this.petImageUrl = petImageUrl;
    }

    public String getPetAge() {
        return petAge;
    }

    public void setPetAge(String petAge) {
        this.petAge = petAge;
    }

    public String getPetGender() {
        return petGender;
    }

    public void setPetGender(String petGender) {
        this.petGender = petGender;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
