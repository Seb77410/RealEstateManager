package com.openclassrooms.realestatemanager.ui.fragments.propertyDetailsFragment;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.openclassrooms.realestatemanager.models.database.InterestPoint;
import com.openclassrooms.realestatemanager.models.database.Media;
import com.openclassrooms.realestatemanager.database.repositories.InterestPointDataRepository;
import com.openclassrooms.realestatemanager.database.repositories.MediaDataRepository;

import java.util.List;

class PropertyDetailsViewModel extends ViewModel {

    private final InterestPointDataRepository interestPointDataSource;
    private final MediaDataRepository mediaDataSource;

    PropertyDetailsViewModel(InterestPointDataRepository interestPointDataSource, MediaDataRepository mediaDataSource){
        this.interestPointDataSource = interestPointDataSource;
        this.mediaDataSource = mediaDataSource;
    }

    // --- For interest points ---
    LiveData<InterestPoint> getInterestPointById(long interestPointId){return interestPointDataSource.getInterestPoint(interestPointId); }

    // --- For Media ---
    LiveData<List<Media>> getMediasByPropertyId(long propertyId){return mediaDataSource.getMediaByPropertyId(propertyId);}


}

