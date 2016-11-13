package com.example.shayzambrovski.digitalrecipe;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

/**
 * Created by Shay Zambrovski on 13/11/2016.
 */
public class MyStartServiceReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Intent dailyUpdater = new Intent(context, MyService.class);
        context.startService(dailyUpdater);
        Log.e("Error: ", "Called context.startService from AlarmReceiver.onReceive");
    }
}
