package com.openclassrooms.realestatemanager.provider;

import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.SQLException;
import android.net.Uri;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.openclassrooms.realestatemanager.database.AppDatabase;
import com.openclassrooms.realestatemanager.models.database.HouseSeller;
import com.openclassrooms.realestatemanager.models.database.InterestPoint;
import com.openclassrooms.realestatemanager.models.database.Property;

import java.util.Objects;

public class ContentProvider extends android.content.ContentProvider {

    // FOR DATA
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


    private static final UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
    static {
        uriMatcher.addURI(AUTHORITY, HOUSE_SELLER_TABLE_NAME, HOUSE_SELLER_TABLE_MATCHER_CODE);
        uriMatcher.addURI(AUTHORITY, HOUSE_SELLER_TABLE_NAME+"/#", HOUSE_SELLER_ITEM_MATCHER_CODE);
        uriMatcher.addURI(AUTHORITY, PROPERTY_TABLE_NAME, PROPERTY_TABLE_MATCHER_CODE);
        uriMatcher.addURI(AUTHORITY, PROPERTY_TABLE_NAME+"/#", PROPERTY_ITEM_MATCHER_CODE);
        uriMatcher.addURI(AUTHORITY, INTEREST_POINT_TABLE_NAME, INTEREST_POINT_TABLE_MATCHER_CODE);
        uriMatcher.addURI(AUTHORITY, INTEREST_POINT_TABLE_NAME+"/#", INTEREST_POINT_ITEM_MATCHER_CODE);
    }

    @Override
    public boolean onCreate() {
        return false;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {

        Cursor cursor;
        switch (uriMatcher.match(uri)) {

            case HOUSE_SELLER_ITEM_MATCHER_CODE:
                long houseSellerId = ContentUris.parseId(uri);
                cursor = AppDatabase.getInstance(getContext()).houseSellerDAO().getHouseSellerWithCursor(houseSellerId);
                break;
            case PROPERTY_ITEM_MATCHER_CODE:
                long propertyId = ContentUris.parseId(uri);
                cursor = AppDatabase.getInstance(getContext()).propertyDAO().getPropertyWithCursor(propertyId);
                break;
            case INTEREST_POINT_ITEM_MATCHER_CODE:
                long interestPointId = ContentUris.parseId(uri);
                cursor = AppDatabase.getInstance(getContext()).interestPointDAO().getInterestPointWithCursor(interestPointId);
                break;
            default: throw new SQLException("Failed to query " + uri);
        }
        return cursor;
    }


    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        String type = null;
        switch (uriMatcher.match(uri)) {
            case HOUSE_SELLER_TABLE_MATCHER_CODE:
                type = "vnd.android.cursor.dir/" + AUTHORITY + "." + HOUSE_SELLER_TABLE_NAME;
                break;
            case HOUSE_SELLER_ITEM_MATCHER_CODE:
                type = "vnd.android.cursor.item/" + AUTHORITY + "." + HOUSE_SELLER_TABLE_NAME;
                break;
            case INTEREST_POINT_TABLE_MATCHER_CODE:
                type = "vnd.android.cursor.dir/" + AUTHORITY + "." + INTEREST_POINT_TABLE_NAME;
                break;
            case INTEREST_POINT_ITEM_MATCHER_CODE:
                type = "vnd.android.cursor.item/" + AUTHORITY + "." + INTEREST_POINT_TABLE_NAME;
                break;
            case PROPERTY_TABLE_MATCHER_CODE:
                type = "vnd.android.cursor.dir/" + AUTHORITY + "." + PROPERTY_TABLE_NAME;
                break;
            case PROPERTY_ITEM_MATCHER_CODE:
                type = "vnd.android.cursor.item/" + AUTHORITY + "." + PROPERTY_TABLE_NAME;
                break;
        }
            return type;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {

        Uri mUri = null;
        switch (uriMatcher.match(uri)) {
            case HOUSE_SELLER_TABLE_MATCHER_CODE:
                final long houseSellerId = AppDatabase.getInstance(getContext()).houseSellerDAO().createHouseSeller(ProviderContentValues.fromHouseSellerContentValues(Objects.requireNonNull(values)));
                if (houseSellerId != 0) {
                    mUri = ContentUris.withAppendedId(URI_HOUSE_SELLER, houseSellerId);
                    Objects.requireNonNull(getContext()).getContentResolver().notifyChange(mUri, null); }
                break;
            case PROPERTY_TABLE_MATCHER_CODE:
                final long propertyId = AppDatabase.getInstance(getContext()).propertyDAO().createProperty(ProviderContentValues.fromPropertyContentValues(Objects.requireNonNull(values)));
                if (propertyId != 0){
                    mUri =  ContentUris.withAppendedId(URI_PROPERTY, propertyId);
                    Objects.requireNonNull(getContext()).getContentResolver().notifyChange(mUri, null); }
                break;
            case INTEREST_POINT_TABLE_MATCHER_CODE:
                final long interestPointId = AppDatabase.getInstance(getContext()).interestPointDAO().createInterestPoint(ProviderContentValues.fromInterestPointContentValues(Objects.requireNonNull(values)));
                if (interestPointId != 0){
                    mUri =  ContentUris.withAppendedId(URI_INTEREST_POINT, interestPointId);
                    Objects.requireNonNull(getContext()).getContentResolver().notifyChange(mUri, null); }
                break;

        default: throw new SQLException("Failed to insert row into " + uri);
        }
        return mUri;
    }


    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {

        int mInt;
        switch (uriMatcher.match(uri)) {
            case HOUSE_SELLER_ITEM_MATCHER_CODE:
                mInt = AppDatabase.getInstance(getContext()).houseSellerDAO().deleteHouseSeller(ContentUris.parseId(uri));
                break;
            case PROPERTY_ITEM_MATCHER_CODE:
                mInt = AppDatabase.getInstance(getContext()).propertyDAO().deleteProperty(ContentUris.parseId(uri));
                break;
            case INTEREST_POINT_ITEM_MATCHER_CODE:
                mInt = AppDatabase.getInstance(getContext()).interestPointDAO().deleteInterestPoint(ContentUris.parseId(uri));
                break;
        default: throw new SQLException("Failed to delete " + uri);
        }
        return mInt;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
        throw new SQLException("Updates are not allowed");
    }
}
