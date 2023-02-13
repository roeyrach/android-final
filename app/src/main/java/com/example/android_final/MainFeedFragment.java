package com.example.android_final;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavDirections;
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

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;


public class MainFeedFragment extends Fragment {

    FragmentMainFeedBinding binding;
    List<Post> data = new LinkedList<>();
    PostRecyclerAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        binding = FragmentMainFeedBinding.inflate(inflater,container,false);
        View view = binding.getRoot();

        binding.postsRecyclerList.setHasFixedSize(true);
        binding.postsRecyclerList.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new PostRecyclerAdapter(getLayoutInflater(), data);
        binding.postsRecyclerList.setAdapter(adapter);

        adapter.setOnItemClickListener(new PostRecyclerAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int pos) {
                Log.d("TAG", "Row was clicked: " + pos);
            }
        });
        View addButton = view.findViewById(R.id.main_feedFrag_add_btn);
        addButton.setOnClickListener(Navigation.createNavigateOnClickListener(R.id.action_mainFeedFragment_to_addPostFragment));

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        reloadData();
    }

    void reloadData(){
        binding.progressBar.setVisibility(View.VISIBLE);
        Model.instance().getAllPosts((postsList)->{
            data = postsList;
            adapter.setData(data);
            binding.progressBar.setVisibility(View.GONE);
        });
    }
}