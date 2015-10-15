package com.example.meiriq0717.mydemo;


import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import in.srain.cube.views.list.ListViewDataAdapterBase;
import in.srain.cube.views.list.ViewHolderCreator;

/**
 * Created by meiriq0717 on 2015/10/14.
 */
public class MAdapter extends ListViewDataAdapterBase {
    public MAdapter(ViewHolderCreator viewHolderCreator) {
        super(viewHolderCreator);
    }
    public MAdapter() {
        super();
    }

    private List<Map<String, String>> list = new ArrayList<Map<String, String>>();
    @Override
    public Object getItem(int i) {
        return list.get(i);
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public long getItemId(int position) {
        return (long)position;
    }

    public void setList(List<Map<String, String>> list) {
        this.list = list;
        notifyDataSetChanged();
    }
}
