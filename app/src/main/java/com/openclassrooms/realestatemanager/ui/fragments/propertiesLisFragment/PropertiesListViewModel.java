package com.openclassrooms.realestatemanager.ui.fragments.propertiesLisFragment;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.openclassrooms.realestatemanager.models.database.Media;
import com.openclassrooms.realestatemanager.models.database.Property;
import com.openclassrooms.realestatemanager.database.repositories.MediaDataRepository;
import com.openclassrooms.realestatemanager.database.repositories.PropertyDataRepository;

import java.util.List;

class PropertiesListViewModel extends ViewModel {

    private final PropertyDataRepository propertyDataSource;
    private final MediaDataRepository mediaDataSource;

    PropertiesListViewModel(PropertyDataRepository propertyDataSource, MediaDataRepository mediaDataSource){
        this.propertyDataSource = propertyDataSource;
        this.mediaDataSource = mediaDataSource;
    }

    // --- For Properties ---
    LiveData<List<Property>> getPropertiesList(){return propertyDataSource.getAllProperties();}

    // --- For Media ---
    LiveData<List<Media>> getMediasByPropertyId(long propertyId){return mediaDataSource.getMediaByPropertyId(propertyId);}
}
