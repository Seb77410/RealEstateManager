package com.openclassrooms.realestatemanager.ui.fragments.propertyDetailsFragment;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.openclassrooms.realestatemanager.database.repositories.InterestPointDataRepository;
import com.openclassrooms.realestatemanager.database.repositories.MediaDataRepository;

public class PropertyDetailsViewModelFactory implements ViewModelProvider.Factory {

    // --- Repository ---
    private final InterestPointDataRepository interestPointDataSource;
    private final MediaDataRepository mediaDataSource;

    // --- Constructor ---
    public PropertyDetailsViewModelFactory(InterestPointDataRepository interestPointDataSource, MediaDataRepository mediaDataSource){
        this.interestPointDataSource = interestPointDataSource;
        this.mediaDataSource = mediaDataSource;
    }

       @SuppressWarnings("unchecked")
    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if(modelClass.isAssignableFrom(PropertyDetailsViewModel.class)){

            return (T) new PropertyDetailsViewModel(interestPointDataSource, mediaDataSource);
        }
        throw new IllegalArgumentException("Unknown ViewModel class");
    }
}
