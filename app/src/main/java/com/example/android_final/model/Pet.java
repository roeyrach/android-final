package com.example.android_final.model;

public class Pet {
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
}
