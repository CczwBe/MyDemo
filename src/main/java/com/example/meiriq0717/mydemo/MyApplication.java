package com.example.meiriq0717.mydemo;

import android.app.Application;
import android.content.Context;
import android.os.Environment;

import java.io.File;

import in.srain.cube.Cube;
import in.srain.cube.image.ImageLoader;
import in.srain.cube.image.ImageLoaderFactory;
import in.srain.cube.image.impl.DefaultImageLoadHandler;

/**
 * Created by meiriq0717 on 2015/10/14.
 */
public class MyApplication extends Application {
    private MyApplication instance;
    @Override
    public void onCreate() {
        instance=this;
        Cube.onCreate(instance);
        initImageLoder();
        super.onCreate();
    }
    public void initImageLoder(){
        File file= Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
        ImageLoaderFactory.customizeCache(this,1024*10,file.getAbsolutePath(),10*1204);
        DefaultImageLoadHandler handler=new DefaultImageLoadHandler(this);
        ImageLoaderFactory.setDefaultImageLoadHandler(handler);
    }
}
