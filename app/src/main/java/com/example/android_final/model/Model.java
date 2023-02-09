package com.example.android_final.model;

public class Model {
    private static final Model _instance = new Model();

    private Model(){
    }

    public static Model instance(){
        return _instance;
    }
}
