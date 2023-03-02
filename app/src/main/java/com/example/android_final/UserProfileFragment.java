package com.example.android_final;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.view.MenuProvider;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.Lifecycle;
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
                menu.removeItem(R.id.userProfile);
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
        userViewModel = UserViewModel.getInstance();
         User mUser = userViewModel.getCurrentUser().getValue();
         userName.setText(mUser.getUserName());
         petName.setText(mUser.getUserPet().getPetName());
         petAge.setText(mUser.getUserPet().getPetAge());
         String uri = mUser.getUserPet().getPetImageUrl();
        if (uri  != null && uri.length() > 5) {
            Picasso.get().load(uri).placeholder(R.drawable.avatar).into(userImg);
        }else{
            userImg.setImageResource(R.drawable.avatar);
        }
         Log.d("TAG",mUser.getUserName());





        return binding.getRoot();
    }
}