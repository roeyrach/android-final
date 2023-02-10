package com.example.android_final.model;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;

import java.util.List;
import java.util.Map;

public class Animal {
    @SerializedName("name")
    String Name;

    @SerializedName("locations")
    List<String> Locations;

    @SerializedName("characteristics")
    Map<String,String> Characteristics;

    @SerializedName("taxonomy")
    Map<String,String> Taxonomy;
    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public List<String> getLocations() {
        return Locations;
    }

    public void setLocations(List<String> locations) {
        Locations = locations;
    }

    public  Map<String,String>  getCharacteristics() {
//        Log.d("TAG", new Gson().fromJson()
        return Characteristics;
    }

    public void setCharacteristics( Map<String,String>  characteristics) {
        Characteristics = characteristics;
    }

    public Map<String, String> getTaxonomy() {
        return Taxonomy;
    }

    public void setTaxonomy(Map<String, String> taxonomy) {
        Taxonomy = taxonomy;
    }
}
