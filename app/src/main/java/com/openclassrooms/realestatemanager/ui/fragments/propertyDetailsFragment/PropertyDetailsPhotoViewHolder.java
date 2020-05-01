package com.openclassrooms.realestatemanager.ui.fragments.propertyDetailsFragment;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.RequestManager;
import com.bumptech.glide.request.RequestOptions;
import com.openclassrooms.realestatemanager.R;
import com.openclassrooms.realestatemanager.models.database.Media;

class PropertyDetailsPhotoViewHolder extends RecyclerView.ViewHolder {
    private ImageView imageView;
    private RequestManager glide;
    private Media media;
    private TextView title;

    PropertyDetailsPhotoViewHolder(@NonNull View itemView) {
        super(itemView);

        imageView = itemView.findViewById(R.id.fragment_property_details_item_image_view);
        title = itemView.findViewById(R.id.fragment_property_details_item_title);
    }

    void updateWithMedia(Media media, RequestManager glide){
        this.media = media;
        this.glide = glide;
        setImageView();
        setTitle();
    }

    private void setImageView(){
        //Log.e("Properties List", "image uri = " + media.getMediaUri() );
        //Log.e("Properties List", "image uri = "  );

        glide.load(media.getMediaUri())
                .apply(RequestOptions.centerCropTransform())
                .placeholder(R.drawable.ic_home_marker_40)
                .error(R.drawable.ic_home_marker_40)
                .into(imageView);
    }

    private void setTitle(){
        title.setText(media.getComment());
    }
}
