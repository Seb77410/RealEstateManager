package com.openclassrooms.realestatemanager.utils;

import android.net.Uri;

import com.openclassrooms.realestatemanager.models.database.HouseSeller;
import com.openclassrooms.realestatemanager.models.database.InterestPoint;
import com.openclassrooms.realestatemanager.models.database.Property;

public class MyConstants {
    //----------------------------------------------------------------------------------------------
    //  Notifications channel
    //----------------------------------------------------------------------------------------------
    static final String CHANNEL_1_ID = "channel1";
    static final String CHANNEL_1_NAME= "Channel 1";

    //----------------------------------------------------------------------------------------------
    //  Content provider
    //----------------------------------------------------------------------------------------------
    public static final String AUTHORITY = "com.openclassrooms.realestatemanager.provider";

    public static final String PROPERTY_TABLE_NAME = Property.class.getSimpleName();
    public static final Uri URI_PROPERTY = Uri.parse("content://" + AUTHORITY + "/" + PROPERTY_TABLE_NAME);
    public static final int PROPERTY_TABLE_MATCHER_CODE = 0;
    public static final int PROPERTY_ITEM_MATCHER_CODE = 1;

    public static final String HOUSE_SELLER_TABLE_NAME = HouseSeller.class.getSimpleName();
    public static final Uri URI_HOUSE_SELLER = Uri.parse("content://" + AUTHORITY + "/" + HOUSE_SELLER_TABLE_NAME);
    public static final int HOUSE_SELLER_TABLE_MATCHER_CODE = 2;
    public static final int  HOUSE_SELLER_ITEM_MATCHER_CODE = 3;

    public static final String INTEREST_POINT_TABLE_NAME = InterestPoint.class.getSimpleName();
    public static final Uri URI_INTEREST_POINT = Uri.parse("content://" + AUTHORITY + "/" + INTEREST_POINT_TABLE_NAME);
    public static final int INTEREST_POINT_TABLE_MATCHER_CODE = 4;
    public static final int INTEREST_POINT_ITEM_MATCHER_CODE = 5;

    //----------------------------------------------------------------------------------------------
    //  Bottom sheet
    //----------------------------------------------------------------------------------------------
    public static final int GALLERY_PICK = 1;
    public static final int MY_CAMERA_PERMISSION_CODE = 2;
    public static final int REQUEST_TAKE_PHOTO = 3;

    //----------------------------------------------------------------------------------------------
    //  Arguments / Params key values
    //----------------------------------------------------------------------------------------------
    // Edit activity
    public final static String EDIT_ACTIVITY_PARAM = "property for details";
    // Property details activity
    public final static String PROPERTY_DETAILS_ACTIVITY_PARAM = "property";
    // Search result activity
    public final static String SEARCH_RESULT_ACTIVITY_PARAM = "properties list";

    //----------------------------------------------------------------------------------------------
    //  API
    //----------------------------------------------------------------------------------------------
    // Map
    public static final int LOCATION_REQUEST_CODE = 1;
    // Static map
    public final static String STATIC_MAP_BASE_URL = "https://maps.googleapis.com/maps/api/staticmap?center=";
    public final static String STATIC_MAP_PARAMS_URL_1 = "&zoom=13&scale=1&size=600x300&maptype=roadmap&key=";
    public final static String STATIC_MAP_PARAMS_URL_2 = "&format=jpeg&visual_refresh=true&markers=size:mid%7Ccolor:0xff0000%7Clabel:1%7C";
    // Geocode
    final static String GEOCODE_BASE_URL = "https://maps.googleapis.com/maps/api/geocode/";
    final static String GEOCODE_JSON_FORMAT = "json?";
    final static String GEOCODE_ADDRESS_QUERY = "address";
    final static String GEOCODE_KEY_QUERY = "key";









}
