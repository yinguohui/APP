package com.example.ygh.app.global;

import android.content.Context;

public class CrashHandler implements Thread.UncaughtExceptionHandler {
    private Context context;
    private static CrashHandler crashHandler;

    private CrashHandler() {

    }

    public void init(Context context) {
        this.context = context;
    }

    public static synchronized CrashHandler getInstance() {
        if (crashHandler == null) {
            crashHandler = new CrashHandler();
        }
        return crashHandler;
    }

    @Override
    public void uncaughtException(Thread t, Throwable e) {

    }
}
