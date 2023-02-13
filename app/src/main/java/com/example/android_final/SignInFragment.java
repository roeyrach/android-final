package com.example.android_final;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.android_final.databinding.FragmentSignInBinding;
import com.example.android_final.databinding.FragmentSignUpBinding;


public class SignInFragment extends Fragment {

    FragmentSignInBinding binding;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentSignInBinding.inflate(inflater, container, false);
        binding.signUnBtn.setOnClickListener((view)->{
            NavHostFragment.findNavController(SignInFragment.this).navigate(R.id.action_signInFragment_to_signUpFragment);
        });
        binding.signInBtn.setOnClickListener(view->{
            NavHostFragment.findNavController(SignInFragment.this).navigate(R.id.action_signInFragment_to_mainFeedFragment);
        });
        // Inflate the layout for this fragment
        return binding.getRoot();
    }
}