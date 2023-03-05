package com.example.android_final.model;
import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

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

    public static Post fromJson(Map<String, Object> json){
        String userName = (String)json.get("userName");
        String postTextContent = (String)json.get("postTextContent");
        Post p = new Post(userName, postTextContent);
        return p;
    }

    public Map<String, Object> toJson(){
        Map<String, Object> json = new HashMap<>();
        json.put("userName", getUserName());
        json.put("postTextContent", getPostTextContent());
        return json;
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
