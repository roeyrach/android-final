package com.example.android_final;

import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;


import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.android_final.databinding.FragmentAnimalApiListBinding;
import com.example.android_final.model.Animal;
import com.example.android_final.model.AnimalApi;
import com.example.android_final.model.AnimalModel;

import java.util.List;
import java.util.Objects;


public class AnimalApiListFragment extends Fragment {
    FragmentAnimalApiListBinding binding;
    AnimalRecyclerAdapter adapter;
    AnimalApiFragmentViewModel viewModel;


    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        viewModel = new ViewModelProvider(this).get(AnimalApiFragmentViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentAnimalApiListBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        LiveData<List<Animal>> data = viewModel.getData();
        data.observe(getViewLifecycleOwner(),list->{
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                list.forEach(item-> {
                    Log.d("Tag", "getAnimal: " + item.getName() );
                });
            }

        });

        binding.animalApiRecyclerView.setHasFixedSize(true);
        binding.animalApiRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new AnimalRecyclerAdapter(getLayoutInflater(),viewModel.getData().getValue());
        binding.animalApiRecyclerView.setAdapter(adapter);

        adapter.setOnItemClickListener(new AnimalRecyclerAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int pos) {
                Log.d("TAG", "Row was clicked " + pos);
                Animal animal = Objects.requireNonNull(viewModel.getData().getValue()).get(pos);
//                AnimalApiListFragmentDerction

            }
        });

        viewModel.getData().observe(getViewLifecycleOwner(), list->{
                adapter.setData(list);
        });

        AnimalModel.instance().EventStudentsListLoadingState.observe(getViewLifecycleOwner(),status->{
            binding.swipeRefresh.setRefreshing(status == AnimalModel.LoadingState.LOADING);
        });

//        binding.swipeRefresh.setOnRefreshListener(()-> {
////            reloadData();
//        });


    return view;
    }

     void reloadData() {
        AnimalModel.instance().refreshAllAnimals();
    }
}