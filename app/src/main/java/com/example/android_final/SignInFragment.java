package com.example.android_final;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavDirections;
import androidx.navigation.fragment.NavHostFragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.android_final.databinding.FragmentSignInBinding;
import com.example.android_final.databinding.FragmentSignUpBinding;
import com.example.android_final.model.Model;


public class SignInFragment extends Fragment {

    FragmentSignInBinding binding;
    UserViewModel userViewModel;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       userViewModel = UserViewModel.getInstance();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentSignInBinding.inflate(inflater, container, false);
        binding.signUnBtn.setOnClickListener((view)->{
            NavHostFragment.findNavController(SignInFragment.this).navigate(R.id.action_signInFragment_to_signUpFragment);
        });
        binding.signInBtn.setOnClickListener(view->{
            Model.instance().signInUser("Guy@guy.guy", "123456",(User)->{
                Log.d("TAG", "userFound");
                userViewModel.setCurrentUser(User);

                NavHostFragment.findNavController(SignInFragment.this).navigate(R.id.action_signInFragment_to_mainFeedFragment);
            });

        });
        // Inflate the layout for this fragment
        return binding.getRoot();
    }
}