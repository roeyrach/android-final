package com.example.android_final;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.example.android_final.SignUpFragmentDirections.ActionSignUpFragmentToPetInfoFragment;
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
            AlertDialogFragment dialog = new AlertDialogFragment();

          String user = Objects.requireNonNull(binding.usernameEt.getText()).toString();
            String email = Objects.requireNonNull(binding.emailEt.getText()).toString();
            String password = Objects.requireNonNull(binding.passwordEt.getText()).toString();
            String rePassword = Objects.requireNonNull(binding.rePasswordEt.getText()).toString();
            if(password.length() >5 &&  password.equals(rePassword)){
                ActionSignUpFragmentToPetInfoFragment action = SignUpFragmentDirections.actionSignUpFragmentToPetInfoFragment();
                action.setUserName(user).setUserEmail(email).setUserPassword(password);
                NavHostFragment.findNavController(SignUpFragment.this).navigate(action);
            }else{
                dialog.setMessage("Password does not match");
                dialog.show(getChildFragmentManager(),"TAG");
                Log.d("Tag", "password does not  match");
            }

        });


        binding.cancelBtn.setOnClickListener(view -> {
            Navigation.findNavController(view).popBackStack();
        });

        return binding.getRoot();

    }
}