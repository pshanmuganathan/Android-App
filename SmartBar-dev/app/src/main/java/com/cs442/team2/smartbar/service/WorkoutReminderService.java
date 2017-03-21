package com.cs442.team2.smartbar.service;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.IBinder;
import android.util.Log;

import com.cs442.team2.smartbar.R;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by SumedhaGupta on 11/24/16.
 */

public class WorkoutReminderService extends Service {


    int entered_counter;
    private Timer timer;
    private Timer timerForNotif;
    TimerTask showNotificationTask;

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public void onCreate() {
        // TODO: Actions to perform when service is created.
        Log.i("MyService", "onCreate called");
    }


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        super.onStartCommand(intent, flags, startId);
        Log.i("MyService", "onStart called");

        startTimer(entered_counter); //start Timer from that counter
        return Service.START_NOT_STICKY; //after closing start the activity using this function e.g Music
    }


    public void startTimer(int entered_counter) {

        timerForNotif = new Timer();
        startNotification();
        timerForNotif.schedule(showNotificationTask, 10000, 10000);
    }


    /**
     * stop the timer, if it's not already null
     */
    public void stoptimertask() {
        //stop the timer, if it's not already null
        if (timer != null) {
            timer.cancel();
            timer = null;
        }
    }

    int notId = 1;

    private void startNotification() {

        showNotificationTask = new TimerTask() {
            public void run() {

                Notification.Builder mBuilder =
                        new Notification.Builder(getApplicationContext())
                                .setSmallIcon(R.mipmap.ic_launcher)
                                .setContentTitle("My notification")
                                .setContentText("Current Counting number:" + entered_counter);
                //Vibration
                mBuilder.setVibrate(new long[]{1000, 1000, 1000, 1000, 1000});

                //Notification Tone
                Uri notifSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

                mBuilder.setSound(notifSound);
                NotificationManager notificationManager =
                        (NotificationManager) getSystemService(Service.NOTIFICATION_SERVICE);
                startForeground(notId, mBuilder.build());

            }
        };


    }

    @Override
    public void onDestroy() {
        Log.i("MyService", "onDestroy Called");
        stoptimertask();
    }

}


