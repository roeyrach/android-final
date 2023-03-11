package com.example.android_final;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.android_final.databinding.FragmentAddPostBinding;
import com.example.android_final.databinding.FragmentEditPostBinding;
import com.example.android_final.model.Model;
import com.example.android_final.model.Post;


public class EditPost extends Fragment {

    FragmentEditPostBinding binding;
    EditPostArgs args;
    UserViewModel userViewModel;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentEditPostBinding.inflate(inflater, container, false);
        args = EditPostArgs.fromBundle(getArguments());
        userViewModel = new ViewModelProvider(requireActivity()).get(UserViewModel.class);
        userViewModel.getCurrentUser().observe(getViewLifecycleOwner(), user -> {
            if (user != null) {
                Log.d("TAG", "onCreateView: " + args.getPostId());
                Post post = new Post("Guy", "hey how are you?");
                post.setPostId(args.getPostId());
                Model.instance().addPost(post, (Post) -> {
                    Log.d("TAG", "onCreateView: " + post.getPostId());
                });

                Model.instance().addPostToUser(user.getUserFirebaseID(), post, (Post) -> {
                    Log.d("TAG", "onCreateView: " + post.getPostId());
                });
            }
        });


        return binding.getRoot();

    }
}