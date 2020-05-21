package com.openclassrooms.realestatemanager.ui.activities.AddProperties;

import android.content.Context;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.openclassrooms.realestatemanager.R;
import com.openclassrooms.realestatemanager.models.database.HouseSeller;
import com.openclassrooms.realestatemanager.models.database.InterestPoint;
import com.openclassrooms.realestatemanager.models.database.Media;
import com.openclassrooms.realestatemanager.models.database.Property;
import com.openclassrooms.realestatemanager.database.repositories.HouseSellerDataRepository;
import com.openclassrooms.realestatemanager.database.repositories.InterestPointDataRepository;
import com.openclassrooms.realestatemanager.database.repositories.MediaDataRepository;
import com.openclassrooms.realestatemanager.database.repositories.PropertyDataRepository;
import com.openclassrooms.realestatemanager.utils.Utils;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;

class AddPropertiesViewModel extends ViewModel {

    // --- Repositories ---
    private final HouseSellerDataRepository houseSellerDataSource;
    private final InterestPointDataRepository interestPointDataSource;
    private final MediaDataRepository mediaDataSource;
    private final PropertyDataRepository propertyDataSource;
    private final Executor executor;

    // --- Constructors ---
    AddPropertiesViewModel(HouseSellerDataRepository houseSellerDataSource, InterestPointDataRepository interestPointDataSource,
                           MediaDataRepository mediaDataSource, PropertyDataRepository propertyDataSource, Executor executor){
        this.houseSellerDataSource = houseSellerDataSource;
        this.interestPointDataSource = interestPointDataSource;
        this.mediaDataSource = mediaDataSource;
        this.propertyDataSource = propertyDataSource;
        this.executor = executor;
    }

    // --- For House Seller ---
    LiveData<List<HouseSeller>> getHouseSellersLIst(){
        return houseSellerDataSource.getHouseSellersList();
    }

    // --- For Interests Points ---

    LiveData<InterestPoint> getInterestPointById(long interestPointId) {
        return interestPointDataSource.getInterestPoint(interestPointId);
    }

        // --- For Media ---
    LiveData<List<Media>> getMediaByPropertyId(long propertyId) {
        return mediaDataSource.getMediaByPropertyId(propertyId);
    }



    // --- FOR PROPERTY AND DATA ---
    void createPropertyAndData(InterestPoint interestPoint, Property property, ArrayList<Media> photoList, Context context){
        executor.execute(()->{
            long interestPointId = interestPointDataSource.createInterestPoint(interestPoint);
            Log.i("InterestPointId", String.valueOf(interestPointId));
            property.setInterestPointId(interestPointId);
            executor.execute(()->{
                long propertyId = propertyDataSource.createProperty(property);
                Log.i("PropertyId", String.valueOf(propertyId));
                for(Media media : photoList){
                executor.execute(()->{
                    media.setPropertyId(propertyId);
                    long mediaId = mediaDataSource.createMedia(media);
                    Log.i("MediaId", String.valueOf(mediaId));
                    if(mediaId >= 0){
                        Utils.startNotification(context.getString(R.string.notif_success_property_title), context.getString(R.string.notif_success_property_content), context);
                    }else{
                        Utils.startNotification(context.getString(R.string.notif_error_property_title), context.getString(R.string.notif_error_property_content), context);
                    }
                });
                }
            });
        });
    }


    void updatePropertyAndData(InterestPoint interestPoint, Property property, ArrayList<Media> mediaList, ArrayList<Long> mediaToDelete, Context context) {
        executor.execute(()-> interestPointDataSource.updateInterestPoint(interestPoint));
        executor.execute(()-> propertyDataSource.updateProperty(property));

        for(Media media : mediaList) {
            executor.execute(() -> {
                if (media.getPropertyId() == property.getId()) {mediaDataSource.updateMedia(media);}
                else {
                    media.setPropertyId(property.getId());
                    mediaDataSource.createMedia(media);
                }
            });

            for (Long mediaId : mediaToDelete) {
                executor.execute(()-> mediaDataSource.deleteMedia(mediaId));
            }
        }
        Utils.startNotification(context.getString(R.string.notif_success_property_update_title), context.getString(R.string.notif_success_property_update_content), context);
    }

}
