package com.example.android_final;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.view.MenuProvider;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.example.android_final.databinding.FragmentSignInBinding;
import com.example.android_final.databinding.FragmentSignUpBinding;
import com.example.android_final.model.Model;
import com.example.android_final.model.User;


public class SignInFragment extends Fragment {

    FragmentSignInBinding binding;
    UserViewModel userViewModel;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FragmentActivity parentActivity = getActivity();
        parentActivity.addMenuProvider(new MenuProvider() {
            @Override
            public void onCreateMenu(@NonNull Menu menu, @NonNull MenuInflater menuInflater) {
                menu.findItem(R.id.userProfile).setVisible(false);
                menu.findItem(R.id.LogOut).setVisible(false);
            }

            @Override
            public boolean onMenuItemSelected(@NonNull MenuItem menuItem) {
                return false;
            }
        },this, Lifecycle.State.RESUMED);
          userViewModel = new ViewModelProvider(this).get(UserViewModel.class);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentSignInBinding.inflate(inflater, container, false);
        AlertDialogFragment dialog = new AlertDialogFragment();

        userViewModel.getCurrentUser().observe(getViewLifecycleOwner(), new Observer<User>() {
            @Override
            public void onChanged(User user) {
                if(user != null){
                  NavHostFragment.findNavController(SignInFragment.this).navigate(R.id.action_signInFragment_to_mainFeedFragment);
                }
            }
        });

        binding.signUnBtn.setOnClickListener((view)->{
            NavHostFragment.findNavController(SignInFragment.this).navigate(R.id.action_signInFragment_to_signUpFragment);
        });
        binding.signInBtn.setOnClickListener(view->{
          String userName = binding.editTextUserName.getEditText().getText().toString();
          String password = binding.signInPassword.getEditableText().toString();
            if(userName.equals("") || password.equals("")){
                dialog.setMessage("Empty input");
                dialog.show(getChildFragmentManager(),"TAG");
            }
            else {
                binding.progressBar2.setVisibility(View.VISIBLE);
                Model.instance().signInUser(userName, password,(User)->{
                    if (User != null){
                        Log.d("TAG", "userFound");
                        NavHostFragment.findNavController(SignInFragment.this).navigate(R.id.action_signInFragment_to_mainFeedFragment);
                    }
                    else {
                        binding.progressBar2.setVisibility(View.INVISIBLE);
                        dialog.setMessage("Email or password are incorrect");
                        dialog.show(getChildFragmentManager(),"TAG");
                    }
                });
            }


        });
        // Inflate the layout for this fragment
        return binding.getRoot();
    }
}