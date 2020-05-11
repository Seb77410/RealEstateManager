package com.openclassrooms.realestatemanager;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;

import androidx.room.Room;
import androidx.test.InstrumentationRegistry;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.openclassrooms.realestatemanager.database.AppDatabase;
import com.openclassrooms.realestatemanager.utils.Converters;
import com.openclassrooms.realestatemanager.utils.MyConstants;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.Calendar;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.core.IsNull.notNullValue;

 @RunWith(AndroidJUnit4.class)
public class ContentProviderTest {

     // FOR DATA
     private ContentResolver mContentResolver;

     // DATA SET FOR TEST
     private static long PROPERTY_ID = 51;
     private static long HOUSE_SELLER_ID = 51;
     private static final long INTEREST_POINT_ID = 51;

     @Before
     public void setUp() {
         Room.inMemoryDatabaseBuilder(InstrumentationRegistry.getContext(),
                 AppDatabase.class)
                 .allowMainThreadQueries()
                 .build();
         mContentResolver = InstrumentationRegistry.getContext().getContentResolver();
     }

     @Test
     public void verifyDataDoesNotExist() {
         // Get house seller when house seller doesn't exist
         checkForItemDoesNotExist(MyConstants.URI_HOUSE_SELLER, HOUSE_SELLER_ID);
         // Get interest point when interest point doesn't exist
         checkForItemDoesNotExist(MyConstants.URI_INTEREST_POINT, INTEREST_POINT_ID);
         // Get property when property doesn't exist
         checkForItemDoesNotExist(MyConstants.URI_PROPERTY, PROPERTY_ID);
     }

     @Test
     public void InsertAndDeleteDataTest() {
         // Insert house seller
         mContentResolver.insert(MyConstants.URI_HOUSE_SELLER, generateHouseSeller());
         Cursor cursor1 = checkItemCorrectlyInsert(MyConstants.URI_HOUSE_SELLER, HOUSE_SELLER_ID);
         Assert.assertThat(cursor1.getString(cursor1.getColumnIndexOrThrow("name")), is("kalakala"));
         cursor1.close();
         // Insert interest point
         mContentResolver.insert(MyConstants.URI_INTEREST_POINT, generateInterestPoint());
         Cursor cursor2 = checkItemCorrectlyInsert(MyConstants.URI_INTEREST_POINT, INTEREST_POINT_ID);
         Assert.assertThat(cursor2.getString(cursor2.getColumnIndexOrThrow("category")), is(Converters.arrayToString(new ArrayList<>())));
         cursor2.close();
         // insert property
         mContentResolver.insert(MyConstants.URI_PROPERTY, generateProperty());
         Cursor cursor3 = checkItemCorrectlyInsert(MyConstants.URI_PROPERTY, PROPERTY_ID);
         Assert.assertThat(cursor3.getString(cursor3.getColumnIndexOrThrow("address")), is("10 rue jean jaures, 77410 CLAYE SOUILLY"));
         cursor3.close();

         // Delete property
         int rows = mContentResolver.delete(ContentUris.withAppendedId(MyConstants.URI_PROPERTY, PROPERTY_ID), null, null);
         Assert.assertEquals(rows, 1);
         checkForItemDoesNotExist(MyConstants.URI_PROPERTY, PROPERTY_ID);
         // Delete house seller
         int rows2 = mContentResolver.delete(ContentUris.withAppendedId(MyConstants.URI_HOUSE_SELLER, HOUSE_SELLER_ID), null, null);
         Assert.assertEquals(rows2, 1);
         checkForItemDoesNotExist(MyConstants.URI_HOUSE_SELLER, HOUSE_SELLER_ID);
         // Delete interest point
         int rows3 = mContentResolver.delete(ContentUris.withAppendedId(MyConstants.URI_INTEREST_POINT, INTEREST_POINT_ID), null, null);
         Assert.assertEquals(rows3, 1);
         checkForItemDoesNotExist(MyConstants.URI_INTEREST_POINT, INTEREST_POINT_ID);
     }

     private void checkForItemDoesNotExist(Uri uri, long id){
         final Cursor cursor = mContentResolver.query(ContentUris.withAppendedId(uri, id), null, null, null, null);
         Assert.assertThat(cursor, notNullValue());
         Assert.assertThat(cursor.getCount(), is(0));
         cursor.close();
     }

     private Cursor checkItemCorrectlyInsert(Uri uri, long id){
         final Cursor cursor = mContentResolver.query(ContentUris.withAppendedId(uri, id), null, null, null, null);
         Assert.assertThat(cursor, notNullValue());
         Assert.assertThat(cursor.getCount(), is(1));
         Assert.assertThat(cursor.moveToFirst(), is(true));
         return cursor;
     }

     private ContentValues generateHouseSeller() {
         final ContentValues values = new ContentValues();
         values.put("id", HOUSE_SELLER_ID);
         values.put("name", "kalakala");
         values.put("email", "kalakala@gmail.com");
         return values;
     }

     private ContentValues generateInterestPoint() {
         final ContentValues values = new ContentValues();
         values.put("id", INTEREST_POINT_ID);
         values.put("category", Converters.arrayToString(new ArrayList<>()));
         return values;
     }

     private ContentValues generateProperty() {
         final ContentValues values = new ContentValues();
         values.put("id", PROPERTY_ID);
         values.put("price", "200000");
         values.put("area", "75");
         values.put("rooms", "3");
         values.put("type", "Home");
         values.put("description", "Petite maison avec 2 chambre, un petit jardin");
         values.put("address", "10 rue jean jaures, 77410 CLAYE SOUILLY");
         values.put("sold", "false");
         values.put("createDate", Converters.calendarToString(Calendar.getInstance()));
         values.put("interestPointId", INTEREST_POINT_ID);
         values.put("houseSellerId", HOUSE_SELLER_ID);
         values.put("type", "Home");
         return values;
     }
 }

