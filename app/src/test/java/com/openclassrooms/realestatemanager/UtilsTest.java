package com.openclassrooms.realestatemanager;

import org.junit.Test;

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

}