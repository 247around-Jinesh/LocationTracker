package com.around.locationtracker;

import android.annotation.SuppressLint;
import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;

import androidx.annotation.NonNull;

import java.util.List;

public class LocationHelper {
    int LOCATION_REFRESH_TIME = 10000;// 3 seconds. The Minimum Time to get location update
    int LOCATION_REFRESH_DISTANCE = 0; // 0 meters. The Minimum Distance to be changed to get location update

    @SuppressLint("MissingPermission")
    public void startListeningUserLocation(Context context,MyLocationListener myLocationListener) {
        // getting GPS status
        LocationManager mLocationManager=(LocationManager) context.getSystemService(Context.LOCATION_SERVICE);

        LocationListener locationListener=new LocationListener() {
            @Override
            public void onLocationChanged(@NonNull Location location) {
                myLocationListener.onLocationChanged(location);
            }

            @Override
            public void onLocationChanged(@NonNull List<Location> locations) {

            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {

            }

            @Override
            public void onProviderEnabled(@NonNull String provider) {

            }

            @Override
            public void onProviderDisabled(@NonNull String provider) {

            }
        };
        mLocationManager.requestLocationUpdates(
                LocationManager.GPS_PROVIDER,
                LOCATION_REFRESH_TIME,
                LOCATION_REFRESH_DISTANCE,
                locationListener);
    }
}

interface MyLocationListener {
    void onLocationChanged(Location location);
}
