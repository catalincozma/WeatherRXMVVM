package com.example.cozma.weatherapp.util;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.LocationManager;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.util.Log;

import com.example.cozma.weatherapp.MainApp;
import com.example.cozma.weatherapp.R;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by cozma on 03.11.2017.
 */

public class Utils {
    public static String API_KEY = "08545a255ebd260e56c2d426424a51a1";

    public static void showObjectLog(String objectName, Object o) {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String json = gson.toJson(o);
        customInfoLog("GSON " + objectName, "^", json);
    }

    public static void customInfoLog(String activityName, String viewId, String infoMessage) {
        Log.i("--->", " \n");
        Log.i("--->", activityName + "\n---------------------------------------------");
        Log.i("--->" + viewId + "       ", infoMessage);
        Log.i("--->", "---------------------------------------------\n");
        Log.i("--->", " ");
    }

    public static String readCityListJson(Context context) {

        String json = null;
        try {
            //
            InputStream is = context.getAssets().open("city.list.min.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
            return json;
        } catch (IOException ex) {
            ex.printStackTrace();

        }
        return "";
    }
    public static void askGpsPermission(final Context context) {

        AlertDialog.Builder dialog = new AlertDialog.Builder(context);
        dialog.setMessage(context.getResources().getString(R.string.dialogEnableGps_message));
        dialog.setPositiveButton(context.getResources().getString(R.string.dialogEnableGps_buttonOk),
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface paramDialogInterface, int paramInt) {
                        Intent settingsIntent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                        settingsIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        context.startActivity(settingsIntent);
                    }
                });
        dialog.setNegativeButton(context.getResources().getString(R.string.dialogEnableGps_buttonCancel),
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface paramDialogInterface, int paramInt) {

                        ((Activity) context).finish();
                        ActivityCompat.finishAffinity((Activity) context);
                        System.exit(0);
                    }
                });
        dialog.setCancelable(false);
        dialog.show();
    }
    public static Boolean isLocationEnabled(final Context context) {
        LocationManager locationManager = null;
        boolean gpsIsEnabled = false, networkIsEnabled = false;
        if (locationManager == null)
            locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        try {
            gpsIsEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);

        } catch (Exception ex) {
        }
        try {
            networkIsEnabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
        } catch (Exception ex) {
        }

        if (!gpsIsEnabled && !networkIsEnabled) {
            return false;
        }
        return true;
    }

}
