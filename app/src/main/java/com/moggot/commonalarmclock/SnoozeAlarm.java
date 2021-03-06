package com.moggot.commonalarmclock;

import android.app.*;
import android.app.AlarmManager;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.moggot.commonalarmclock.alarm.Alarm;

public class SnoozeAlarm {

    private static final String LOG_TAG = SnoozeAlarm.class.getSimpleName();

    private Context context;

    public SnoozeAlarm(Context context) {
        this.context = context;
    }

    public void setAlarm(Alarm alarm, int requestCode, long currentTime) {
        Intent intent = new Intent(context, AlarmManagerBroadcastReceiver.class);
        intent.putExtra(Consts.EXTRA_ID, alarm.getId());
        PendingIntent pi = PendingIntent.getBroadcast(context, requestCode, intent, 0);
        AlarmManager am = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        currentTime += Consts.SNOOZE_TIME_IN_MINUTES * 60000;
        am.set(AlarmManager.RTC_WAKEUP, currentTime, pi);
    }
}
