package com.example.meiriq0717.mydemo;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import in.srain.cube.image.CubeImageView;
import in.srain.cube.image.ImageLoader;
import in.srain.cube.image.ImageLoaderFactory;
import in.srain.cube.image.ImageTask;
import in.srain.cube.image.iface.ImageLoadHandler;
import in.srain.cube.image.impl.DefaultImageLoadHandler;
import in.srain.cube.util.LocalDisplay;

/**
 * Created by meiriq0717 on 2015/10/14.
 */
public class MyAdapter extends BaseAdapter {
    private List<Map<String, String>> list = new ArrayList<Map<String, String>>();
    private ImageLoader mImageLoader;
    private Context mContext;
    private DefaultImageLoadHandler imageLoadHandler;
    private ImageLoadHandler mImageLoadHandler;
    public MyAdapter(List<Map<String, String>> list, Context context) {
        this.list = list;
        mContext = context;
        imageLoadHandler = new DefaultImageLoadHandler(context);
        imageLoadHandler.setLoadingImageColor(Color.parseColor("#84ffff"));
        imageLoadHandler.setLoadingResources(R.mipmap.ic_launcher);
        imageLoadHandler.setImageRounded(true, LocalDisplay.dp2px(50));
        imageLoadHandler.setImageFadeIn(true);
        imageLoadHandler.setResizeImageViewAfterLoad(false);
        mImageLoader = ImageLoaderFactory.create(context,imageLoadHandler);
        mImageLoadHandler=new ImageLoadHandler() {
            @Override
            public void onLoading(ImageTask imageTask, CubeImageView cubeImageView) {

            }

            @Override
            public void onLoadFinish(ImageTask imageTask, CubeImageView cubeImageView, BitmapDrawable bitmapDrawable) {

            }

            @Override
            public void onLoadError(ImageTask imageTask, CubeImageView cubeImageView, int i) {

            }
        };
        notifyDataSetChanged();
    }
    //备用方法
    public void setList(List<Map<String, String>> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_layout, null);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.cube_iv = (CubeImageView) convertView.findViewById(R.id.cube_iv);
        viewHolder.iv_title = (TextView) convertView.findViewById(R.id.tv_title);
        viewHolder.iv_title.setText(list.get(position).get("title"));
        viewHolder.cube_iv.loadImage(mImageLoader, list.get(position).get("image_url"));
        return convertView;
    }

    public class ViewHolder {
        private CubeImageView cube_iv;
        private TextView iv_title;
    }
}
