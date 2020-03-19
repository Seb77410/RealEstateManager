package com.openclassrooms.realestatemanager.database.Dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.openclassrooms.realestatemanager.models.Media;

@Dao
public interface MediaDAO {

    @Insert
    long createMedia(Media media);

    @Query("SELECT * FROM Media WHERE id = :mediaId")
    LiveData<Media> getMediaById(long mediaId);

    @Query("SELECT * FROM Media WHERE propertyId = :propertyId")
    LiveData<Media> getMediaByProperty(String propertyId);

    @Query("DELETE FROM Media WHERE id = :mediaId")
    int deleteMedia(long mediaId);
}
