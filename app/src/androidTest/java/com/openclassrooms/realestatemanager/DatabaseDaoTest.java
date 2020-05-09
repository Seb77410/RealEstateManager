package com.openclassrooms.realestatemanager;

import android.content.Context;

import androidx.annotation.Nullable;
import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.room.Room;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;

import com.openclassrooms.realestatemanager.database.AppDatabase;
import com.openclassrooms.realestatemanager.models.database.HouseSeller;
import com.openclassrooms.realestatemanager.models.database.InterestPoint;
import com.openclassrooms.realestatemanager.models.database.Property;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import static junit.framework.TestCase.assertEquals;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */@RunWith(AndroidJUnit4.class)
public class DatabaseDaoTest {

    // For data
    private AppDatabase appDatabase;
    private HouseSeller houseSeller = new HouseSeller("BeauChamp", "beauchamp@gmail.com");
    private InterestPoint interestPoint = new InterestPoint(new ArrayList<>());

    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();
    private Context instrumentationContext = InstrumentationRegistry.getInstrumentation().getContext();


    @Before
    public void initDatabase(){
        this.appDatabase = Room.inMemoryDatabaseBuilder( instrumentationContext, AppDatabase.class)
                .allowMainThreadQueries()
                .build();
    }

    @After
    public void closeDb(){
        appDatabase.close();
    }

    @Test
    public void createAndGetData() throws InterruptedException {

        // Test HouseSellerDAO
        long houseSellerId = this.appDatabase.houseSellerDAO().createHouseSeller(houseSeller);
        HouseSeller houseSeller1  = getValue(this.appDatabase.houseSellerDAO().getHouseSellerByName("BeauChamp"));
        assertEquals(houseSeller1.getEmail(), houseSeller.getEmail());

        // Test InterestPointDAO
        long interestPointId = this.appDatabase.interestPointDAO().createInterestPoint(interestPoint);
        InterestPoint interestPoint1 = getValue(this.appDatabase.interestPointDAO().getInterestPointById(interestPointId));
        assertEquals(interestPoint1.getCategory(), interestPoint.getCategory());

        // Test PropertyDao
        Property property = new Property(234000,153,5,"Home", "Trop belle", "Chez moi", false, Calendar.getInstance(), null, interestPointId, houseSellerId);
        long propertyId = this.appDatabase.propertyDAO().createProperty(property);
        Property property1  = getValue(this.appDatabase.propertyDAO().getPropertyById(propertyId));
        assertEquals(property1.getType(), property.getType());
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
