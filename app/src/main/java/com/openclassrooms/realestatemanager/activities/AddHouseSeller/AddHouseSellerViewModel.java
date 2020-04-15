package com.openclassrooms.realestatemanager.activities.AddHouseSeller;

import android.util.Log;

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


    // --- CONSTRUCTOR ---
    public AddHouseSellerViewModel (HouseSellerDataRepository houseSellerDataSource, Executor executor){
        this.houseSellerDataSource = houseSellerDataSource;
        this.executor = executor;
    }

    // --- DATA ---
    private String userName;
    private String userEmail;


    // --- FOR HOUSE SELLER ---

        // --- create ---
    public void createHouseSeller(HouseSeller houseSeller){
        executor.execute(()->{
            long id =  houseSellerDataSource.createHouSeller(houseSeller);
            Log.e("HouseSellerId", String.valueOf(id));
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
