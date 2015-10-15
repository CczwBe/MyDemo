package com.example.meiriq0717.mydemo;

import android.view.LayoutInflater;
import android.view.View;

import java.util.List;

import in.srain.cube.views.list.ViewHolderBase;

/**
 * Created by meiriq0717 on 2015/10/14.
 */
public class ViewHolder extends ViewHolderBase<List<String>> {
    @Override
    public View createView(LayoutInflater layoutInflater) {
        return null;
    }

    @Override
    public void showData(int i, List<String> list) {

    }

    @Override
    public void setItemData(int position, View view) {
        super.setItemData(position, view);
    }
}
