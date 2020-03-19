package com.openclassrooms.realestatemanager.database.Dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.openclassrooms.realestatemanager.models.HouseSeller;

import java.util.List;

@Dao
public interface HouseSellerDAO {

    @Insert
    long createHouseSeller(HouseSeller houseSeller);

    @Query("SELECT * FROM HouseSeller WHERE name = :houseSellerName")
    LiveData<HouseSeller> getHouseSellerByName(String houseSellerName);

    @Query("SELECT * FROM HouseSeller")
    LiveData<List<HouseSeller>> getHouseSellersList();

    @Query("DELETE FROM HouseSeller WHERE id = :houseSellerId")
    int deleteHouseSeller(long houseSellerId);
}