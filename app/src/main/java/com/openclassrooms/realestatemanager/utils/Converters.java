package com.openclassrooms.realestatemanager.utils;

import android.net.Uri;

import androidx.room.TypeConverter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.openclassrooms.realestatemanager.models.database.Property;

import java.lang.reflect.Type;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

public class Converters {

    @TypeConverter
    public static Calendar stringToCalendar(String value){
        if (value!=null){
            Calendar calendar = Calendar.getInstance();
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd", Locale.getDefault());
            try {
                calendar.setTime(Objects.requireNonNull(simpleDateFormat.parse(value)));
            } catch (ParseException e) {
                e.printStackTrace();
            }
            return calendar;
        }else {return null;}
    }

    @TypeConverter
    public static String calendarToString(Calendar calendar){
        if (calendar != null){
            return calendar.get(Calendar.YEAR) +
                convertDateIntToString(calendar.get(Calendar.MONTH)+1)+
                convertDateIntToString(calendar.get(Calendar.DAY_OF_MONTH));
        }else {
            return null;
        }
    }

    @TypeConverter
    public static ArrayList<String> stringToArray(String value){
        Type arrayType = new TypeToken<ArrayList<String>>() {}.getType();
        return new Gson().fromJson(value, arrayType);
    }

    @TypeConverter
    public static String arrayToString(ArrayList<String> arrayList){
        Gson gson = new Gson();
        return gson.toJson(arrayList);
    }

    @TypeConverter
    public static String uriToString(Uri uri) {
        return uri.toString();
    }

    @TypeConverter
    public static Uri stringToUri(String uri){
        return Uri.parse(uri);
    }



    public static String convertPropertyToString(Property property) {
        Gson gson = new Gson();
        return gson.toJson(property);
    }

    public static Property convertStringToProperty(String property) {
        Type propertyType = new TypeToken<Property>() {
        }.getType();
        return new Gson().fromJson(property, propertyType);
    }

    public static String convertPropertiesListToString(List<Property> properties){
        Gson gson = new Gson();
        return gson.toJson(properties);
    }

    public static List<Property> convertStringToPropertiesList(String propertiesList) {
        Type propertyType = new TypeToken<List<Property>>() {
        }.getType();
        return new Gson().fromJson(propertiesList, propertyType);
    }


    public static String convertCalendarToFormatString(Calendar calendar){

        String dayOfMonth = convertDateIntToString(calendar.get(Calendar.DAY_OF_MONTH));
        String month = convertDateIntToString(calendar.get(Calendar.MONTH)+1);
        String year = String.valueOf(calendar.get(Calendar.YEAR));

        return dayOfMonth+"-"+month+"-"+year;
    }

    public static String convertDateIntToString(int date){
        if (date<10){
            return  "0" + date;
        }
        else {return String.valueOf(date);}
    }


}
