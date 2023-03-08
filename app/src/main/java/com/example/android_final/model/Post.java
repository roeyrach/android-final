package com.example.android_final.model;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.example.android_final.MyApplication;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.FieldValue;

import java.sql.Time;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Entity
public class Post {
    @PrimaryKey
    @NonNull
    public String postId = "";
    public String avatarUrl = "";
    public String userName = "";
    public String postTextContent = "";
    public String imageUrl = "";
    public Long lastUpdated;

    public Post() {
    }

    public Post(String userName, String postTextContent) {
        this.userName = userName;
        this.postTextContent = postTextContent;
        this.postId = UUID.randomUUID().toString();
    }

    static final String USER_NAME = "userName";
    static final String POST_TEXT_CONTEXT = "postTextContent";
    static final String COLLECTION = "posts";
    static final String LAST_UPDATED = "lastUpdated";
    static final String LOCAL_LAST_UPDATED = "posts_local_last_update";
    static final String ID = "id";
    static final String AVATAR_URL = "avatarUrl";

    public static Post fromJson(Map<String, Object> json) {
        String userName = (String) json.get(USER_NAME);
        String postTextContent = (String) json.get(POST_TEXT_CONTEXT);
        String avatarUrl = (String) json.get(AVATAR_URL);
        Post p = new Post(userName, postTextContent);
        p.setAvatarUrl(avatarUrl);
        p.setPostId((String) json.get(ID));
        try {
            Timestamp time = (Timestamp) json.get(LAST_UPDATED);
            p.setLastUpdated(time.getSeconds());
        } catch (Exception e) {
        }
        return p;
    }

    public Map<String, Object> toJson() {
        Map<String, Object> json = new HashMap<>();
        json.put(USER_NAME, getUserName());
        json.put(POST_TEXT_CONTEXT, getPostTextContent());
        json.put(ID, getPostId());
        json.put(LAST_UPDATED, FieldValue.serverTimestamp());
        json.put(AVATAR_URL, getAvatarUrl());
        return json;
    }

    public static Long getLocalLastUpdate() {
        SharedPreferences sharedPref = MyApplication.getMyContext().getSharedPreferences("TAG", Context.MODE_PRIVATE);
        return sharedPref.getLong(LOCAL_LAST_UPDATED, 0);
    }

    public static void setLocalLastUpdate(Long time) {
        SharedPreferences sharedPref = MyApplication.getMyContext().getSharedPreferences("TAG", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putLong(LOCAL_LAST_UPDATED,time);
        editor.commit();
    }

    public String getPostId() {
        return postId;
    }

    public void setPostId(String postId) {
        this.postId = postId;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPostTextContent() {
        return postTextContent;
    }

    public void setPostTextContent(String postTextContent) {
        this.postTextContent = postTextContent;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public Long getLastUpdated() {
        return lastUpdated;
    }

    public void setLastUpdated(Long lastUpdated) {
        this.lastUpdated = lastUpdated;
    }
}
