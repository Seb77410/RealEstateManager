package com.openclassrooms.realestatemanager.ui.activities.AddProperties;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.RequestManager;
import com.openclassrooms.realestatemanager.R;
import com.openclassrooms.realestatemanager.models.database.Media;

import java.util.ArrayList;

public class AddPhotosRecyclerAdapter extends RecyclerView.Adapter<AddPhotosRecyclerViewHolder> {

    private RequestManager glide;
    private ArrayList<Media> mediaList;
    private ArrayList<Long> mediaToDelete;

    AddPhotosRecyclerAdapter(ArrayList<Media> mediaList, RequestManager glide){
        this.mediaList = mediaList;
        this.glide = glide;
    }

    AddPhotosRecyclerAdapter(ArrayList<Media> mediaList, ArrayList<Long> mediaToDelete, RequestManager glide){
        this.mediaList = mediaList;
        this.mediaToDelete = mediaToDelete;
        this.glide = glide;
    }

    @NonNull
    @Override
    public AddPhotosRecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Create view holder and inflating its xml layout
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.activity_add_property_recycler_item, parent, false);
        return new AddPhotosRecyclerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AddPhotosRecyclerViewHolder holder, int position) {
        if (mediaToDelete != null) {
            holder.updateWithUriList(mediaList, mediaToDelete, glide, this);
        } else {
            holder.updateWithUriList(mediaList, glide, this);
        }
    }

    @Override
    public int getItemCount() {
        return mediaList.size();
    }
}
