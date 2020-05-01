package com.openclassrooms.realestatemanager.ui.activities.Map;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.openclassrooms.realestatemanager.database.repositories.PropertyDataRepository;

public class MapViewModelFactory implements ViewModelProvider.Factory {

    // --- Repository ---
    private final PropertyDataRepository propertyDataSource;

    // --- Constructor ---
    public MapViewModelFactory(PropertyDataRepository propertyDataSource){
        this.propertyDataSource = propertyDataSource;
    }


    @SuppressWarnings("unchecked")
    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if(modelClass.isAssignableFrom(MapViewModel.class)){
            return (T) new MapViewModel(propertyDataSource);
        }
        throw new IllegalArgumentException("Unknown ViewModel class");
    }
}
