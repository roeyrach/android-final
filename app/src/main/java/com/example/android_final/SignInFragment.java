package com.example.android_final;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.view.MenuProvider;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavDirections;
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
       userViewModel = UserViewModel.getInstance();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentSignInBinding.inflate(inflater, container, false);

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
            Model.instance().signInUser("Guy@guy.guy", "123456",(User)->{
                Log.d("TAG", "userFound");
                NavHostFragment.findNavController(SignInFragment.this).navigate(R.id.action_signInFragment_to_mainFeedFragment);
            });

        });
        // Inflate the layout for this fragment
        return binding.getRoot();
    }
}