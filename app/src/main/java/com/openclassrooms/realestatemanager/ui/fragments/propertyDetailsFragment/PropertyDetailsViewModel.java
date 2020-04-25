package com.openclassrooms.realestatemanager.ui.fragments.propertyDetailsFragment;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.openclassrooms.realestatemanager.models.InterestPoint;
import com.openclassrooms.realestatemanager.models.Media;
import com.openclassrooms.realestatemanager.models.Property;
import com.openclassrooms.realestatemanager.repositories.InterestPointDataRepository;
import com.openclassrooms.realestatemanager.repositories.MediaDataRepository;
import com.openclassrooms.realestatemanager.repositories.PropertyDataRepository;

import java.util.List;
import java.util.concurrent.Executor;

public class PropertyDetailsViewModel extends ViewModel {

    private final InterestPointDataRepository interestPointDataSource;
    private final MediaDataRepository mediaDataSource;
    private final Executor executor;

    public PropertyDetailsViewModel(InterestPointDataRepository interestPointDataSource, MediaDataRepository mediaDataSource,Executor executor){
        this.interestPointDataSource = interestPointDataSource;
        this.mediaDataSource = mediaDataSource;
        this.executor = executor;
    }


    // --- For interest points ---
    public LiveData<InterestPoint> getInterestPointById(long interestPointId){return interestPointDataSource.getInterestPoint(interestPointId); }

    // --- For Media ---
    public LiveData<List<Media>> getMediasByPropertyId(long propertyId){return mediaDataSource.getMediaByPropertyId(propertyId);}


}

