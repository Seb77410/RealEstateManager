package com.openclassrooms.realestatemanager.ui.fragments.propertiesLisFragment;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.openclassrooms.realestatemanager.database.repositories.MediaDataRepository;
import com.openclassrooms.realestatemanager.database.repositories.PropertyDataRepository;

import java.util.concurrent.Executor;

public class PropertiesListViewModelFactory implements ViewModelProvider.Factory {

    // --- Repository ---
    private final PropertyDataRepository propertyDataSource;
    private final MediaDataRepository mediaDataSource;
    private final Executor executor;

    // --- Constructor ---
    public PropertiesListViewModelFactory(PropertyDataRepository propertyDataSource, MediaDataRepository mediaDataSource, Executor executor){
        this.propertyDataSource = propertyDataSource;
        this.mediaDataSource = mediaDataSource;
        this.executor = executor;
    }

    @SuppressWarnings("unchecked")
    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if(modelClass.isAssignableFrom(PropertiesListViewModel.class)){

            return (T) new PropertiesListViewModel(propertyDataSource, mediaDataSource,executor);
        }
        throw new IllegalArgumentException("Unknown ViewModel class");
    }
}
