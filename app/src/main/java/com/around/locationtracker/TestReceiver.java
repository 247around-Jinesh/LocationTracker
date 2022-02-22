package com.around.locationtracker;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.util.Log;
import android.widget.Toast;

import androidx.core.content.ContextCompat;

import java.util.Calendar;

public class TestReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        if(intent.getAction()!=null) {
            if (intent.getAction().equals("START_SERVICE")) {
                Intent intentservice = new Intent(context, LocationService.class);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    ContextCompat.startForegroundService(context, intentservice);
                } else {
                    context.startService(intentservice);
                }
            } else {
                Intent intentstop = new Intent(context, LocationService.class);
                context.stopService(intentstop);
            }
        }
    }
}
