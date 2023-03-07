package com.example.android_final;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
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
import com.example.android_final.model.User;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;


public class MainFeedFragment extends Fragment {

    FragmentMainFeedBinding binding;
    PostRecyclerAdapter adapter;
    PostsListFragmentViewModel viewModel;
    UserViewModel userViewModel;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        binding = FragmentMainFeedBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        binding.postsRecyclerList.setHasFixedSize(true);
        binding.postsRecyclerList.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new PostRecyclerAdapter(getLayoutInflater(), viewModel.getData().getValue());
        binding.postsRecyclerList.setAdapter(adapter);

        adapter.setOnItemClickListener(new PostRecyclerAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int pos) {
                Log.d("TAG", "Row was clicked: " + pos);
            }
        });
        View addButton = view.findViewById(R.id.main_feedFrag_add_btn);
        addButton.setOnClickListener(Navigation.createNavigateOnClickListener(R.id.action_mainFeedFragment_to_addPostFragment));
        binding.progressBar.setVisibility(View.GONE);
        viewModel.getData().observe(getViewLifecycleOwner(), list -> {
            adapter.setData(list);

        });

        Model.instance().EventPostListLoadingState.observe(getViewLifecycleOwner(), status -> {
            binding.swipeRefresh.setRefreshing(status == Model.LoadingState.LOADING);
        });

        binding.swipeRefresh.setOnRefreshListener(() -> {
            reloadData();
        });

        return view;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        viewModel = new ViewModelProvider(this).get(PostsListFragmentViewModel.class);
    }

    void reloadData() {
        Model.instance().refreshAllPosts();

    }
}