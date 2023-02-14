package com.example.android_final;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.android_final.model.Animal;
import com.example.android_final.model.Post;
import com.squareup.picasso.Picasso;

import java.util.List;

class AnimalViewHolder extends RecyclerView.ViewHolder {

    TextView animalName;
    TextView playfulness;
    TextView goodWithKids;
    ImageView avatar;

    List<Animal> data;

    public AnimalViewHolder(@NonNull View itemView ,AnimalRecyclerAdapter.OnItemClickListener listener, List<Animal> data) {
        super(itemView);
        this.data = data;
        animalName = itemView.findViewById(R.id.animalName);
        playfulness = itemView.findViewById(R.id.playfulnessTV);
        goodWithKids = itemView.findViewById(R.id.good_with_kidsTV);
        avatar = itemView.findViewById(R.id.avatarImg);

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int pos = getAdapterPosition();
                if (listener != null) {
                    listener.onItemClick(pos);
                }
            }
        });
    }

    public void bind(Animal animal, int pos){
        String name = "Type: " + animal.getName();
        String goodWith = "Good with kids: " + animal.getGoodWithChildren();
        String playfull = "Playfullness: " + animal.getPlayFulness();
        animalName.setText(name);
        playfulness.setText(playfull);
        goodWithKids.setText(goodWith);
        if (animal.getImageUrl()  != null && animal.getImageUrl().length() > 5) {
            Picasso.get().load(animal.getImageUrl()).placeholder(R.drawable.avatar).into(avatar);
        }else{
            avatar.setImageResource(R.drawable.avatar);
        }

    }
}

public class AnimalRecyclerAdapter extends RecyclerView.Adapter<AnimalViewHolder> {
    OnItemClickListener listener;
    public static interface OnItemClickListener{
        void onItemClick(int pos);
    }

    LayoutInflater inflater;
    List<Animal> data;
    public void setData(List<Animal> data){
        this.data = data;
        notifyDataSetChanged();
    }
    public AnimalRecyclerAdapter(LayoutInflater inflater, List<Animal> data){
        this.inflater = inflater;
        this.data = data;
    }

    void setOnItemClickListener(OnItemClickListener listener){
        this.listener = listener;
    }


    @NonNull
    @Override
    public AnimalViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.fragment_animal_card, parent, false);
        AnimalViewHolder holder = new AnimalViewHolder(view, listener, data);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull AnimalViewHolder holder, int position) {
        Animal animal = data.get(position);
        holder.bind(animal,position);
    }

    @Override
    public int getItemCount() {

        if(data == null) return 0;
        return data.size();
    }
}
