package com.example.meiriq0717.mydemo;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;
import java.util.Map;

import in.srain.cube.image.CubeImageView;
import in.srain.cube.image.ImageLoader;
import in.srain.cube.views.list.ViewHolderBase;

/**
 * Created by meiriq0717 on 2015/10/14.
 */
public class MViewHolder extends ViewHolderBase<Map<String,String>> {
    private CubeImageView cubeIv;
    private TextView imageTitle;
    private ImageLoader mImageLoader;

    public MViewHolder(ImageLoader imageLoader) {
        mImageLoader = imageLoader;
    }

    @Override
    public View createView(LayoutInflater layoutInflater) {
        View v = layoutInflater.inflate(R.layout.item_layout, null);
        cubeIv = (CubeImageView) v.findViewById(R.id.cube_iv);
        imageTitle = (TextView) v.findViewById(R.id.tv_title);
        return v;
    }
    @Override
    public void showData(int i, Map<String,String> map) {
        Log.i("showData", map.toString());
        cubeIv.setScaleType(ImageView.ScaleType.CENTER_CROP);
        imageTitle.setText(map.get("title"));
        cubeIv.loadImage(mImageLoader, map.get("image_url"));
    }
}
