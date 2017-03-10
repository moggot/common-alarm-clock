package com.moggot.commonalarmclock;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

public class AlarmManagerBroadcastReceiver extends BroadcastReceiver {

    private static final String LOG_TAG = "AlarmManagerBroadcast";

    @SuppressLint("InlinedApi")
    @Override
    public void onReceive(Context context, Intent intent) {

        Log.v(LOG_TAG, "startbroadcts");
        Bundle extras = intent.getExtras();
        long id = extras.getLong(Consts.EXTRA_ID);
        intent = new Intent(context, ActivityAlarm.class);
        intent.putExtra(Consts.EXTRA_ID, id);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK
                | Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }
}