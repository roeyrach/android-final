package com.example.android_final.model;

import android.graphics.Bitmap;
import android.net.Uri;
import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreSettings;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.util.LinkedList;
import java.util.List;

public class FirebaseModel {

    FirebaseAuth auth;
    FirebaseFirestore db;
    FirebaseStorage storage;


    FirebaseModel() {
        auth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
        FirebaseFirestoreSettings settings = new FirebaseFirestoreSettings.Builder()
                .setPersistenceEnabled(false)
                .build();
        db.setFirestoreSettings(settings);
        storage = FirebaseStorage.getInstance();

    }

    public void getAllPostsSince(Long since, Model.Listener<List<Post>> callback) {
        db.collection(Post.COLLECTION)
                .whereGreaterThanOrEqualTo(Post.LAST_UPDATED,new Timestamp(since,0))
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                List<Post> list = new LinkedList<>();
                if (task.isSuccessful()) {
                    QuerySnapshot jsonsList = task.getResult();
                    for (DocumentSnapshot json : jsonsList) {
                        Post p = Post.fromJson(json.getData());
                        list.add(p);
                    }
                }
                callback.onComplete(list);
            }
        });
    }

    public void addPost(Post p, Model.Listener<Void> listener) {
        db.collection(Post.COLLECTION).document(p.getPostId()).set(p.toJson()).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                System.out.println(p.getPostId());
                listener.onComplete(null);
            }
        });

    }

    public void signInUser(String email, String password, Model.Listener<FirebaseUser> listener) {
        auth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    Log.d("Tag", "signIn success");
                    FirebaseUser user = auth.getCurrentUser();
                    listener.onComplete(user);
                } else {
                    Log.d("tag", "signIn failed");
                }
            }
        });

    }

    public void signUpUser(String email, String password, Model.Listener<FirebaseUser> listener) {
        auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
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

    public void addUser(User user, Model.Listener<Void> listener) {
        db.collection(User.COLLECTION).document(user.getUserFirebaseID()).set(user.toJson())
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Log.d("TAG", "userAdded");
                        listener.onComplete(null);

                    }
                });
    }

    public void addPostToUser(String uid, Post post, Model.Listener<Void> listener) {
        db.collection(User.COLLECTION).document(uid).collection(Post.COLLECTION).document(post.getPostId()).set(post.toJson())
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Log.d("TAG", "postAdded");
                        listener.onComplete(null);

                    }
                });
    }

    public void getAllPostsOfUser(String uid, Model.Listener<List<Post>> callback) {
        db.collection(User.COLLECTION).document(uid).collection(Post.COLLECTION)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        List<Post> list = new LinkedList<>();
                        if (task.isSuccessful()) {
                            QuerySnapshot jsonsList = task.getResult();
                            for (DocumentSnapshot json : jsonsList) {
                                Post p = Post.fromJson(json.getData());
                                list.add(p);
                            }
                        }
                        callback.onComplete(list);
                    }
                });
    }

    public void getUser(String uid, Model.Listener<User> listener) {
        db.collection(User.COLLECTION).whereEqualTo("id", uid)
                .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            QuerySnapshot jsonList = task.getResult();
                            for (DocumentSnapshot json : jsonList) {
                                User user = User.fromJson(json.getData());
                                Log.d("TAG", "User found");
                                listener.onComplete(user);
                            }
                        }
                    }
                });
    }

    void uploadImage(String name, Bitmap bitmap, Model.Listener<String> listener) {
        StorageReference storageRef = storage.getReference();
        StorageReference imagesRef = storageRef.child("images/" + name + ".jpg");
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] data = baos.toByteArray();

        UploadTask uploadTask = imagesRef.putBytes(data);
        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                listener.onComplete(null);
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                imagesRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        listener.onComplete(uri.toString());
                    }
                });

            }
        });
    }


}
