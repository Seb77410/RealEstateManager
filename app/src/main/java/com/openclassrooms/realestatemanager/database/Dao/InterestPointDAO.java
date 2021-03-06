package com.openclassrooms.realestatemanager.database.Dao;

import android.database.Cursor;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.openclassrooms.realestatemanager.models.database.InterestPoint;

@Dao
public interface InterestPointDAO {

    @Insert
    long createInterestPoint(InterestPoint interestPoint);

    @Update
    void updateInterestPoint(InterestPoint interestPoint);

    @Query("SELECT * FROM InterestPoint WHERE interest_point_id = :interestPointId")
    LiveData<InterestPoint> getInterestPointById(long interestPointId);

    @Query("DELETE FROM InterestPoint WHERE interest_point_id = :interestPointId")
    int deleteInterestPoint(long interestPointId);

    @Query("SELECT * FROM InterestPoint WHERE interest_point_id = :interestPointId")
    Cursor getInterestPointWithCursor(long interestPointId);

}
