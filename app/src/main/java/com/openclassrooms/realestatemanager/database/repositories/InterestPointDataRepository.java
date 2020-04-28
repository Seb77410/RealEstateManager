package com.openclassrooms.realestatemanager.database.repositories;

import androidx.lifecycle.LiveData;

import com.openclassrooms.realestatemanager.database.Dao.InterestPointDAO;
import com.openclassrooms.realestatemanager.models.database.InterestPoint;

public class InterestPointDataRepository {

    private final InterestPointDAO interestPointDAO;

    public InterestPointDataRepository(InterestPointDAO interestPointDAO){this.interestPointDAO = interestPointDAO;}

    // --- CREATE ---
    public long createInterestPoint(InterestPoint interestPoint){return this.interestPointDAO.createInterestPoint(interestPoint);}

    // --- GET ---
    public LiveData<InterestPoint> getInterestPoint(long interestPointId){return this.interestPointDAO.getInterestPointById((interestPointId));}
    public LiveData<InterestPoint> getInterestPointByList(String list){return this.interestPointDAO.getInterestPointByList(list);}
    public LiveData<InterestPoint> getLastInterestPointSaved(){return this.interestPointDAO.getLastInterestPointSaved();}
    // --- UPDATE ---
    public void updateInterestPoint(InterestPoint interestPoint){this.interestPointDAO.updateInterestPoint(interestPoint);}

    // --- DELETE ---
    public void deleteInterestPoint(long interestPointId){this.interestPointDAO.deleteInterestPoint(interestPointId);}
}
