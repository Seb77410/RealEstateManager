package com.openclassrooms.realestatemanager.ui.activities.AddHouseSeller;

import android.content.Context;
import android.util.Log;

import androidx.lifecycle.ViewModel;

import com.openclassrooms.realestatemanager.R;
import com.openclassrooms.realestatemanager.models.database.HouseSeller;
import com.openclassrooms.realestatemanager.database.repositories.HouseSellerDataRepository;
import com.openclassrooms.realestatemanager.utils.Utils;

import java.util.concurrent.Executor;

class AddHouseSellerViewModel extends ViewModel {

    // --- REPOSITORY ---
    private final HouseSellerDataRepository houseSellerDataSource;
    private final Executor executor;


    // --- CONSTRUCTOR ---
    AddHouseSellerViewModel(HouseSellerDataRepository houseSellerDataSource, Executor executor){
        this.houseSellerDataSource = houseSellerDataSource;
        this.executor = executor;
    }

    // --- FOR HOUSE SELLER ---

        // --- create ---
        void createHouseSeller(HouseSeller houseSeller, Context context){
        executor.execute(()->{
            long id =  houseSellerDataSource.createHouSeller(houseSeller);
            Log.i("HouseSellerId", String.valueOf(id));
            if(id >= 0){
                Utils.startNotification(context.getString(R.string.notif_success_house_seller_title), context.getString(R.string.notif_success_house_seller_content), context);
            }else{
                Utils.startNotification(context.getString(R.string.notif_error_house_seller_title), context.getString(R.string.notif_error_house_seller_content), context);
            }
        });
    }

}
