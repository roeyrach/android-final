package com.example.android_final.model;

import android.graphics.Bitmap;
import android.os.Handler;
import android.os.Looper;

import androidx.core.os.HandlerCompat;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import android.util.Log;

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

    public interface Listener<T> {
        void onComplete(T data);
    }

    public enum LoadingState {
        LOADING,
        NOT_LOADING
    }

    final public MutableLiveData<LoadingState> EventPostListLoadingState = new MutableLiveData<LoadingState>(LoadingState.NOT_LOADING);


    public static Model instance() {
        return _instance;
    }

    AppLocalDbRepository localDb = AppLocalDb.getAppDb();

    private LiveData<List<Post>> postList;

    private LiveData<User> user;

    public LiveData<List<Post>> getAllPosts() {
        if (postList == null) {
            postList = localDb.postDao().getAll();
            refreshAllPosts();
        }
        return postList;
    }

    public void refreshAllPosts() {
        EventPostListLoadingState.setValue(LoadingState.LOADING);
        //get local last update
        Long localLastUpdate = Post.getLocalLastUpdate();
        //get all updated records from firebase since local last update
        firebaseModel.getAllPostsSince(localLastUpdate, list -> {
            executor.execute(() -> {
                Log.d("TAG", "firebase return : " + list.size());
                Long time = localLastUpdate;
                for (Post p : list) {
                    //insert new record into ROOM
                    localDb.postDao().insertAll(p);
                    if (time < p.getLastUpdated()) {
                        time = p.getLastUpdated();
                    }
                }
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {

                }
                //update local last update
                Post.setLocalLastUpdate(time);
                EventPostListLoadingState.postValue(LoadingState.NOT_LOADING);
            });
        });
    }

    public void addPost(Post p, Listener<Void> listener) {
        firebaseModel.addPost(p, (Void) -> {
            refreshAllPosts();
            listener.onComplete(null);
        });

    }

    public void addPostToUser(String uid ,Post p, Listener<Void> listener) {
        firebaseModel.addPostToUser(uid, p, (Void) -> {
            listener.onComplete(null);
        });

    }

    public LiveData<User> getUser(){
        if(user == null){
            user = localDb.userDao().getUser();
        }
        return user;
    }


    public void signUpUser(String name, String email, String password, Pet pet, Listener<User> listener) {
        firebaseModel.signUpUser(email, password, (FireBaseUser) -> {
            Log.d("TAG", FireBaseUser.getUid());

            User user = new User(name, email, password, pet);
            user.setUserFirebaseID(FireBaseUser.getUid());
            Log.d("TAG", user.toJson().toString());

            firebaseModel.addUser(user, (unused) -> {
                executor.execute(()->{
                    localDb.userDao().deleteAll();
                    localDb.userDao().insertUser(user);

                });
                listener.onComplete(user);
            });
        });
    }

    public void signInUser(String email, String password, Listener<User> listener) {
        firebaseModel.signInUser(email, password, (FireBaseUser) -> {
            Log.d("TAG", FireBaseUser.getUid());
            firebaseModel.getUser(FireBaseUser.getUid(), (User) -> {
                Log.d("TAG", "userfound in Model");
               executor.execute(()->{
                   localDb.userDao().deleteAll();
                   localDb.userDao().insertUser(User);

               });

                listener.onComplete(User);
            });

        });

    }



    public void uploadImage(String name, Bitmap bitmap, Listener<String> listener) {
        firebaseModel.uploadImage(name, bitmap, listener);
    }

    public void editUser(User user, Listener<Void> listener){
        firebaseModel.addUser(user,(unused)->{
            executor.execute(()->{
                localDb.userDao().insertUser(user);

            });
            listener.onComplete(null);
        });
    }

}
