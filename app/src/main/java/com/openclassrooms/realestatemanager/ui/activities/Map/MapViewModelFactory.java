package com.openclassrooms.realestatemanager.ui.activities.Map;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.openclassrooms.realestatemanager.database.repositories.PropertyDataRepository;

import java.util.concurrent.Executor;

public class MapViewModelFactory implements ViewModelProvider.Factory {

    // --- Repository ---
    private final PropertyDataRepository propertyDataSource;
    private final Executor executor;

    // --- Constructor ---
    public MapViewModelFactory(PropertyDataRepository propertyDataSource, Executor executor){
        this.propertyDataSource = propertyDataSource;
        this.executor = executor;
    }



    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if(modelClass.isAssignableFrom(MapViewModel.class)){
            return (T) new MapViewModel(propertyDataSource, executor);
        }
        throw new IllegalArgumentException("Unknown ViewModel class");
    }
}
