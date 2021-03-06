package com.openclassrooms.realestatemanager.utils;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;

import android.content.Intent;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;
import android.provider.Settings;

import androidx.core.app.NotificationCompat;

import com.openclassrooms.realestatemanager.R;
import com.openclassrooms.realestatemanager.ui.activities.MainActivity;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;

/**
 * Created by Philippe on 21/02/2018.
 */

public class Utils {

    //----------------------------------------------------------------------------------------------
    // Exercice 1
    //----------------------------------------------------------------------------------------------

    /**
     * Conversion d'un prix d'un bien immobilier (Dollars vers Euros)
     * NOTE : NE PAS SUPPRIMER, A MONTRER DURANT LA SOUTENANCE
     */
    public static int convertDollarToEuro(int dollars){
        return (int) Math.round(dollars * 0.812);
    }

    // ----- Correction -----
    public static int convertEuroToDollar(int euro){
        return (int) Math.round(euro * 1.231);
    }

    //----------------------------------------------------------------------------------------------
    // Exercice 2
    //----------------------------------------------------------------------------------------------

    /**
     * Conversion de la date d'aujourd'hui en un format plus approprié
     * NOTE : NE PAS SUPPRIMER, A MONTRER DURANT LA SOUTENANCE
     */
    public static String getTodayDate(){
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
        return dateFormat.format(new Date());
    }

    // ----- Correction -----
    public static String getTodayDateNew(){
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        return dateFormat.format(new Date());
    }

    //----------------------------------------------------------------------------------------------
    // Exercice 3
    //----------------------------------------------------------------------------------------------

    /**
     * Vérification de la connexion réseau
     * NOTE : NE PAS SUPPRIMER, A MONTRER DURANT LA SOUTENANCE
     */
    public static Boolean isInternetAvailable(Context context){
        WifiManager wifi = (WifiManager)context.getSystemService(Context.WIFI_SERVICE);
        return wifi.isWifiEnabled();
    }

    // ----- Correction -----
    public static Boolean isInternetAvailableNew(ConnectivityManager connectivityManager){
        NetworkInfo networkInfo;
        if (connectivityManager != null) {
            networkInfo = connectivityManager.getActiveNetworkInfo();
            if (networkInfo != null) {
                return networkInfo.isConnected();
            }else{return false;}
        }
        else { return false;}
    }

    //----------------------------------------------------------------------------------------------
    // My Utils
    //----------------------------------------------------------------------------------------------

    public static void startNotification(String title, String messageContent, Context context){
        Intent resultIntent = new Intent(context , MainActivity.class);
        resultIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        PendingIntent resultPendingIntent = PendingIntent.getActivity(context, 0,
                resultIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(context, MyConstants.CHANNEL_1_ID);
        mBuilder.setSmallIcon(R.drawable.ic_home_24px)
                .setContentTitle(title)
                .setContentText(messageContent)
                .setSound(Settings.System.DEFAULT_NOTIFICATION_URI)
                .setContentIntent(resultPendingIntent);

        NotificationManager mNotificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel notificationChannel = new NotificationChannel(MyConstants.CHANNEL_1_ID, MyConstants.CHANNEL_1_NAME, importance);
            notificationChannel.enableLights(true);
            notificationChannel.setLightColor(Color.RED);
            notificationChannel.enableVibration(true);
            notificationChannel.setVibrationPattern(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400});
            mBuilder.setChannelId(MyConstants.CHANNEL_1_ID);
            Objects.requireNonNull(mNotificationManager).createNotificationChannel(notificationChannel);
        }
        Objects.requireNonNull(mNotificationManager).notify(0, mBuilder.build());
    }

}
