package com.openclassrooms.realestatemanager.ui.fragments.propertiesLisFragment;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.RequestManager;
import com.openclassrooms.realestatemanager.R;
import com.openclassrooms.realestatemanager.models.database.Property;

import java.util.List;

public class PropertiesListAdapter extends RecyclerView.Adapter<PropertiesListViewHolder> {

    private RequestManager glide;
    private List<Property> propertiesList;
    private Uri[] propertiesPhoto;


    PropertiesListAdapter (List<Property> propertiesList, Uri[] propertiesPreviewPhotoList, RequestManager glide){
        this.propertiesList = propertiesList;
        this.propertiesPhoto = propertiesPreviewPhotoList;
        this.glide = glide;
    }

    @NonNull
    @Override
    public PropertiesListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.fragment_properties_list_item, parent, false);
        return new PropertiesListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PropertiesListViewHolder holder, int position) {
        holder.updateWithPropertiesList(propertiesList.get(position), propertiesPhoto[position],glide);
    }


    @Override
    public int getItemCount() {
        return propertiesList.size();
    }
}
