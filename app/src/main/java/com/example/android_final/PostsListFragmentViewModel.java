package com.example.android_final;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.android_final.model.Model;
import com.example.android_final.model.Post;

import java.util.List;

public class PostsListFragmentViewModel extends ViewModel {
    private LiveData<List<Post>> data = Model.instance().getAllPosts();

    LiveData<List<Post>> getData(){
        return data;
    }
}
