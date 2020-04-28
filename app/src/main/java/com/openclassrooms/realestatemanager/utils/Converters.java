package com.openclassrooms.realestatemanager.utils;

import android.net.Uri;

import androidx.room.TypeConverter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Calendar;

public class Converters {

     @TypeConverter
    public static Calendar StringToCalendar (String value){
        Type calendarType = new TypeToken<Calendar>() {}.getType();
        return new Gson().fromJson(value, calendarType);
    }

    @TypeConverter
    public static String CalendarToString (Calendar calendar){
        Gson gson = new Gson();
        return gson.toJson(calendar);
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

}
