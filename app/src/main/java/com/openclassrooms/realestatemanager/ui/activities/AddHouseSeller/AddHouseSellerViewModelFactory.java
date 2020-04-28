package com.openclassrooms.realestatemanager.ui.activities.AddHouseSeller;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.openclassrooms.realestatemanager.database.repositories.HouseSellerDataRepository;

import java.util.concurrent.Executor;

public class AddHouseSellerViewModelFactory implements ViewModelProvider.Factory {

    // --- Repository ---
    private final HouseSellerDataRepository houseSellerDataSource;
    private final Executor executor;

    // --- Constructor ---
    public AddHouseSellerViewModelFactory(HouseSellerDataRepository houseSellerDataSource, Executor executor){
        this.houseSellerDataSource = houseSellerDataSource;
        this.executor = executor;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if(modelClass.isAssignableFrom(AddHouseSellerViewModel.class)){
            return (T) new AddHouseSellerViewModel(houseSellerDataSource, executor);
        }
        throw new IllegalArgumentException("Unknown ViewModel class");
    }
}
