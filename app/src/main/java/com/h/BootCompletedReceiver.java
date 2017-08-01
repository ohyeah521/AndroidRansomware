package com.h;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;

public class BootCompletedReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals("android.intent.action.BOOT_COMPLETED")) {
            abortBroadcast();
            try {
                Intent intent2 = new Intent(context, Class.forName("com.h.MyService"));
                intent2.addFlags(FLAG_ACTIVITY_NEW_TASK);
                context.startService(intent2);
            } catch (Throwable e) {
                throw new NoClassDefFoundError(e.getMessage());
            }
        }
    }
}
