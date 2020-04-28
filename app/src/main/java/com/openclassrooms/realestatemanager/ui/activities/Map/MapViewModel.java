package com.openclassrooms.realestatemanager.ui.activities.Map;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.openclassrooms.realestatemanager.models.database.Property;
import com.openclassrooms.realestatemanager.database.repositories.PropertyDataRepository;

import java.util.List;
import java.util.concurrent.Executor;

public class MapViewModel extends ViewModel {

    // --- Repositories ---
    private final PropertyDataRepository propertyDataSource;
    private final Executor executor;

    // --- Constructors ---
    public MapViewModel(PropertyDataRepository propertyDataSource, Executor executor){
        this.propertyDataSource = propertyDataSource;
        this.executor = executor;
    }

    // --- Get properties ---
    LiveData<List<Property>> getProperties(){
        return propertyDataSource.getAllProperties();
    }
}
