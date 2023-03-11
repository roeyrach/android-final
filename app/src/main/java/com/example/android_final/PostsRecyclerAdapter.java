package com.example.android_final;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.android_final.databinding.FragmentEditPostBinding;
import com.example.android_final.databinding.FragmentUserAllPostsBinding;
import com.example.android_final.databinding.FragmentUserProfileBinding;
import com.example.android_final.model.Post;
import com.squareup.picasso.Picasso;

import java.util.List;

class PostViewHolder extends RecyclerView.ViewHolder {
    TextView userName;
    TextView postTextContent;
    ImageView avatarUrl;
    ImageView imageUrl;
    Button editPost;
    List<Post> data;

    public PostViewHolder(@NonNull View itemView, PostRecyclerAdapter.OnItemClickListener listener,
                          List<Post> data, String fragmentName) {
        super(itemView);
        this.data = data;
        userName = itemView.findViewById(R.id.userName);
        postTextContent = itemView.findViewById(R.id.postTextContent);
        avatarUrl = itemView.findViewById(R.id.avatarUrl);
        imageUrl = itemView.findViewById(R.id.post_list_row_image);
        editPost = itemView.findViewById(R.id.post_list_row_edit_post);
        if (fragmentName.equals("MainFeedFragment")) {
            editPost.setVisibility(View.GONE);
        } else {
            editPost.setVisibility(View.VISIBLE);
            editPost.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = editPost.getTag() != null ? (int) editPost.getTag() : -1;
                    if (listener != null) {
                        listener.onItemClick(pos);
                    }
                }
            });
        }
    }

    public void bind(Post post, int pos) {
        editPost.setTag(pos);
        userName.setText(post.getUserName());
        postTextContent.setText(post.getPostTextContent());
        if (post.getAvatarUrl() != null && post.getAvatarUrl().length() > 5) {
            Picasso.get().load(post.getAvatarUrl()).into(avatarUrl);
        }else{
            avatarUrl.setImageResource(R.drawable.avatar);
        }
        if (post.getImageUrl() != null && post.getImageUrl().length() > 5) {
            Picasso.get().load(post.getImageUrl()).into(imageUrl);
        }else{
            imageUrl.setImageResource(R.drawable.avatar);
        }

    }
}

class PostRecyclerAdapter extends RecyclerView.Adapter<PostViewHolder> {
    OnItemClickListener listener;


    public static interface OnItemClickListener {
        void onItemClick(int pos);
    }

    LayoutInflater inflater;
    List<Post> data;
    String fragmentName;

    public void setData(List<Post> data) {
        this.data = data;
        notifyDataSetChanged();
    }

    public PostRecyclerAdapter(LayoutInflater inflater, List<Post> data,String fragmentName) {
        this.inflater = inflater;
        this.data = data;
        this.fragmentName = fragmentName;
    }

    void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    @NonNull
    @Override
    public PostViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.post_list_row, parent, false);
        PostViewHolder holder = new PostViewHolder(view, listener, data, fragmentName);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull PostViewHolder holder, int position) {
        Post post = data.get(position);
        holder.bind(post, position);
    }

    @Override
    public int getItemCount() {
        if (data == null) return 0;
        return data.size();
    }
}