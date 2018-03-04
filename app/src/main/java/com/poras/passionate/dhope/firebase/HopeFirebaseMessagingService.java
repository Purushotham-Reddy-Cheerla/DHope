package com.poras.passionate.dhope.firebase;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.poras.passionate.dhope.HomeActivity;
import com.poras.passionate.dhope.R;
import com.poras.passionate.dhope.data.HopeContract;

import java.util.Map;

/**
 * Created by purus on 3/4/2018.
 */

public class HopeFirebaseMessagingService extends FirebaseMessagingService {
    private static final int NOTIFICATION_MAX_CHARACTERS = 30;
    private static final String CHANNEL_ID = "Alerts";
    private static final int NOTIFICATION_ID = 25689;
    private static String LOG_TAG = HopeFirebaseMessagingService.class.getSimpleName();

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);
        Log.d(LOG_TAG, "From: " + remoteMessage.getFrom());
        Map<String, String> data = remoteMessage.getData();

        if (data.size() > 0) {
            Log.d(LOG_TAG, "Message data payload: " + data);
            sendNotification(data);
            insertHopeTransaction(data);

        }
    }

    private void insertHopeTransaction(final Map<String, String> data) {
        AsyncTask<Void, Void, Void> insertMessageTask = new AsyncTask<Void, Void, Void>() {

            @Override
            protected Void doInBackground(Void... voids) {
                long timeNow = System.currentTimeMillis();
                ContentValues contentValues = new ContentValues();
                contentValues.put(HopeContract.HopeEntry.COLUMN_CATEGORY, data.get(HopeContract.HopeEntry.COLUMN_CATEGORY));
                contentValues.put(HopeContract.HopeEntry.COLUMN_TYPE, data.get(HopeContract.HopeEntry.COLUMN_CATEGORY));
                contentValues.put(HopeContract.HopeEntry.COLUMN_QUANTITY, data.get(HopeContract.HopeEntry.COLUMN_CATEGORY));
                contentValues.put(HopeContract.HopeEntry.COLUMN_LAT, data.get(HopeContract.HopeEntry.COLUMN_CATEGORY));
                contentValues.put(HopeContract.HopeEntry.COLUMN_LANG, data.get(HopeContract.HopeEntry.COLUMN_CATEGORY));
                contentValues.put(HopeContract.HopeEntry.COLUMN_TIME, timeNow);
                getContentResolver().insert(HopeContract.HopeEntry.CONTENT_URI, contentValues);
                return null;
            }
        };

        insertMessageTask.execute();
    }

    private void sendNotification(Map<String, String> data) {
        Intent intent = new Intent(this, HomeActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0 /* Request code */, intent,
                PendingIntent.FLAG_ONE_SHOT);

        String title = data.get(HopeContract.HopeEntry.COLUMN_CATEGORY);
        String message = data.get(HopeContract.HopeEntry.COLUMN_CATEGORY);

        if (message.length() > NOTIFICATION_MAX_CHARACTERS) {
            message = message.substring(0, NOTIFICATION_MAX_CHARACTERS) + "\u2026";
        }

        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle(title)
                .setContentText(message)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setAutoCancel(true)
                .setSound(defaultSoundUri)
                .setContentIntent(pendingIntent);

        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        notificationManager.notify(NOTIFICATION_ID, mBuilder.build());
    }
}
