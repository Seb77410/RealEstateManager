package com.openclassrooms.realestatemanager.ui.fragments.propertiesLisFragment;

import android.net.Uri;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.RequestManager;
import com.bumptech.glide.request.RequestOptions;
import com.openclassrooms.realestatemanager.R;
import com.openclassrooms.realestatemanager.models.database.Property;


class PropertiesListViewHolder extends RecyclerView.ViewHolder {

    private ImageView propertyImage;
    private TextView propertyPrice;
    private TextView propertyType;
    private TextView propertyAddress;
    private TextView propertySold;
    private Property property;
    private RequestManager glide;
    private Uri propertyPreviewPhoto;

    PropertiesListViewHolder(@NonNull View itemView) {
        super(itemView);
        propertyImage = itemView.findViewById(R.id.fragment_properties_list_item_imageView);
        propertyPrice = itemView.findViewById(R.id.fragment_properties_list_item_price);
        propertyType = itemView.findViewById(R.id.fragment_properties_list_item_type);
        propertyAddress = itemView.findViewById(R.id.fragment_properties_list_item_address);
        propertySold = itemView.findViewById(R.id.fragment_properties_list_item_sold);

    }

    void updateWithPropertiesList(Property property, Uri propertyPreviewPhoto, RequestManager glide) {
        this.property = property;
        this.glide = glide;
        this.propertyPreviewPhoto = propertyPreviewPhoto;
        setPropertyPrice();
        setPropertyImage();
        setPropertyType();
        setPropertyAddress();
        setPropertySold();
    }

    private void setPropertyPrice(){
        propertyPrice.setText(String.valueOf(property.getPrice()));
    }

    private void setPropertyImage(){
       // Log.e("Properties List", "image uri = " + propertyPreviewPhoto );
            glide.load(propertyPreviewPhoto)
                    .apply(RequestOptions.centerCropTransform())
                    .placeholder(R.drawable.ic_home_24px)
                    .error(R.drawable.ic_home_24px)
                    .into(propertyImage);
    }

    private void setPropertyType(){
        propertyType.setText(property.getType());
    }

    private void setPropertyAddress(){
        propertyAddress.setText(property.getAddress());
    }

    private void setPropertySold(){
        if(!property.getSold()){
            propertySold.setVisibility(View.GONE);
        }
    }




}
