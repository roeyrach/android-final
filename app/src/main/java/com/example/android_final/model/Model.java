package com.example.android_final.model;

import java.util.ArrayList;
import java.util.List;

public class Model {
    private static final Model _instance = new Model();

    private FirebaseModel firebaseModel = new FirebaseModel();


    private Model(){
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


    public void signUpUser(String email, String password){
        firebaseModel.signUpUser(email,password);
    }

}
