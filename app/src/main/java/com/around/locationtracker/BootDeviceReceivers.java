package com.around.locationtracker;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import androidx.core.content.ContextCompat;

public class BootDeviceReceivers extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {

        ContextCompat.startForegroundService(context, intent);
    }
}
