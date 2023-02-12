package com.example.android_final;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.android_final.databinding.FragmentMainFeedBinding;
import com.example.android_final.databinding.FragmentSignInBinding;
import com.example.android_final.model.Model;
import com.example.android_final.model.Post;

import java.util.List;


public class MainFeedFragment extends Fragment {

//    FragmentMainFeedBinding binding;
    List<Post> data;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_main_feed, container, false);
        data = Model.instance().getAllPosts();
        RecyclerView list = view.findViewById(R.id.posts_RecyclerList);
        list.setHasFixedSize(true);

        list.setLayoutManager(new LinearLayoutManager(getContext()));
        PostRecyclerAdapter adapter = new PostRecyclerAdapter(getLayoutInflater(), data);
        list.setAdapter(adapter);

        adapter.setOnItemClickListener(new PostRecyclerAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int pos) {
                Log.d("TAG", "Row was clicked: " + pos);


            }
        });
        View button = view.findViewById(R.id.main_feedFrag_add_btn);
        button.setOnClickListener(Navigation.createNavigateOnClickListener(R.id.action_mainFeedFragment_to_addPostFragment));
        return view;
    }


}