package com.openclassrooms.realestatemanager.ui.activities.AddHouseSeller;

import android.content.Context;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.openclassrooms.realestatemanager.R;
import com.openclassrooms.realestatemanager.models.HouseSeller;
import com.openclassrooms.realestatemanager.repositories.HouseSellerDataRepository;
import com.openclassrooms.realestatemanager.utils.Utils;

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

    // --- FOR HOUSE SELLER ---

        // --- create ---
    public void createHouseSeller(HouseSeller houseSeller, Context context){
        executor.execute(()->{
            long id =  houseSellerDataSource.createHouSeller(houseSeller);
            Log.e("HouseSellerId", String.valueOf(id));
            if(id >= 0){
                Utils.startNotification(context.getString(R.string.notif_success_house_seller_title), context.getString(R.string.notif_success_house_seller_content), context);
            }else{
                Utils.startNotification(context.getString(R.string.notif_error_house_seller_title), context.getString(R.string.notif_error_house_seller_content), context);
            }
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
