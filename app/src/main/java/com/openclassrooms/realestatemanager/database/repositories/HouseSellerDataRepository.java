package com.openclassrooms.realestatemanager.database.repositories;

import androidx.lifecycle.LiveData;

import com.openclassrooms.realestatemanager.database.Dao.HouseSellerDAO;
import com.openclassrooms.realestatemanager.models.database.HouseSeller;

import java.util.List;

public class HouseSellerDataRepository {

    private final HouseSellerDAO houseSellerDAO;

    public HouseSellerDataRepository (HouseSellerDAO houseSellerDAO){this.houseSellerDAO = houseSellerDAO;}

    // --- CREATE ---
    public long createHouSeller(HouseSeller houseSeller){ return this.houseSellerDAO.createHouseSeller(houseSeller);}

    // --- READ ---
    public LiveData<HouseSeller> getHouseSeller(String houseSellerId){return this.houseSellerDAO.getHouseSellerByName(houseSellerId);}
    public LiveData<List<HouseSeller>> getHouseSellersList(){return this.houseSellerDAO.getHouseSellersList();}

    // --- DELETE ---
    public void deleteHouseSeller(long houseSellerId){this.houseSellerDAO.deleteHouseSeller(houseSellerId);}
}
