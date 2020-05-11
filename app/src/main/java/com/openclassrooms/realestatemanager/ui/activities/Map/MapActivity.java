package com.openclassrooms.realestatemanager.ui.activities.Map;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.lifecycle.ViewModelProvider;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.openclassrooms.realestatemanager.BuildConfig;
import com.openclassrooms.realestatemanager.R;
import com.openclassrooms.realestatemanager.database.injection.Injection;
import com.openclassrooms.realestatemanager.models.ApiResponse.GeocodeResponse;
import com.openclassrooms.realestatemanager.models.database.Property;
import com.openclassrooms.realestatemanager.ui.activities.PropertyDetails.PropertyDetailsActivity;
import com.openclassrooms.realestatemanager.utils.Converters;
import com.openclassrooms.realestatemanager.utils.GeocodingService;
import com.openclassrooms.realestatemanager.utils.MyConstants;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;


public class MapActivity extends AppCompatActivity implements OnMapReadyCallback, GoogleMap.OnMarkerClickListener
{

    //----------------------------------------------------------------------------------------------
    // For Data
    //----------------------------------------------------------------------------------------------
    private GoogleMap mMap;
    private MapViewModel viewModel;
    private ArrayList<DisposableObserver> observerList = new ArrayList<>();

    //----------------------------------------------------------------------------------------------
    // On Create
    //----------------------------------------------------------------------------------------------
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        this.configureToolbar();
        this.configureViewModel();
        this.configureMap();
    }

    //----------------------------------------------------------------------------------------------
    // Toolbar
    //----------------------------------------------------------------------------------------------
    private void configureToolbar(){
        Toolbar mToolbar = findViewById(R.id.map_activity_toolbar);
        setSupportActionBar(mToolbar);
        // Set back stack
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setHomeAsUpIndicator(R.drawable.ic_arrow_back_white_24dp);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    //----------------------------------------------------------------------------------------------
    // Configure Map
    //----------------------------------------------------------------------------------------------
    private void configureMap(){
        try {
            SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map_activity_map);
            if (mapFragment != null) {mapFragment.getMapAsync(MapActivity.this);}
        }
        catch (Exception e) {e.printStackTrace();}
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        checkForPermissionLocation();
    }

    //----------------------------------------------------------------------------------------------
    // Get User location
    // ---------------------------------------------------------------------------------------------
    private void updateUserLastLocation() {
        FusedLocationProviderClient mFusedLocationProviderClient = new FusedLocationProviderClient(this);
        mFusedLocationProviderClient.getLastLocation().addOnSuccessListener(location -> {
                    if (location != null) {
                        Log.i("MapActivity", "User Location = " + location.getLatitude() +" , " + location.getLongitude());
                        LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
                        mMap.getUiSettings().setZoomControlsEnabled(true);
                        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 12));
                    }
                });
    }

    //----------------------------------------------------------------------------------------------
    //  Marker Click
    //----------------------------------------------------------------------------------------------

    @Override
    public boolean onMarkerClick(Marker marker) {
        if (marker.getTag() != null) {
            String markerPlaceId = (String) marker.getTag();
            Intent intent = new Intent(this, PropertyDetailsActivity.class);
            intent.putExtra("property" ,markerPlaceId);
            startActivity(intent);
        }
        return false;
    }


    //----------------------------------------------------------------------------------------------
    // Permission Location
    //----------------------------------------------------------------------------------------------
    private void checkForPermissionLocation(){
        // Device location not allowed
        if (ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // Ask for location permission
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION,Manifest.permission.ACCESS_FINE_LOCATION}, MyConstants.LOCATION_REQUEST_CODE);
        }
        // Device location allowed
        else{
            mMap.setMyLocationEnabled(true);
            mMap.setOnMarkerClickListener(this);
            this.updateUserLastLocation();
            this.getProperties();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == MyConstants.LOCATION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                if (ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                    mMap.setMyLocationEnabled(true);
                    mMap.setOnMarkerClickListener(this);
                    this.updateUserLastLocation();
                    this.getProperties();
                }
            }
        }
    }


    //----------------------------------------------------------------------------------------------
    // Observable for geocode api request
    //----------------------------------------------------------------------------------------------
    public static Observable<GeocodeResponse> streamFetchLatLang(String address, String apiKey){
        GeocodingService newYorkTimesService = GeocodingService.retrofit.create(GeocodingService.class);
        return newYorkTimesService.getLatLngFromAddress(address, apiKey)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .timeout(10, TimeUnit.SECONDS);
    }


    //----------------------------------------------------------------------------------------------
    // ViewModel and data
    //----------------------------------------------------------------------------------------------
    private void configureViewModel() {
        MapViewModelFactory mapViewModelFactory = Injection.providerMapViewModelFactory(getApplicationContext());
        this.viewModel = new ViewModelProvider(this, mapViewModelFactory).get(MapViewModel.class);
    }

    private void getProperties(){
        viewModel.getProperties().observe(this, properties -> {
            for(Property property : properties){
                // Get Lat Lng from address
                String propertyAddress = property.getAddress().replaceAll("\\s", "+");
                DisposableObserver observer = streamFetchLatLang(propertyAddress, BuildConfig.GEOCODING_API_KEY).subscribeWith(new DisposableObserver<GeocodeResponse>() {
                    @Override
                    public void onNext(GeocodeResponse geocodeResponse) {
                        // Add marker to property Lat Lng
                        if (geocodeResponse.getStatus().equals("OK")) {
                            MarkerOptions markerOptions = new MarkerOptions();
                            LatLng latLng = new LatLng(geocodeResponse.getResults().get(0).getGeometry().getLocation().getLat(), geocodeResponse.getResults().get(0).getGeometry().getLocation().getLng());
                            markerOptions.position(latLng);
                            markerOptions.title(property.getType());
                            Marker mMarker = mMap.addMarker(markerOptions);
                            mMarker.setTag(Converters.convertPropertyToString(property));
                        }
                        else{Log.i("MapActivity", "Geocoding for property " + property.getId() + "failed");}
                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                    }
                    @Override
                    public void onComplete() {}
                });
                observerList.add(observer);
            }
        });
    }

    @Override
    protected void onDestroy() {
        for (DisposableObserver observer : observerList){
            if (observer != null && !observer.isDisposed()) observer.dispose();
        }
        super.onDestroy();
    }
}
