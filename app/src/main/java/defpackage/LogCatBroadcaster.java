package defpackage;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Build.VERSION;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class LogCatBroadcaster implements Runnable {
    private static boolean started = false;
    private Context context;

    private LogCatBroadcaster(Context context) {
        this.context = context;
    }

    public static void start(Context context) {
        Object obj = 1;
        synchronized (LogCatBroadcaster.class) {
            try {
                if (!started) {
                    started = true;
                    if (VERSION.SDK_INT >= 16) {
                        if ((context.getApplicationInfo().flags & 2) == 0) {
                            obj = null;
                        }

                        if (obj != null) {
                            try {
                                context.getPackageManager().getPackageInfo("com.aide.ui", PackageManager.GET_META_DATA);
                                new Thread(new LogCatBroadcaster(context)).start();
                            } catch (NameNotFoundException e) {
                            }
                        }
                    }
                }
            } catch (Throwable th) {
                Class cls = LogCatBroadcaster.class;
            }
        }
    }

    public void run() {
        try {
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(Runtime.getRuntime().exec("logcat -v threadtime").getInputStream()), 20);
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                Intent intent = new Intent();
                intent.setPackage("com.aide.ui");
                intent.setAction("com.aide.runtime.VIEW_LOGCAT_ENTRY");
                intent.putExtra("lines", new String[]{line});
                context.sendBroadcast(intent);
            }
        } catch (IOException e) {
        }
    }
}
