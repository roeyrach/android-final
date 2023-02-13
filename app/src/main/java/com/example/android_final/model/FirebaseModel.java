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
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreSettings;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.Map;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class FirebaseModel {

    private FirebaseAuth auth;
    FirebaseFirestore db;


    FirebaseModel(){
        auth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
        FirebaseFirestoreSettings settings = new FirebaseFirestoreSettings.Builder()
                .setPersistenceEnabled(false)
                .build();
        db.setFirestoreSettings(settings);


    }


    public void signInUser(String email, String password, Model.Listener<FirebaseUser> listener){
        auth.signInWithEmailAndPassword(email,password).addOnCompleteListener(  new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    Log.d("Tag", "signIn success");
                    FirebaseUser user = auth.getCurrentUser();
                    listener.onComplete(user);
                }
                else{
                    Log.d("tag", "signIn failed");
                }
            }
        });

    }

    public void signUpUser(String email, String password, Model.Listener<FirebaseUser> listener){
        auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener( new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d("TAG", "createUserWithEmail:success");
                            FirebaseUser user = auth.getCurrentUser();

                            listener.onComplete(user);

                        } else {
                            // If sign in fails, display a message to the user.
                            Log.d("TAG", "createUserWithEmail:failure", task.getException());

                        }
                    }
                });
    }

    public void addUser(User user, Model.Listener<Void> listener){
        db.collection(User.COLLECTION).document(user.getUserFirebaseID()).set(user.toJson())
                .addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                Log.d("TAG","userAdded");
                listener.onComplete(null);
            }
        });
    }

    public void getUser(String uid, Model.Listener<User> listener){
        db.collection(User.COLLECTION).whereEqualTo("id", uid)
                .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.isSuccessful()){
                            QuerySnapshot jsonList = task.getResult();
                            for (DocumentSnapshot json : jsonList){
                                User user = User.fromJson(json.getData());
                                Log.d("TAG","User found");
                                listener.onComplete(user);
                            }
                        }
                    }
                });
    }



}
