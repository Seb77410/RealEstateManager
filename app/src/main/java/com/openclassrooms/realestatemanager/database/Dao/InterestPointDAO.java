package com.openclassrooms.realestatemanager.database.Dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.openclassrooms.realestatemanager.models.InterestPoint;

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
