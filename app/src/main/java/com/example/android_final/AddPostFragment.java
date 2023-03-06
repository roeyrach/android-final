package com.example.android_final;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.view.MenuProvider;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.Lifecycle;
import androidx.navigation.Navigation;

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
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentAddPostBinding.inflate(inflater,container,false);
        View view = binding.getRoot();
        binding.addPostSave.setOnClickListener(view1->{
            String un = binding.addPostUserName.getText().toString();
            String pc = binding.addPostPostContext.getText().toString();
            Post post = new Post(un,pc);
            System.out.println(post.getPostId());
            Model.instance().addPost(post,(unused)->{
                Navigation.findNavController(view1).popBackStack();
            });
        });
        binding.addPostCancel.setOnClickListener(view1-> Navigation.findNavController(view1).popBackStack());
        return view;
    }
}