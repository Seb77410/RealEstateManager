package com.openclassrooms.realestatemanager.database.Dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.openclassrooms.realestatemanager.models.InterestPoint;

import java.util.ArrayList;

@Dao
public interface InterestPointDAO {

    @Insert
    long createInterestPoint(InterestPoint interestPoint);

    @Update
    void updateInterestPoint(InterestPoint interestPoint);

    @Query("SELECT * FROM InterestPoint WHERE category = :list")
    LiveData<InterestPoint> getInterestPointByList(String list);

    @Query("SELECT * FROM InterestPoint WHERE id = :interestPointId")
    LiveData<InterestPoint> getInterestPointById(long interestPointId);

    @Query("DELETE FROM InterestPoint WHERE id = :interestPointId")
    void deleteInterestPoint(long interestPointId);

    @Query("SELECT * FROM InterestPoint ORDER BY id DESC LIMIT 1")
    LiveData<InterestPoint> getLastInterestPointSaved();

}
