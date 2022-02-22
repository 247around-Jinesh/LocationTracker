package com.around.locationtracker;

import static android.Manifest.permission.ACCESS_COARSE_LOCATION;
import static android.Manifest.permission.ACCESS_FINE_LOCATION;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity{

    Button buttonStartService,buttonStopService;
    //GPSTracker gps;
    TextView textView1;
    public static final int REQUEST_ID_MULTIPLE_PERMISSIONS = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //gps = new GPSTracker(this);
        buttonStartService=(Button) findViewById(R.id.btn);
        textView1=(TextView) findViewById(R.id.textView1);
        buttonStopService=(Button) findViewById(R.id.textView);
        /////////////////////////////////////////////
        checkAndLocationRequestPermissions();
        Calendar calStart = Calendar.getInstance();

        calStart.set(Calendar.HOUR_OF_DAY, 18);
        calStart.set(Calendar.MINUTE, 20);
        calStart.set(Calendar.SECOND, 0);
        calStart.set(Calendar.MILLISECOND, 0);

        Intent intent = new Intent(getBaseContext(), TestReceiver.class);
        intent.setAction("START_SERVICE");

        PendingIntent pendingIntent = PendingIntent.getBroadcast(
                getBaseContext(), 1, intent, 0);
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        /*alarmManager.set(AlarmManager.RTC_WAKEUP, calStart.getTimeInMillis(),
                pendingIntent);*/


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, calStart.getTimeInMillis(), pendingIntent);
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            alarmManager.setExact(AlarmManager.RTC_WAKEUP, calStart.getTimeInMillis(), pendingIntent);
        } else {
            alarmManager.set(AlarmManager.RTC_WAKEUP, calStart.getTimeInMillis(), pendingIntent);
        }
        /////////////////////////////////////////////
        Calendar calEnd = Calendar.getInstance();

        calEnd.set(Calendar.HOUR_OF_DAY, 18);
        calEnd.set(Calendar.MINUTE, 25);
        calEnd.set(Calendar.SECOND, 0);
        calEnd.set(Calendar.MILLISECOND, 0);
        Intent intentEnd = new Intent(getBaseContext(), TestReceiver.class);
        intentEnd.setAction("STOP_SERVICE");
        PendingIntent pendingIntentEnd = PendingIntent.getBroadcast(
                getBaseContext(), 2, intentEnd, 0);
        AlarmManager alarmManager2 = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            alarmManager2.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, calEnd.getTimeInMillis(), pendingIntentEnd);
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            alarmManager2.setExact(AlarmManager.RTC_WAKEUP, calEnd.getTimeInMillis(), pendingIntentEnd);
        } else {
            alarmManager2.set(AlarmManager.RTC_WAKEUP, calEnd.getTimeInMillis(), pendingIntentEnd);
        }
        /*alarmManager2.set(AlarmManager.RTC_WAKEUP, calEnd.getTimeInMillis(),
                pendingIntentEnd);*/
        /*//////////////////////////////*/
        buttonStartService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*if (!checkAndLocationRequestPermissions()) {
                    // check if GPS enabled
                        try {
                            Intent intent=new Intent(getApplicationContext(),LocationService.class);

                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                                ContextCompat.startForegroundService(getApplicationContext(),intent);
                            } else {
                                startService(intent);
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }



                }*/

            }
        });
        buttonStopService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*Intent intent=new Intent(getApplicationContext(),LocationService.class);
                stopService(intent);*/
            }
        });
        /*////////////////////////////////////////*/



    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    public boolean checkAndLocationRequestPermissions() {
        int accessFineLocation = ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION);

        int accessCoarseLocation = ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_COARSE_LOCATION);

        int fgservice = ContextCompat.checkSelfPermission(this,
                Manifest.permission.FOREGROUND_SERVICE);

        List<String> listPermissionsNeeded = new ArrayList<>();

        if (accessCoarseLocation != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.ACCESS_COARSE_LOCATION);
        }

        if (accessFineLocation != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.ACCESS_FINE_LOCATION);
        }

        if (fgservice != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.FOREGROUND_SERVICE);
        }

        if (!listPermissionsNeeded.isEmpty()) {
            ActivityCompat.requestPermissions(this,
                    listPermissionsNeeded.toArray(new String[listPermissionsNeeded.size()]),
                    REQUEST_ID_MULTIPLE_PERMISSIONS);
            return false;
        }
        return true;
    }

}
