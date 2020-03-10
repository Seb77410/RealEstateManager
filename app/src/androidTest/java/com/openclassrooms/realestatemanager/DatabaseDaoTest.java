package com.openclassrooms.realestatemanager;

import android.content.Context;

import androidx.annotation.Nullable;
import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.room.Room;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.openclassrooms.realestatemanager.Database.AppDatabase;
import com.openclassrooms.realestatemanager.Models.HouseSeller;
import com.openclassrooms.realestatemanager.Models.InterestPoint;
import com.openclassrooms.realestatemanager.Models.Media;
import com.openclassrooms.realestatemanager.Models.Property;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import static junit.framework.TestCase.assertEquals;
import static org.junit.Assert.*;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */@RunWith(AndroidJUnit4.class)
public class DatabaseDaoTest {

    // For data
    private AppDatabase appDatabase;
    private HouseSeller houseSeller = new HouseSeller(1,"BeauChamp", "Alexis");
    private InterestPoint interestPoint = new InterestPoint(2, "Ecole");
    private Media media = new Media(3, "Ceci est un test", "il n'y a pas d'url","4");
    private Property property = new Property(4,234000,153,5,"Maison", "Trop belle", "Chez moi", "Vendu", "21/12/2020", "22/12/2020", "2", "1");


    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();
    private Context instrumentationContext = InstrumentationRegistry.getInstrumentation().getContext();


    @Before
    public void initDatabase() throws Exception {
        this.appDatabase = Room.inMemoryDatabaseBuilder( instrumentationContext, AppDatabase.class)
                .allowMainThreadQueries()
                .build();
    }

    @After
    public void closeDb() throws Exception {
        appDatabase.close();
    }

    @Test
    public void insertAndGetProperty() throws InterruptedException {

        // Test HouseSellerDAO
        this.appDatabase.houseSellerDAO().createHouseSeller(houseSeller);
        HouseSeller houseSeller1  = getValue(this.appDatabase.houseSellerDAO().getHouseSellerById(1));
        assertEquals(houseSeller1.getName(), houseSeller.getName());

        // Test InterestPointDAO
        this.appDatabase.interestPointDAO().createInterestPoint(interestPoint);
        InterestPoint interestPoint1  = getValue(this.appDatabase.interestPointDAO().getInterestPointById(2));
        assertEquals(interestPoint1.getCategory(), interestPoint.getCategory());

        // Test PropertyDao
        this.appDatabase.propertyDAO().createProperty(property);
        Property property1  = getValue(this.appDatabase.propertyDAO().getPropertyById(4));
        assertEquals(property1.getAddress(), property.getAddress());

        // Test MediaDao
        this.appDatabase.mediaDAO().createMedia(media);
        Media media1  = getValue(this.appDatabase.mediaDAO().getMediaById(3));
        assertEquals(media1.getMediaUrl(), media.getMediaUrl());

    }


    public static <T> T getValue(final LiveData<T> liveData) throws InterruptedException {
        final Object[] data = new Object[1];
        final CountDownLatch latch = new CountDownLatch(1);
        Observer<T> observer = new Observer<T>() {
            @Override
            public void onChanged(@Nullable T o) {
                data[0] = o;
                latch.countDown();
                liveData.removeObserver(this);
            }
        };
        liveData.observeForever(observer);
        latch.await(2, TimeUnit.SECONDS);
        //noinspection unchecked
        return (T) data[0];
    }
}
