package com.example.shayzambrovski.digitalrecipe;

import android.app.IntentService;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

/**
 * Created by Shay Zambrovski on 13/11/2016.
 */
public class MyService extends IntentService {
    public MyService() {
        super("MyService");
    }
    @Override
    protected void onHandleIntent(Intent intent) {
        Log.e("Error: ", "About to execute MyTask");
        new MyTask().execute();
        this.sendNotification(this);
    }
    private class MyTask extends AsyncTask<String, Void, Boolean> {
        @Override
        protected Boolean doInBackground(String... strings) {
            Log.e("Error: ", "Calling doInBackground within MyTask");
            return false;
        }
    }
    private void sendNotification(Context context) {

        DatabaseHandler db = new DatabaseHandler(this);
        String sUserName = db.getLogInUser();
        if(sUserName != null) {

        } else {
            return;
        }

        NotificationCompat.Builder builder =
                new NotificationCompat.Builder(context)
                        .setSmallIcon(R.drawable.app_logo)
                        .setContentTitle(getResources().getString(R.string.success))
                        .setContentText(" " + getResources().getString(R.string.saved));

        // Add as notification
        NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        manager.notify(0, builder.build());
    }
}
