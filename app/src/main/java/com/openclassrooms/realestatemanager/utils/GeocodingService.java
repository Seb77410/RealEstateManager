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
    @GET(MyConstants.GEOCODE_JSON_FORMAT)
    Observable<GeocodeResponse> getLatLngFromAddress(@Query(MyConstants.GEOCODE_ADDRESS_QUERY) String address, @Query(MyConstants.GEOCODE_KEY_QUERY) String apiKey);

    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(MyConstants.GEOCODE_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build();
}
