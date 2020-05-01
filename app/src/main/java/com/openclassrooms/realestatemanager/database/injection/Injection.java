package com.openclassrooms.realestatemanager.database.injection;

import android.content.Context;

import com.openclassrooms.realestatemanager.ui.activities.AddHouseSeller.AddHouseSellerViewModelFactory;
import com.openclassrooms.realestatemanager.ui.activities.AddProperties.AddPropertiesViewModelFactory;
import com.openclassrooms.realestatemanager.database.AppDatabase;
import com.openclassrooms.realestatemanager.database.repositories.HouseSellerDataRepository;
import com.openclassrooms.realestatemanager.database.repositories.InterestPointDataRepository;
import com.openclassrooms.realestatemanager.database.repositories.MediaDataRepository;
import com.openclassrooms.realestatemanager.database.repositories.PropertyDataRepository;
import com.openclassrooms.realestatemanager.ui.activities.Map.MapViewModelFactory;
import com.openclassrooms.realestatemanager.ui.fragments.propertiesLisFragment.PropertiesListViewModelFactory;
import com.openclassrooms.realestatemanager.ui.fragments.propertyDetailsFragment.PropertyDetailsViewModelFactory;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class Injection {

    private static HouseSellerDataRepository provideHouseSellerDataSource(Context context){
        AppDatabase database = AppDatabase.getInstance(context);
        return new HouseSellerDataRepository(database.houseSellerDAO());
    }

    private static InterestPointDataRepository providerInterestPointDataSource(Context context){
        AppDatabase database = AppDatabase.getInstance(context);
        return new InterestPointDataRepository(database.interestPointDAO());
    }

    private static MediaDataRepository providerMediaDataSource(Context context){
        AppDatabase database = AppDatabase.getInstance(context);
        return new MediaDataRepository(database.mediaDAO());
    }

    private static PropertyDataRepository providerPropertyDataSource(Context context){
        AppDatabase database = AppDatabase.getInstance(context);
        return new PropertyDataRepository(database.propertyDAO());
    }

    private static Executor provideExecutor(){
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


    public static PropertiesListViewModelFactory providerPropertiesListViewModelFactory(Context context) {
        PropertyDataRepository dataSourceProperty = providerPropertyDataSource(context);
        MediaDataRepository dataSourceMedia = providerMediaDataSource(context);
        return new PropertiesListViewModelFactory(dataSourceProperty, dataSourceMedia );
    }

    public static PropertyDetailsViewModelFactory providerPropertyDetailsViewModelFactory(Context context) {
        InterestPointDataRepository dataSourceInterestPoint = providerInterestPointDataSource(context);
        MediaDataRepository dataSourceMedia = providerMediaDataSource(context);
        return new PropertyDetailsViewModelFactory(dataSourceInterestPoint, dataSourceMedia);
    }

    public static MapViewModelFactory providerMapViewModelFactory(Context context){
        PropertyDataRepository dataSourceProperty = providerPropertyDataSource(context);
        return new MapViewModelFactory(dataSourceProperty);
    }
}
