package com.openclassrooms.realestatemanager.database.Dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.openclassrooms.realestatemanager.models.database.Media;

import java.util.List;

@Dao
public interface MediaDAO {

    @Insert
    long createMedia(Media media);

    @Query("DELETE FROM Media WHERE media_id = :mediaId")
    void deleteMedia(long mediaId);

    @Query("SELECT * FROM Media WHERE media_property_id = :propertyId")
    LiveData<List<Media>> getMediaByPropertyId(long propertyId);

    @Update
    void updateMedia(Media media);
}
