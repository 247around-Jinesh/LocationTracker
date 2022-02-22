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
    public static Context ctx;
    @Override
    public void onReceive(Context context, Intent intent) {
        if(intent.getAction()!=null) {
            if (!intent.getAction().equals("STOP_SERVICE")) {
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
    public static boolean isServiceLive() {
        int start = 9;
        int end = 12;
        int hours = 24 - start + end;

        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.HOUR_OF_DAY, start);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);

        long startHourMilli = cal.getTimeInMillis();
        Log.e("time",cal.getTime()+"");

        cal.add(Calendar.HOUR_OF_DAY, hours);
        long endHourMilli = cal.getTimeInMillis();

        long currentMilli = Calendar.getInstance().getTimeInMillis();


        if (currentMilli >= startHourMilli && currentMilli < endHourMilli) {
            return true;
        }else
            return false;

    }
}
