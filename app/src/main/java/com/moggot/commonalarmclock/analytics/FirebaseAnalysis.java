package com.moggot.commonalarmclock.analytics;

/**
 * Created by dmitry on 25.11.16.
 */

import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;

import com.google.firebase.analytics.FirebaseAnalytics;
import com.moggot.commonalarmclock.Consts;

/**
 * Created by dmitry on 25.11.16.
 */

public class FirebaseAnalysis {

    private FirebaseAnalytics firebaseAnalytics;

    public FirebaseAnalysis(Context ctx) {
        if (ActivityCompat.checkSelfPermission(ctx, android.Manifest.permission.WAKE_LOCK) == PackageManager.PERMISSION_GRANTED)
            firebaseAnalytics = FirebaseAnalytics.getInstance(ctx);
    }

    public void init() {
        Bundle bundle = new Bundle();
        bundle.putString(FirebaseAnalytics.Param.ITEM_ID, Consts.FIREBASE_ITEM_ID);
        bundle.putString(FirebaseAnalytics.Param.ITEM_NAME, Consts.FIREBASE_ITEM_NAME);
        bundle.putString(FirebaseAnalytics.Param.CONTENT_TYPE, Consts.FIREBASE_CONTENT_TYPE);
        firebaseAnalytics.logEvent(FirebaseAnalytics.Event.SELECT_CONTENT, bundle);
    }
}