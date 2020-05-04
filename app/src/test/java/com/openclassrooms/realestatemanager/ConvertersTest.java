package com.openclassrooms.realestatemanager;

import com.openclassrooms.realestatemanager.models.database.Property;
import com.openclassrooms.realestatemanager.utils.Converters;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNull;

public class ConvertersTest {

    @Test
    public void stringToCalendarTest() {
        String date = "19861127";
        Calendar calendar = Converters.stringToCalendar(date);

        assertEquals(calendar.get(Calendar.YEAR), 1986);
        assertEquals(calendar.get(Calendar.DAY_OF_MONTH), 27);
    }

    @Test
    public void calendarToStringTest() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, 1986);
        calendar.set(Calendar.MONTH, 11);
        calendar.set(Calendar.DAY_OF_MONTH, 27);

        assertEquals(Converters.calendarToString(calendar), "19861127");
    }

    @Test
    public void stringToArrayTest() {
        ArrayList<String> array = new ArrayList<>();
        String stringArray = Converters.arrayToString(array);

        assertEquals(array, Converters.stringToArray(stringArray));
    }

    @Test
    public void convertStringToPropertyTest(){
        Property property = new Property();
        String sProperty = Converters.convertPropertyToString(property);

        assertEquals(Converters.convertStringToProperty(sProperty).getId(), 0);
        assertNull(Converters.convertStringToProperty(sProperty).getAddress());
        assertEquals(Converters.convertStringToProperty(sProperty).getRooms(), 0);
    }

    @Test
    public void convertPropertiesListToStringTest(){
        List<Property> propertiesList = new ArrayList<>();
        propertiesList.add(new Property());
        String sPropertiesList = Converters.convertPropertiesListToString(propertiesList);

        assertEquals(Converters.convertStringToPropertiesList(sPropertiesList).get(0).getId(), propertiesList.get(0).getId());
        assertEquals(Converters.convertStringToPropertiesList(sPropertiesList).get(0).getAddress(), propertiesList.get(0).getAddress());
        assertEquals(Converters.convertStringToPropertiesList(sPropertiesList).get(0).getRooms(), propertiesList.get(0).getRooms());
    }

    @Test
    public void convertCalendarToFormatStringTest(){
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, 1986);
        calendar.set(Calendar.MONTH, 11);
        calendar.set(Calendar.DAY_OF_MONTH, 27);

        assertEquals("27-12-1986", Converters.convertCalendarToFormatString(calendar));
    }

    @Test
    public void convertDateIntToString(){
        int date = 3;
        int date2 = 15;

        assertEquals(Converters.convertDateIntToString(date), "03");
        assertNotEquals(Converters.convertDateIntToString(date), "3");
        assertEquals(Converters.convertDateIntToString(date2), "15");
        assertNotEquals(Converters.convertDateIntToString(date2), "015");
    }
}
