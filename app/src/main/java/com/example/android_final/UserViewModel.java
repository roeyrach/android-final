package com.example.android_final;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.android_final.model.User;

public class UserViewModel extends ViewModel {
    private  MutableLiveData<User> currentUser = new MutableLiveData<>();
    private static final UserViewModel _instance = new UserViewModel();

    private UserViewModel(){

    }

    public static UserViewModel getInstance(){
        return _instance;
    }


    public void setCurrentUser(User user){
        currentUser.setValue(user);

    }

    public LiveData<User> getCurrentUser(){
        return currentUser;
    }
}
