package com.raz.smoketracker;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/** Alarms are wiped by a reboot or an app update, so put a pending one back. */
public class BootReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        long due = context.getSharedPreferences(Alerts.PREFS, Context.MODE_PRIVATE)
                .getLong(Alerts.KEY_DUE, 0);
        if (due > System.currentTimeMillis()) {
            Alerts.schedule(context, due);
        }
    }
}
