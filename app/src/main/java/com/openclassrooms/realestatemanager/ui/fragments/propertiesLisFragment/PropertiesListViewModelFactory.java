package com.openclassrooms.realestatemanager.ui.fragments.propertiesLisFragment;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.openclassrooms.realestatemanager.database.repositories.MediaDataRepository;
import com.openclassrooms.realestatemanager.database.repositories.PropertyDataRepository;


public class PropertiesListViewModelFactory implements ViewModelProvider.Factory {

    // --- Repository ---
    private final PropertyDataRepository propertyDataSource;
    private final MediaDataRepository mediaDataSource;

    // --- Constructor ---
    public PropertiesListViewModelFactory(PropertyDataRepository propertyDataSource, MediaDataRepository mediaDataSource){
        this.propertyDataSource = propertyDataSource;
        this.mediaDataSource = mediaDataSource;
    }

    @SuppressWarnings("unchecked")
    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if(modelClass.isAssignableFrom(PropertiesListViewModel.class)){

            return (T) new PropertiesListViewModel(propertyDataSource, mediaDataSource);
        }
        throw new IllegalArgumentException("Unknown ViewModel class");
    }
}
