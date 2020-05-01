package com.openclassrooms.realestatemanager.ui.activities.Map;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.openclassrooms.realestatemanager.models.database.Property;
import com.openclassrooms.realestatemanager.database.repositories.PropertyDataRepository;

import java.util.List;

class MapViewModel extends ViewModel {

    // --- Repositories ---
    private final PropertyDataRepository propertyDataSource;

    // --- Constructors ---
    MapViewModel(PropertyDataRepository propertyDataSource){
        this.propertyDataSource = propertyDataSource;
    }

    // --- Get properties ---
    LiveData<List<Property>> getProperties(){
        return propertyDataSource.getAllProperties();
    }
}
