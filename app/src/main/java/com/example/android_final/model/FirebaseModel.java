package com.example.android_final.model;

import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthSettings;
import com.google.firebase.auth.FirebaseUser;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class FirebaseModel {

    private FirebaseAuth auth;
    private Executor executor = Executors.newSingleThreadExecutor();


    FirebaseModel(){
        auth = FirebaseAuth.getInstance();


    }


    public void signInUser(String email, String password){
        auth.signInWithEmailAndPassword(email,password).addOnCompleteListener( executor, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    Log.d("Tag", "signIn success");
                }
                else{
                    Log.d("tag", "signIn failed");
                }
            }
        });

    }

    public void signUpUser(String email, String password){
        auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener( executor, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d("TAG", "createUserWithEmail:success");
                            FirebaseUser user = auth.getCurrentUser();

                        } else {
                            // If sign in fails, display a message to the user.
                            Log.d("TAG", "createUserWithEmail:failure", task.getException());

                        }
                    }
                });
    }

    public void getUser(){

    }

}
