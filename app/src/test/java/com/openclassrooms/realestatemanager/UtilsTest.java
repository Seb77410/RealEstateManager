package com.openclassrooms.realestatemanager;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.openclassrooms.realestatemanager.utils.Utils;

import org.junit.Test;
import org.mockito.Mockito;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class UtilsTest {

    @Test
    public void convertEuroToDollarTest() {
        int euros = 10;
        int dollars = 12;
        assertEquals(dollars, Utils.convertEuroToDollar(euros));
    }

    @Test
    public void convertDollarToEuroTest() {
        int euros = 8;
        int dollars = 10;
        assertEquals(euros, Utils.convertDollarToEuro(dollars));
    }

    @Test
    public void getTodayDateNewTest() {
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        String currentDate = dateFormat.format(new Date());
        assertEquals(currentDate, Utils.getTodayDateNew());
    }

    @Test
    public void isInternetAvailableTest() {
        final NetworkInfo networkInfo = Mockito.mock( NetworkInfo.class );
        final ConnectivityManager connectivityManager = Mockito.mock( ConnectivityManager.class );
        Mockito.when( connectivityManager.getActiveNetworkInfo() ).thenReturn( networkInfo );
        Mockito.when( networkInfo.isConnected() ).thenReturn( false );

        assertEquals(false, Utils.isInternetAvailableNew(connectivityManager));
    }



}