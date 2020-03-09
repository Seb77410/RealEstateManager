package com.openclassrooms.realestatemanager.Database.Dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.openclassrooms.realestatemanager.Models.HouseSeller;
import com.openclassrooms.realestatemanager.Models.InterestPoint;

@Dao
public interface InterestPointDAO {

    @Insert
    long createInterestPoint(InterestPoint interestPoint);

    @Update
    int updateInterestPoint(InterestPoint interestPoint);

    @Query("SELECT * FROM InterestPoint WHERE id = :interestPointId")
    LiveData<InterestPoint> getInterestPointById(long interestPointId);

    @Query("DELETE FROM InterestPoint WHERE id = :interestPointId")
    int deleteInterestPoint(long interestPointId);
}
