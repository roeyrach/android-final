package com.example.android_final;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.android_final.databinding.FragmentEditProfileBinding;
import com.example.android_final.model.FirebaseModel;
import com.example.android_final.model.Model;
import com.example.android_final.model.Pet;
import com.example.android_final.model.User;
import com.squareup.picasso.Picasso;

public class EditProfileFragment extends Fragment {
    private FragmentEditProfileBinding binding;
    UserViewModel userViewModel;
    ImageView userImg;
    TextView userName;
    TextView petName;
    ActivityResultLauncher<Void> cameraLauncher;
    ActivityResultLauncher<String> galleryLauncher;
    Boolean isAvatarSelected = false;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

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
        binding = FragmentEditProfileBinding.inflate(inflater,container,false);

        binding.cameraButton.setOnClickListener(view -> {
            cameraLauncher.launch(null);
        });

        binding.galleryButton.setOnClickListener(view -> {
            galleryLauncher.launch("image/*");
        });

        userImg = binding.avatarImg;
        userName = binding.userName;
        petName = binding.petNameEt;
        userViewModel = UserViewModel.getInstance();
        User mUser = userViewModel.getCurrentUser().getValue();
        assert mUser != null;
        String uri = mUser.getUserPet().getPetImageUrl();
        if (uri  != null && uri.length() > 5) {
            Picasso.get().load(uri).placeholder(R.drawable.avatar).into(userImg);
        }else{
            userImg.setImageResource(R.drawable.avatar);
        }

        binding.saveBtn.setOnClickListener(view -> {
            String userN = userName.getText().toString();
            String petN = petName.getText().toString();
            if (!userN.equals(""))
            {
                mUser.setUserName(userN);
            }
           if (!petN.equals(""))
           {
               mUser.getUserPet().setPetName(petN);
           }

            if(isAvatarSelected){
                binding.avatarImg.setDrawingCacheEnabled(true);
                binding.avatarImg.buildDrawingCache();
                Bitmap bitmap = ((BitmapDrawable) binding.avatarImg.getDrawable()).getBitmap();
                Model.instance().uploadImage(userN,bitmap, url-> {
                    if (url != null) {
                        mUser.getUserPet().setPetImageUrl(url);
                    }
                });
            }

            Log.d("TAG", mUser.toJson().toString());
        });

        binding.cancellBtn.setOnClickListener(view -> {
            NavHostFragment.findNavController(EditProfileFragment.this).popBackStack();
        });


        return binding.getRoot();

    }
}