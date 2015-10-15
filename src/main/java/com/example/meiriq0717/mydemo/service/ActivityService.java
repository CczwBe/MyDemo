package com.example.meiriq0717.mydemo.service;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.Nullable;

/**
 * Created by meiriq0717 on 2015/10/12.
 */
public class ActivityService extends Service {
    private MyBinder myBinder;
    private String str = "BinderService";

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return myBinder;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onCreate() {
        myBinder = new MyBinder();
        super.onCreate();
    }

    public class MyBinder extends Binder {
        public ActivityService getService() {
            return ActivityService.this;
        }

    }

    public String getData() {
        return str;
    }
}
