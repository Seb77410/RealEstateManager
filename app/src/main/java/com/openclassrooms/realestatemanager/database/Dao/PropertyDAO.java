package com.openclassrooms.realestatemanager.database.Dao;

import android.database.Cursor;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.openclassrooms.realestatemanager.models.database.Property;

import java.util.List;

@Dao
public interface PropertyDAO {

    @Query("SELECT * FROM Property WHERE houseSellerId = :houseSellerId")
    LiveData<List<Property>> getPropertiesByHouseSellerId(long houseSellerId);

    @Query("SELECT * FROM Property WHERE id = :propertyId")
    LiveData<Property> getPropertyById(long propertyId);

    @Insert
    long createProperty(Property property);

    @Update
    int updateProperty(Property property);

    @Query("DELETE FROM Property WHERE id = :propertyId")
    int deleteProperty(long propertyId);

    @Query("SELECT * FROM Property ORDER BY id DESC LIMIT 1")
    LiveData<Property> getLastPropertySaved();

    @Query("SELECT * FROM Property")
    LiveData<List<Property>> getAllProperties();

    @Query("SELECT * FROM Property WHERE id = :propertyId")
    Cursor getPropertyWithCursor(long propertyId);
}
