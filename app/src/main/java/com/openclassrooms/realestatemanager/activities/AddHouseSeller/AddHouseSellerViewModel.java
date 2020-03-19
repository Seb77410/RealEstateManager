package com.openclassrooms.realestatemanager.activities.AddHouseSeller;

import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.openclassrooms.realestatemanager.models.HouseSeller;
import com.openclassrooms.realestatemanager.repositories.HouseSellerDataRepository;

import java.util.List;
import java.util.concurrent.Executor;

public class AddHouseSellerViewModel extends ViewModel {

    // --- REPOSITORY ---
    private final HouseSellerDataRepository houseSellerDataSource;
    private final Executor executor;

    // --- DATA ---
    @Nullable
    private HouseSeller currentHouseSeller;

    // --- CONSTRUCTOR ---
    public AddHouseSellerViewModel (HouseSellerDataRepository houseSellerDataSource, Executor executor){
        this.houseSellerDataSource = houseSellerDataSource;
        this.executor = executor;
    }

    // --- FOR HOUSE SELLER ---

        // --- create ---
    public void createHouseSeller(HouseSeller houseSeller){
        executor.execute(()->{
            houseSellerDataSource.createHouSeller(houseSeller);
        });
    }
        // --- read ---
    public LiveData<HouseSeller> getHouseSeller(String houseSellerId) {
        return houseSellerDataSource.getHouseSeller(houseSellerId);
    }

    public LiveData<List<HouseSeller>> getHouseSellersLIst(){
        return houseSellerDataSource.getHouseSellersList();
    }

}
