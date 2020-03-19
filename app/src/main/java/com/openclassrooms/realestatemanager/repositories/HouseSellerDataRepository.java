package com.openclassrooms.realestatemanager.repositories;

import androidx.lifecycle.LiveData;

import com.openclassrooms.realestatemanager.database.Dao.HouseSellerDAO;
import com.openclassrooms.realestatemanager.models.HouseSeller;

import java.util.List;

public class HouseSellerDataRepository {

    private final HouseSellerDAO houseSellerDAO;

    public HouseSellerDataRepository (HouseSellerDAO houseSellerDAO){this.houseSellerDAO = houseSellerDAO;}

    // --- CREATE ---
    public void createHouSeller(HouseSeller houseSeller){this.houseSellerDAO.createHouseSeller(houseSeller);}

    // --- READ ---
    public LiveData<HouseSeller> getHouseSeller(long houseSellerId){return this.houseSellerDAO.getHouseSellerById(houseSellerId);}
    public LiveData<List<HouseSeller>> getHouseSellersList(){return this.houseSellerDAO.getHouseSellersList();}

    // --- DELETE ---
    public void deleteHouseSeller(long houseSellerId){this.houseSellerDAO.deleteHouseSeller(houseSellerId);}
}
