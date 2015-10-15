package com.example.meiriq0717.mydemo.service;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.support.annotation.Nullable;
import android.widget.Toast;

/**
 * Created by meiriq0717 on 2015/10/13.
 */
public class MessengerService extends Service {
    private Messenger mMessenger;
    private mHandler handler;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        handler = new mHandler();
        mMessenger = new Messenger(handler);
        return mMessenger.getBinder();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);
    }

    public class mHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 0x1:
                    Toast.makeText(getApplicationContext(), "服务接收数据"+msg.arg1, Toast.LENGTH_SHORT).show();
                    break;
            }
            super.handleMessage(msg);
        }
    }
}
