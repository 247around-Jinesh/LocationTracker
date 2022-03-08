package com.around.locationtracker;

import android.annotation.SuppressLint;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationManager;
import android.os.Build;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;

import java.util.Timer;

public class LocationService extends Service {
    private String NOTIFICATION_CHANNEL_ID = "my_notification_location";
    private String TAG = "LocationService";
    public static boolean isServiceStarted=false;
    MainActivity activity=new MainActivity();
    @Override
    public void onCreate() {
        super.onCreate();
        isServiceStarted = true;
        NotificationCompat.Builder builder=
                new NotificationCompat.Builder(this, NOTIFICATION_CHANNEL_ID)
                        .setOngoing(false)
                        .setSmallIcon(R.drawable.ic_launcher_background);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            NotificationChannel notificationChannel = new  NotificationChannel(
                    NOTIFICATION_CHANNEL_ID,
                    NOTIFICATION_CHANNEL_ID, NotificationManager.IMPORTANCE_LOW
            );
            notificationChannel.setDescription(NOTIFICATION_CHANNEL_ID);
            notificationChannel.setSound(null, null);
            notificationManager.createNotificationChannel(notificationChannel);
            startForeground(1, builder.build());
        }
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.v("engineerBuddyy","hiiiiiiii");
        new LocationHelper().startListeningUserLocation(
                this, new MyLocationListener() {
                    @Override
                    public void onLocationChanged(Location location) {
                        if(LocationService.isServiceStarted)
                            Log.d("Changed", "onLocationChanged: Latitude" + location.getLatitude() + " , Longitude " + location.getLongitude());
                            Log.d("Changed", "run: Running = Location Update Successful");
                            Toast.makeText(getApplicationContext(), String.valueOf(location.getLongitude()), Toast.LENGTH_SHORT).show();
                    }

                });
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        isServiceStarted = false;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }


}
