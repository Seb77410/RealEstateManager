package com.openclassrooms.realestatemanager.utils;

import com.openclassrooms.realestatemanager.models.ApiResponse.GeocodeResponse;

import io.reactivex.Observable;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface GeocodingService {

    //----------------------------------------------------------------------------------------------
    // Most Popular request
    //----------------------------------------------------------------------------------------------
    @GET("json?")
    Observable<GeocodeResponse> getLatLngFromAddress(@Query("address") String address, @Query("key") String apiKey);

    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("https://maps.googleapis.com/maps/api/geocode/")
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build();
}
