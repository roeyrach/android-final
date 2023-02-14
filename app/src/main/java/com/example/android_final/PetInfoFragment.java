package com.example.android_final;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.example.android_final.databinding.FragmentPetInfoBinding;
import com.example.android_final.model.Model;
import com.example.android_final.model.Pet;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


public class PetInfoFragment extends Fragment {

    FragmentPetInfoBinding binding;
    ActivityResultLauncher<Void> cameraLauncher;
    ActivityResultLauncher<String> galleryLauncher;
    Boolean isAvatarSelected = false;
    PetInfoFragmentArgs args;

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
         binding = FragmentPetInfoBinding.inflate(inflater, container, false);
         args = PetInfoFragmentArgs.fromBundle(getArguments());
        Spinner spinner = binding.spinner;
        spinner.setPrompt("Select Gender");

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity(),R.array.gender, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);

        adapter.setDropDownViewResource(com.google.android.material.R.layout.support_simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String text = adapterView.getItemAtPosition(i).toString();
                System.out.println(text);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        binding.cameraButton.setOnClickListener(view -> {
            cameraLauncher.launch(null);
        });

        binding.galleryButton.setOnClickListener(view -> {
            galleryLauncher.launch("image/*");
        });

        binding.saveBtn.setOnClickListener(view -> {
            binding.petInfoProgressbar.setVisibility(View.VISIBLE);
            String name = args.getUserName();
            String email = args.getUserEmail();
            String password = args.getUserPassword();
            String petName = Objects.requireNonNull(binding.petNameEt.getText()).toString();
            String petGender = binding.spinner.getSelectedItem().toString();
            String petAge = Objects.requireNonNull(binding.petAgeEt.getText()).toString();
            String url = "";
            Pet pet = new Pet(petName,url,petAge,petGender);
            if(isAvatarSelected){
                binding.avatarImg.setDrawingCacheEnabled(true);
                binding.avatarImg.buildDrawingCache();
                Bitmap bitmap = ((BitmapDrawable) binding.avatarImg.getDrawable()).getBitmap();
                Model.instance().uploadImage(name,bitmap,uri-> {
                    if (uri != null) {
                        pet.setPetImageUrl(uri);
                    }
                    Model.instance().signUpUser(name, email, password, pet, (unused) -> {
                        NavHostFragment.findNavController(PetInfoFragment.this).navigate(R.id.action_petInfoFragment_to_mainFeedFragment);
                        binding.petInfoProgressbar.setVisibility(View.GONE);
                    });
                });
            }else{
                Model.instance().signUpUser(name, email ,password,pet, (unused)->{
                    NavHostFragment.findNavController(PetInfoFragment.this).navigate(R.id.action_petInfoFragment_to_mainFeedFragment);
                    binding.petInfoProgressbar.setVisibility(View.GONE);

                });
            }


        });

        binding.cancellBtn.setOnClickListener(view -> {
            NavHostFragment.findNavController(PetInfoFragment.this).navigate(R.id.action_petInfoFragment_to_signUpFragment);
        });


         return binding.getRoot();
    }



}