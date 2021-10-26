package com.example.arvoregenealogica;

import android.app.Application;

import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.FormatStrategy;
import com.orhanobut.logger.Logger;
import com.orhanobut.logger.PrettyFormatStrategy;

public class MyApplication extends Application {

    private static MyApplication INStANCE;

    public MyApplication() {
        INStANCE = this;
    }

    public static MyApplication getInstance() {
        if (INStANCE == null) {
            synchronized (MyApplication.class) {
                if (INStANCE == null) {
                    INStANCE = new MyApplication();
                }
            }
        }
        return INStANCE;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        FormatStrategy formatStrategy = PrettyFormatStrategy.newBuilder()
                .tag(getString(R.string.app_name))
                .build();
        Logger.addLogAdapter(new AndroidLogAdapter(formatStrategy));
    }
}
