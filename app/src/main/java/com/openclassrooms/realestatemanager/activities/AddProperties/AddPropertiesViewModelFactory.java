package com.openclassrooms.realestatemanager.activities.AddProperties;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.openclassrooms.realestatemanager.repositories.HouseSellerDataRepository;
import com.openclassrooms.realestatemanager.repositories.InterestPointDataRepository;
import com.openclassrooms.realestatemanager.repositories.MediaDataRepository;
import com.openclassrooms.realestatemanager.repositories.PropertyDataRepository;

import java.util.concurrent.Executor;

public class AddPropertiesViewModelFactory implements ViewModelProvider.Factory  {

    // --- Repository ---
    private final HouseSellerDataRepository houseSellerDataSource;
    private final InterestPointDataRepository interestPointDataSource;
    private final MediaDataRepository mediaDataSource;
    private final PropertyDataRepository propertyDataSource;
    private final Executor executor;

    // --- Constructor ---
    public AddPropertiesViewModelFactory(HouseSellerDataRepository houseSellerDataSource,
                                         InterestPointDataRepository interestPointDataSource,
                                         MediaDataRepository mediaDataSource,
                                         PropertyDataRepository propertyDataSource,
                                         Executor executor){
        this.houseSellerDataSource = houseSellerDataSource;
        this.interestPointDataSource = interestPointDataSource;
        this.mediaDataSource = mediaDataSource;
        this.propertyDataSource = propertyDataSource;
        this.executor = executor;
    }


    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if(modelClass.isAssignableFrom(AddPropertiesViewModel.class)){
            return (T) new AddPropertiesViewModel(houseSellerDataSource, interestPointDataSource, mediaDataSource, propertyDataSource, executor);
        }
        throw new IllegalArgumentException("Unknown ViewModel class");
    }

}
