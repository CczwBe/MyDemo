package com.example.meiriq0717.mydemo.service;

import android.app.IntentService;
import android.content.Intent;
import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by meiriq0717 on 2015/10/12.
 */
public class MyServicce extends IntentService {
    private static final String TAG = "MyServicce";
    private String filePath;
    private boolean complete = false;

    public MyServicce() {
        super("MyService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        filePath=new Data().getImagpath();
        saveData2SdCard(filePath);
    }

    private void saveData2SdCard(String filePath) {
        File file;
        InputStream fis = null;
        FileOutputStream fos = null;
        Log.i(TAG, filePath + "==================");
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            file = new File(Environment.getExternalStorageDirectory().getAbsolutePath(), "sd.jpg");
            try {
                URL url = new URL(filePath);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setDoInput(true);
                connection.connect();
                connection.setConnectTimeout(10 * 1000);
                fis = connection.getInputStream();
                fos = new FileOutputStream(file);
                int len = 0;
                byte[] bytes = new byte[1024 * 4];
                while ((len = fis.read(bytes)) != -1) {
                    fos.write(bytes, 0, len);
                    fos.flush();
                }
                new Data().mcallBack.compleate(true,file);
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (fis != null) {
                    try {
                        fis.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                if (fos != null) {
                    try {
                        fos.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }


            }
        }
    }

    public MyServicce(String name) {
        super(name);
    }

    @Override
    public void onCreate() {
        Log.i(TAG, "      onCreate     ");
        super.onCreate();
    }

    @Override
    public void onStart(Intent intent, int startId) {
        Log.i(TAG, "      onStart     ");
        super.onStart(intent, startId);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.i(TAG, "        onStartCommand             ");
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.i(TAG, "     onDestroy             ");
    }

    public void setPath(String str) {
        filePath = str;
    }



    public static class Data{
        public static ServiceCompleteCallBack mcallBack;
        public static  String imagpath=null;
        public void setPath(String path){
            imagpath=path;
        }
        public  String getImagpath(){
            return imagpath;
        }
        public interface ServiceCompleteCallBack {
            public void compleate(boolean compleate, File file);
        }
        public void setCallBack(ServiceCompleteCallBack callBack) {
            mcallBack = callBack;
        }

    }
}
