package com.faiz.managesystem.adapter;

import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SortedListAdapterCallback;

import com.faiz.managesystem.model.Province;


public class MySortedListCallback extends SortedListAdapterCallback<Province> {

    public MySortedListCallback(RecyclerView.Adapter adapter) {
        super(adapter);
    }


    @Override
    public int compare(Province o1, Province o2) {
        return o1.getNameIndex().compareTo(o2.getNameIndex());
    }

    @Override
    public boolean areContentsTheSame(Province oldItem, Province newItem) {
        if (!oldItem.getNameIndex().equals(newItem.getNameIndex())) {
            return false;
        }
        if (!oldItem.getProvinceName().equals(newItem.getProvinceName())) {
            return false;
        }
        return true;
    }

    @Override
    public boolean areItemsTheSame(Province oldItem, Province newItem) {
        return oldItem.getProvinceName().equals(newItem.getProvinceName());
    }
}
