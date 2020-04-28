package com.openclassrooms.realestatemanager.ui.activities.Map;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.res.ResourcesCompat;
import androidx.lifecycle.ViewModelProvider;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.openclassrooms.realestatemanager.R;
import com.openclassrooms.realestatemanager.database.injection.Injection;
import com.openclassrooms.realestatemanager.models.ApiResponse.GeocodeResponse;
import com.openclassrooms.realestatemanager.models.database.Property;
import com.openclassrooms.realestatemanager.ui.activities.PropertyDetailsActivity;
import com.openclassrooms.realestatemanager.utils.GeocodingService;
import com.openclassrooms.realestatemanager.utils.MyConstants;
import com.openclassrooms.realestatemanager.utils.Utils;

import java.util.ArrayList;
import java.util.Objects;
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
    private FusedLocationProviderClient mFusedLocationProviderClient;
    private MapViewModel viewModel;
    private ArrayList<DisposableObserver> observerList = new ArrayList<>();

    private LocationManager locationManager;
    private static final long MIN_TIME = 400;
    private static final float MIN_DISTANCE = 1000;

    //----------------------------------------------------------------------------------------------
    // On Create
    //----------------------------------------------------------------------------------------------

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        this.configureToolbar();
        this.configureViewModel();



        // Configure view
        try {
            SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                    .findFragmentById(R.id.map_activity_map);
            if (mapFragment != null) {
                mapFragment.getMapAsync(MapActivity.this);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //----------------------------------------------------------------------------------------------
    // Toolbar
    //----------------------------------------------------------------------------------------------

    private void configureToolbar(){
        Toolbar mToolbar = findViewById(R.id.map_activity_toolbar);
        setSupportActionBar(mToolbar);
        // Set back stack
        Drawable upArrow = ResourcesCompat.getDrawable(this.getResources(), R.drawable.ic_arrow_back_white_24dp, null);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setHomeAsUpIndicator(upArrow);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    //----------------------------------------------------------------------------------------------
    // On Map Ready
    //----------------------------------------------------------------------------------------------

    @Override
    public void onMapReady(GoogleMap googleMap) {

        mMap = googleMap;
        mMap.setMyLocationEnabled(true);
        mMap.getUiSettings().setZoomControlsEnabled(true);
        mMap.setOnMarkerClickListener(this);
        this.checkForPermissionLocation();
    }

    //----------------------------------------------------------------------------------------------
    // Get User location
    // ---------------------------------------------------------------------------------------------

    /**
     * This method get user Location, add a marker on his location and search restaurants nearby
     */
    private void updateUserLastLocation() {
        mFusedLocationProviderClient.getLastLocation()
                .addOnSuccessListener(location -> {
                    // Got last known location. In some rare situations this can be null.
                    if (location != null) {
                        Log.d("User Location ",  location.getLatitude() +" , " + location.getLongitude());
                        LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
                        // Move camera to current position
                        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 12));

                    }
                });
    }

    private void updateUserLocation(Location location){
        LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
        // Move camera to current position
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 12));

    }
    //----------------------------------------------------------------------------------------------
    //  Marker Click
    //----------------------------------------------------------------------------------------------

    @Override
    public boolean onMarkerClick(Marker marker) {

        if (marker.getTag() != null) {
            // Set marker place Id into string value
            String markerPlaceId = (String) marker.getTag();
            //Start RestaurantDetailsActivity activity with restaurant details as arguments
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
        else{

            mFusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(getApplicationContext());
            this.updateUserLastLocation();
            // Add location listener
            locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
            Objects.requireNonNull(locationManager).requestLocationUpdates(LocationManager.NETWORK_PROVIDER, MIN_TIME, MIN_DISTANCE, new android.location.LocationListener() {
                @Override
                public void onLocationChanged(Location location) {
                    updateUserLocation(location);
                }

                @Override
                public void onStatusChanged(String provider, int status, Bundle extras) {

                }

                @Override
                public void onProviderEnabled(String provider) {

                }

                @Override
                public void onProviderDisabled(String provider) {

                }
            }); //You can also use LocationManager.GPS_PROVIDER and LocationManager.PASSIVE_PROVIDER
            getProperties();

        }
    }



    /**
     * This method define app comportment when user allow app to get user location
     * @param requestCode is a request code
     * @param permissions is the current permission asked to user
     * @param grantResults is user response to this ask
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == MyConstants.LOCATION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                if (ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                    this.updateUserLastLocation();
                    // TODO : ajouter marker pour chaque Property
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
                String propertyAddress = property.getAddress().replaceAll("\\s", "+");
                DisposableObserver observer = streamFetchLatLang(propertyAddress, getResources().getString(R.string.map_api_key)).subscribeWith(new DisposableObserver<GeocodeResponse>() {

                    @Override
                    public void onNext(GeocodeResponse geocodeResponse) {
                        if (geocodeResponse.getStatus().equals("OK")) {
                            MarkerOptions markerOptions = new MarkerOptions();
                            LatLng latLng = new LatLng(geocodeResponse.getResults().get(0).getGeometry().getLocation().getLat(), geocodeResponse.getResults().get(0).getGeometry().getLocation().getLng());
                            markerOptions.position(latLng);
                            markerOptions.title(property.getType());
                            Marker mMarker = mMap.addMarker(markerOptions);
                            mMarker.setTag(Utils.convertPropertyToString(property));
                        }
                        else{
                            Log.e("MapActivity", "Geocoding for property " + property.getId() + "failed");
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                    }

                    @Override
                    public void onComplete() {

                    }
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
