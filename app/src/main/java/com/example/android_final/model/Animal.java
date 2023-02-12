package com.example.android_final.model;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;

import java.util.List;
import java.util.Map;

public class Animal {
    @SerializedName("name")
    String Name;



    @SerializedName("image_link")
    String ImageUrl;

    @SerializedName("good_with_children")
    String GoodWithChildren;

     @SerializedName("playfulness")
    String PlayFulness;

    public String getImageUrl() {
        return ImageUrl;
    }

    public void setImageUrl(String imageUrl) {
        ImageUrl = imageUrl;
    }
    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }



    public String getGoodWithChildren() {
        return GoodWithChildren;
    }

    public void setGoodWithChildren(String goodWithChildren) {
        GoodWithChildren = goodWithChildren;
    }

    public String getPlayFulness() {
        return PlayFulness;
    }

    public void setPlayFulness(String playFulness) {
        PlayFulness = playFulness;
    }
}
