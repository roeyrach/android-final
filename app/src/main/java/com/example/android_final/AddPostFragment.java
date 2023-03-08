package com.example.android_final;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.view.MenuProvider;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.android_final.databinding.FragmentAddPostBinding;
import com.example.android_final.model.Model;
import com.example.android_final.model.Post;

public class AddPostFragment extends Fragment {

    FragmentAddPostBinding binding;
    ActivityResultLauncher<Void> cameraLauncher;
    ActivityResultLauncher<String> galleryLauncher;
    UserViewModel userViewModel;

    Boolean isAvatarSelected = false;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FragmentActivity parentActivity = getActivity();
        parentActivity.addMenuProvider(new MenuProvider() {
            @Override
            public void onCreateMenu(@NonNull Menu menu, @NonNull MenuInflater menuInflater) {
                menu.removeItem(R.id.addPostFragment);
            }

            @Override
            public boolean onMenuItemSelected(@NonNull MenuItem menuItem) {
                return false;
            }
        },this, Lifecycle.State.RESUMED);

        cameraLauncher = registerForActivityResult(new ActivityResultContracts.TakePicturePreview(), new ActivityResultCallback<Bitmap>() {
            @Override
            public void onActivityResult(Bitmap result) {
                if (result != null) {
                    binding.avatarImg.setImageBitmap(result);
                    isAvatarSelected = true;
                }
            }
        });
        galleryLauncher = registerForActivityResult(new ActivityResultContracts.GetContent(), new ActivityResultCallback<Uri>() {
            @Override
            public void onActivityResult(Uri result) {
                if (result != null){
                    binding.avatarImg.setImageURI(result);
                    isAvatarSelected = true;
                }
            }
        });
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentAddPostBinding.inflate(inflater,container,false);
        View view = binding.getRoot();
        userViewModel = new ViewModelProvider(this).get(UserViewModel.class);
        userViewModel.getCurrentUser().observe(getViewLifecycleOwner(),user -> {
            if (user != null) {
                binding.addPostUserName.getEditText().setText(user.getUserName());
            }
        });

        binding.addPostSave.setOnClickListener(view1->{
            String userName = binding.addPostUserName.getEditText().getText().toString();
            String postContext = binding.addPostPostContext.getEditText().getText().toString();
            Post post = new Post(userName,postContext);
            post.setAvatarUrl(userViewModel.getCurrentUser().getValue().getUserPet().getPetImageUrl());
            if (isAvatarSelected) {
                binding.avatarImg.setDrawingCacheEnabled(true);
                binding.avatarImg.buildDrawingCache();
                Bitmap bitmap = ((BitmapDrawable) binding.avatarImg.getDrawable()).getBitmap();
                Model.instance().uploadImage(userViewModel.getCurrentUser().getValue().getUserFirebaseID(), bitmap, url->{
                    post.setImageUrl(url);
                    Model.instance().addPost(post,(unused)->{

                    });
                    Model.instance().addPostToUser(userViewModel.getCurrentUser().getValue().getUserFirebaseID(),post,(unused)->{
                        Navigation.findNavController(view1).popBackStack();
                    });
                });
            } else {
                Model.instance().addPost(post,(unused)->{

                });
                Model.instance().addPostToUser(userViewModel.getCurrentUser().getValue().getUserFirebaseID(),post,(unused)->{
                    Navigation.findNavController(view1).popBackStack();
                });
            }
        });
        binding.addPostCancel.setOnClickListener(view1-> Navigation.findNavController(view1).popBackStack());

        binding.cameraButton.setOnClickListener(view1->{
            cameraLauncher.launch(null);
        });

        binding.galleryButton.setOnClickListener(view1->{
            galleryLauncher.launch("image/*");
        });
        return view;
    }
}