package com.example.android_final.model;

import java.util.HashMap;
import java.util.Map;

public class User {


    String userFirebaseID;
    String userName;
    String email;
    String password;
    Pet userPet;
    public User(String userName, String email, String password, Pet userPet){
        this.userName =userName;
        this.email = email;
        this.password =password;
        this.userPet = userPet;
    }

    static final String NAME = "userName";
    static final String ID = "id";
    static final String Pet = "pet";
    static final String Email = "email";
    static final String COLLECTION = "users";


    public Map<String,Object> toJson(){
        Map<String,Object> map = new HashMap<>();
        map.put(ID,userFirebaseID);
        map.put(NAME,userName);
        map.put(Email,email);
        map.put(Pet,userPet);

        return map;
    }

    public String getUserFirebaseID() {
        return userFirebaseID;
    }

    public void setUserFirebaseID(String userFirebaseID) {
        this.userFirebaseID = userFirebaseID;
    }
    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Pet getUserPet() {
        return userPet;
    }

    public void setUserPet(Pet userPet) {
        this.userPet = userPet;
    }
}
