package com.example.android_final;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.view.MenuProvider;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;

import com.squareup.picasso.Picasso;


import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.android_final.databinding.FragmentUserProfileBinding;
import com.example.android_final.model.Pet;
import com.example.android_final.model.User;


public class UserProfileFragment extends Fragment {
    FragmentUserProfileBinding binding;
    UserViewModel userViewModel;
    TextView userName,petName,petAge;
    ImageView userImg;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FragmentActivity parentActivity = getActivity();

        parentActivity.addMenuProvider(new MenuProvider() {
            @Override
            public void onCreateMenu(@NonNull Menu menu, @NonNull MenuInflater menuInflater) {
                menu.findItem(R.id.userProfile).setVisible(false);
            }

            @Override
            public boolean onMenuItemSelected(@NonNull MenuItem menuItem) {
                return false;
            }
        },this, Lifecycle.State.RESUMED);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentUserProfileBinding.inflate(inflater,container,false);
        userName = binding.profileName;
        petName = binding.profilePetName;
        petAge = binding.profilePetAge;
        userImg = binding.avatarImg;
        userViewModel = new ViewModelProvider(this).get(UserViewModel.class);
        userViewModel.getCurrentUser().observe(getViewLifecycleOwner(),user -> {
            if (user != null){
                Log.d("TAG", user.getUserName());
                userName.setText(user.getUserName());
                petName.setText(R.string.petName);
                petAge.setText(R.string.petAge);
                petName.append("\t\t" +  user.getUserPet().getPetName());
                petAge.append("\t\t" + user.getUserPet().getPetAge());
                String uri = user.getUserPet().getPetImageUrl();
                if (uri  != null && uri.length() > 5) {
                    Picasso.get().load(uri).placeholder(R.drawable.avatar).into(userImg);
                }else{
                    userImg.setImageResource(R.drawable.avatar);
                }
            }

        });
        binding.editProfileBtn.setOnClickListener(view -> {
            NavHostFragment.findNavController(UserProfileFragment.this).navigate(R.id.action_userProfile_to_editProfileFragment);
        });
        binding.userPostsBtn.setOnClickListener(view->{
            NavHostFragment.findNavController(UserProfileFragment.this).navigate(R.id.action_userProfile_to_user_all_posts);
        });



        return binding.getRoot();
    }
}