package com.example.android_final;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.core.view.MenuProvider;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.android_final.databinding.FragmentEditProfileBinding;
import com.example.android_final.model.Model;
import com.example.android_final.model.User;
import com.squareup.picasso.Picasso;

public class EditProfileFragment extends Fragment {
    private FragmentEditProfileBinding binding;
    UserViewModel userViewModel;
    ImageView userImg;
    TextView petAge;
    TextView petName;
    ActivityResultLauncher<Void> cameraLauncher;
    ActivityResultLauncher<String> galleryLauncher;
    Boolean isAvatarSelected = false;

    ProgressBar progressBar;

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
        petAge = binding.petAge;
        petName = binding.petNameEt;
        progressBar = binding.progressBar3;
        userViewModel = new ViewModelProvider(this).get(UserViewModel.class);
        User mUser = userViewModel.getCurrentUser().getValue();
        assert mUser != null;
        String uri = mUser.getUserPet().getPetImageUrl();
        if (uri  != null && uri.length() > 5) {
            Picasso.get().load(uri).placeholder(R.drawable.avatar).into(userImg);
        }else{
            userImg.setImageResource(R.drawable.avatar);
        }

        binding.saveBtn.setOnClickListener(view -> {
            AlertDialogFragment dialog = new AlertDialogFragment();
            String userN = petAge.getText().toString();
            String petN = petName.getText().toString();
            if(userN.equals("") && petN.equals("")){
                dialog.setMessage("Empty input");
                dialog.show(getChildFragmentManager(),"TAG");
            }
            else{
                if (!userN.equals(""))
                {
                    mUser.getUserPet().setPetAge(userN);
                }
                if (!petN.equals(""))
                {
                    mUser.getUserPet().setPetName(petN);
                }
                progressBar.setVisibility(View.VISIBLE);
                if(isAvatarSelected) {
                    binding.avatarImg.setDrawingCacheEnabled(true);
                    binding.avatarImg.buildDrawingCache();
                    Bitmap bitmap = ((BitmapDrawable) binding.avatarImg.getDrawable()).getBitmap();
                    Model.instance().uploadImage(userN, bitmap, url -> {
                        if (url != null) {
                            mUser.getUserPet().setPetImageUrl(url);
                            Model.instance().editUser(mUser, (User) -> {
                                Log.d("TAG", "userEdited");
                                NavHostFragment.findNavController(EditProfileFragment.this).popBackStack();
                                progressBar.setVisibility(View.INVISIBLE);

                            });

                        }
                    });
                }else{
                    Model.instance().editUser(mUser, (User)->{
                        Log.d("TAG", "userEdited");
                        NavHostFragment.findNavController(EditProfileFragment.this).popBackStack();
                        progressBar.setVisibility(View.INVISIBLE);

                    });
                }

                Log.d("TAG", mUser.toJson().toString());
            }

        });

        binding.cancellBtn.setOnClickListener(view -> {
            NavHostFragment.findNavController(EditProfileFragment.this).popBackStack();
        });


        return binding.getRoot();

    }
}