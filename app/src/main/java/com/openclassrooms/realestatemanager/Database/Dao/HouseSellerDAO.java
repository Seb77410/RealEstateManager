package com.openclassrooms.realestatemanager.Database.Dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.openclassrooms.realestatemanager.Models.HouseSeller;
import com.openclassrooms.realestatemanager.Models.Property;

@Dao
public interface HouseSellerDAO {

    @Insert
    long createHouseSeller(HouseSeller houseSeller);

    @Query("SELECT * FROM HouseSeller WHERE id = :houseSellerId")
    LiveData<HouseSeller> getHouseSellerById(long houseSellerId);

    @Query("DELETE FROM HouseSeller WHERE id = :houseSellerId")
    int deleteHouseSeller(long houseSellerId);
}
