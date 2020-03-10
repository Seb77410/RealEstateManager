package com.openclassrooms.realestatemanager.Repositories;

import androidx.lifecycle.LiveData;

import com.openclassrooms.realestatemanager.Database.Dao.HouseSellerDAO;
import com.openclassrooms.realestatemanager.Models.HouseSeller;

public class HouseSellerDataRepository {

    private final HouseSellerDAO houseSellerDAO;

    public HouseSellerDataRepository (HouseSellerDAO houseSellerDAO){this.houseSellerDAO = houseSellerDAO;}

    // --- CREATE ---
    public void createHouSeller(HouseSeller houseSeller){this.houseSellerDAO.createHouseSeller(houseSeller);}

    // --- READ ---
    public LiveData<HouseSeller> getHouseSeller(long houseSellerId){return this.houseSellerDAO.getHouseSellerById(houseSellerId);}

    // --- DELETE ---
    public void deleteHouseSeller(long houseSellerId){this.houseSellerDAO.deleteHouseSeller(houseSellerId);}
}
