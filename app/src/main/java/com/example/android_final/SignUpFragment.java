package com.example.android_final;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.android_final.databinding.FragmentSignUpBinding;

import java.util.Objects;


public class SignUpFragment extends Fragment {
    FragmentSignUpBinding binding;



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentSignUpBinding.inflate(inflater, container, false);

        binding.saveBtn.setOnClickListener(view -> {

          String user = Objects.requireNonNull(binding.usernameEt.getText()).toString();
            String email = Objects.requireNonNull(binding.emailEt.getText()).toString();
            String password = Objects.requireNonNull(binding.passwordEt).toString();
            String rePassword = Objects.requireNonNull(binding.rePasswordEt.getText()).toString();
            System.out.println(user );
            NavHostFragment.findNavController(SignUpFragment.this).navigate(R.id.action_signUpFragment_to_petInfoFragment);

        });

        binding.cancelBtn.setOnClickListener(view -> {
            binding.usernameEt.setText("");
            binding.emailEt.setText("");
            binding.passwordEt.setText("");
            binding.rePasswordEt.setText("");
        });

        return binding.getRoot();

    }
}