package com.openclassrooms.realestatemanager.Database.Dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.openclassrooms.realestatemanager.Models.InterestPoint;
import com.openclassrooms.realestatemanager.Models.Media;

@Dao
public interface MediaDAO {

    @Insert
    long createMedia(Media media);

    @Query("SELECT * FROM Media WHERE id = :mediaId")
    LiveData<Media> getMediaById(long mediaId);

    @Query("DELETE FROM Media WHERE id = :mediaId")
    int deleteMedia(long mediaId);
}
