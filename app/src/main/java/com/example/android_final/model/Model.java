package com.example.android_final.model;

import android.util.Log;

import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;
import java.util.List;

public class Model {
    private static final Model _instance = new Model();

    private FirebaseModel firebaseModel = new FirebaseModel();


    private Model(){
    }

    public interface Listener<T>{
        void onComplete(T data);
    }

    public static Model instance(){
        return _instance;
    }

    List<Post> postsList = new ArrayList<>();

    public List<Post> getAllPosts() {
        for (int i = 0; i < 10; i++) {
            postsList.add(new Post("Post " + i, "Hello guy how you doin'?", ""));
        }

        return postsList;
    }
    public void addPost(Post post){
        postsList.add(post);
    }


    public void signUpUser(String name, String email ,String password,Pet pet, Listener<Void> listener){
        firebaseModel.signUpUser(email,password, (FireBaseUser)->{
            Log.d("TAG", FireBaseUser.getUid());

            User user = new User(name,email,password,pet);
            user.setUserFirebaseID(FireBaseUser.getUid());
            Log.d("TAG", user.toJson().toString());
            listener.onComplete(null);
            firebaseModel.addUser(user);
        });
    }

    public void signInUser(String email, String password, Listener<Void> listener){
        firebaseModel.signInUser(email,password,(FireBaseUser)->{
            Log.d("TAG", FireBaseUser.getUid());
            listener.onComplete(null);
        });
    }

}
