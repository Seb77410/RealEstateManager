package com.openclassrooms.realestatemanager.ui.fragments.propertyDetailsFragment;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.RequestManager;
import com.openclassrooms.realestatemanager.R;
import com.openclassrooms.realestatemanager.models.Media;

import java.util.List;

public class PropertyDetailsPhotoAdapter extends RecyclerView.Adapter<PropertyDetailsPhotoViewHolder> {
    RequestManager glide;
    List<Media> mediaList;

    public PropertyDetailsPhotoAdapter (List<Media> mediaList, RequestManager glide){
        this.mediaList = mediaList;
        this.glide = glide;
    }

    @NonNull
    @Override
    public PropertyDetailsPhotoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.fragment_property_details_photo_item, parent, false);

        ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
        layoutParams.width = (int) (parent.getWidth() * 0.4);
        view.setLayoutParams(layoutParams);

        return new PropertyDetailsPhotoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PropertyDetailsPhotoViewHolder holder, int position) {

        holder.updateWithMedia(mediaList.get(position),glide);
    }

    @Override
    public int getItemCount() {
        return mediaList.size();
    }
}
