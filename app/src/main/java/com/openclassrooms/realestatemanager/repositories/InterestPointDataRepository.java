package com.openclassrooms.realestatemanager.repositories;

import androidx.lifecycle.LiveData;

import com.openclassrooms.realestatemanager.database.Dao.InterestPointDAO;
import com.openclassrooms.realestatemanager.models.InterestPoint;

public class InterestPointDataRepository {

    private final InterestPointDAO interestPointDAO;

    public InterestPointDataRepository(InterestPointDAO interestPointDAO){this.interestPointDAO = interestPointDAO;}

    // --- CREATE ---
    public void createInterestPoint(InterestPoint interestPoint){this.interestPointDAO.createInterestPoint(interestPoint);}

    // --- GET ---
    public LiveData<InterestPoint> getInterestPoint(long interestPointId){return this.interestPointDAO.getInterestPointById((interestPointId));}

    // --- UPDATE ---
    public void updateInterestPoint(InterestPoint interestPoint){this.interestPointDAO.updateInterestPoint(interestPoint);}

    // --- DELETE ---
    public void deleteInterestPoint(long interestPointId){this.interestPointDAO.deleteInterestPoint(interestPointId);}
}
