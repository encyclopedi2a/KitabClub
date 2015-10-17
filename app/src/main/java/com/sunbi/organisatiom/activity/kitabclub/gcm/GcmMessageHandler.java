package com.sunbi.organisatiom.activity.kitabclub.gcm;

import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.sunbi.organisatiom.activity.kitabclub.PushNotification;
import com.sunbi.organisatiom.activity.kitabclub.R;

/**
 * Created by gokarna on 8/28/15.
 */
public class GcmMessageHandler extends IntentService {
    String title;
    String message;
    public GcmMessageHandler() {
        super("GcmMessageHandler");
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        Bundle extras = intent.getExtras();

        GoogleCloudMessaging gcm = GoogleCloudMessaging.getInstance(this);
        // The getMessageType() intent parameter must be the intent you received
        // in your BroadcastReceiver.
        String messageType = gcm.getMessageType(intent);

        title = extras.getString("title");
        message = extras.getString("message");
        Log.i("GCM",
                "Received Title : (" + messageType + ")  "
                        + extras.getString("title"));
        Log.i("GCM",
                "Received : Message (" + messageType + ")  "
                        + extras.getString("message"));
        Intent intentCalled = new Intent(this, PushNotification.class);
        PendingIntent pIntent = PendingIntent.getActivity(this, 0,
                intentCalled, 0);
        // build notification
        // the addAction re-use the same intent to keep the example short
        Notification notification = new NotificationCompat.Builder(getApplicationContext())
                .setContentTitle(title).setContentText(message)
                .setSmallIcon(R.mipmap.ic_launcher).setContentIntent(pIntent)
                .build();
        // Play default notification sound
        notification.defaults |= Notification.DEFAULT_SOUND;

        // Vibrate if vibrate is enabled
        notification.defaults |= Notification.DEFAULT_VIBRATE;
        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

        notificationManager.notify(0, notification);

        GcmBroadcastReceiver.completeWakefulIntent(intent);
    }
}
