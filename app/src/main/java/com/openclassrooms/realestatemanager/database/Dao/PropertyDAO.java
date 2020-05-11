package com.openclassrooms.realestatemanager.database.Dao;

import android.database.Cursor;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.RawQuery;
import androidx.room.Update;
import androidx.sqlite.db.SupportSQLiteQuery;

import com.openclassrooms.realestatemanager.models.database.Property;

import java.util.List;

@Dao
public interface PropertyDAO {

    @Query("SELECT * FROM Property WHERE property_id = :propertyId")
    LiveData<Property> getPropertyById(long propertyId);

    @Insert
    long createProperty(Property property);

    @Update
    void updateProperty(Property property);

    @Query("DELETE FROM Property WHERE property_id = :propertyId")
    int deleteProperty(long propertyId);

    @Query("SELECT * FROM Property")
    LiveData<List<Property>> getAllProperties();

    @Query("SELECT * FROM Property WHERE property_id = :propertyId")
    Cursor getPropertyWithCursor(long propertyId);

    @RawQuery(observedEntities = Property.class)
    LiveData<List<Property>> searchProperty(SupportSQLiteQuery query);

}
