package com.openclassrooms.realestatemanager.injection;

import android.content.Context;

import com.openclassrooms.realestatemanager.activities.AddHouseSeller.AddHouseSellerViewModelFactory;
import com.openclassrooms.realestatemanager.activities.AddProperties.AddPropertiesViewModelFactory;
import com.openclassrooms.realestatemanager.database.AppDatabase;
import com.openclassrooms.realestatemanager.repositories.HouseSellerDataRepository;
import com.openclassrooms.realestatemanager.repositories.InterestPointDataRepository;
import com.openclassrooms.realestatemanager.repositories.MediaDataRepository;
import com.openclassrooms.realestatemanager.repositories.PropertyDataRepository;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class Injection {

    public static HouseSellerDataRepository provideHouseSellerDataSource(Context context){
        AppDatabase database = AppDatabase.getInstance(context);
        return new HouseSellerDataRepository(database.houseSellerDAO());
    }

    public static InterestPointDataRepository providerInterestPointDataSource(Context context){
        AppDatabase database = AppDatabase.getInstance(context);
        return new InterestPointDataRepository(database.interestPointDAO());
    }

    public static MediaDataRepository providerMediaDataSource(Context context){
        AppDatabase database = AppDatabase.getInstance(context);
        return new MediaDataRepository(database.mediaDAO());
    }

    public static PropertyDataRepository providerPropertyDataSource(Context context){
        AppDatabase database = AppDatabase.getInstance(context);
        return new PropertyDataRepository(database.propertyDAO());
    }

    public static Executor provideExecutor(){
        return Executors.newSingleThreadExecutor();
    }

    public static AddHouseSellerViewModelFactory providerAddHouseSellerViewModelFactory(Context context){
        HouseSellerDataRepository dataSourceHouseSeller = provideHouseSellerDataSource(context);
        Executor executor = provideExecutor();
        return new AddHouseSellerViewModelFactory(dataSourceHouseSeller, executor);
    }

    public static AddPropertiesViewModelFactory providerAddPropertiesViewModelFactory(Context context){
        HouseSellerDataRepository dataSourceHouseSeller = provideHouseSellerDataSource(context);
        InterestPointDataRepository dataSourceInterestPoint = providerInterestPointDataSource(context);
        MediaDataRepository dataSourceMedia = providerMediaDataSource(context);
        PropertyDataRepository dataSourceProperty = providerPropertyDataSource(context);
        Executor executor = provideExecutor();
        return new AddPropertiesViewModelFactory(dataSourceHouseSeller, dataSourceInterestPoint, dataSourceMedia, dataSourceProperty,executor);
    }
}
