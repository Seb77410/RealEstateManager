package com.openclassrooms.realestatemanager.ui.fragments.propertiesLisFragment;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.openclassrooms.realestatemanager.models.database.Media;
import com.openclassrooms.realestatemanager.models.database.Property;
import com.openclassrooms.realestatemanager.database.repositories.MediaDataRepository;
import com.openclassrooms.realestatemanager.database.repositories.PropertyDataRepository;

import java.util.List;
import java.util.concurrent.Executor;

public class PropertiesListViewModel extends ViewModel {

    private final PropertyDataRepository propertyDataSource;
    private final MediaDataRepository mediaDataSource;
    private final Executor executor;

    public PropertiesListViewModel(PropertyDataRepository propertyDataSource, MediaDataRepository mediaDataSource,Executor executor){
        this.propertyDataSource = propertyDataSource;
        this.mediaDataSource = mediaDataSource;
        this.executor = executor;
    }

    // --- For Properties ---
    public LiveData<List<Property>> getPropertiesList(){return propertyDataSource.getAllProperties();}

    // --- For Media ---
    public LiveData<List<Media>> getMediasByPropertyId(long propertyId){return mediaDataSource.getMediaByPropertyId(propertyId);}
}
