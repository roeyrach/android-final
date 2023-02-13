package com.example.android_final.model;


import android.os.Handler;
import android.os.Looper;

import androidx.core.os.HandlerCompat;

=======
import android.util.Log;

import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;

import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class Model {
    private static final Model _instance = new Model();

    private FirebaseModel firebaseModel = new FirebaseModel();


    private Model() {
    }


    private Executor executor = Executors.newSingleThreadExecutor();
    private Handler mainHandler = HandlerCompat.createAsync(Looper.getMainLooper());

    public interface Listener<T>{
        void onComplete(T data);
    }

    public static Model instance(){
        return _instance;
    }

    AppLocalDbRepository localDb = AppLocalDb.getAppDb();

    public interface GetAllPostsListener {
        void onComplete(List<Post> data);
    }

    public void getAllPosts(GetAllPostsListener callback) {
        executor.execute(() -> {
            List<Post> data = localDb.postDao().getAll();
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            mainHandler.post(() -> callback.onComplete(data));
        });
    }

    public interface AddPostListener {
        void onComplete();
    }

    public void addPost(Post p, AddPostListener listener) {
        executor.execute(() -> {
            localDb.postDao().insertAll(p);
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            mainHandler.post(() -> listener.onComplete());
        });
    }



    public void signUpUser(String name, String email ,String password,Pet pet, Listener<Void> listener){
        firebaseModel.signUpUser(email,password, (FireBaseUser)->{
            Log.d("TAG", FireBaseUser.getUid());

            User user = new User(name,email,password,pet);
            user.setUserFirebaseID(FireBaseUser.getUid());
            Log.d("TAG", user.toJson().toString());

            firebaseModel.addUser(user,(unused)->{
                listener.onComplete(null);
            });
        });
    }

    public void signInUser(String email, String password, Listener<Void> listener){
        firebaseModel.signInUser(email,password,(FireBaseUser)->{
            Log.d("TAG", FireBaseUser.getUid());
            firebaseModel.getUser(FireBaseUser.getUid(), (User)->{
                Log.d("TAG", "userfound in Model");
                listener.onComplete(null);
            });

        });

    }

}
