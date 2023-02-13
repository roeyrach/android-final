package com.example.android_final.model;

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
