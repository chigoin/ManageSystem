package com.faiz.managesystem.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SortedList;


import com.faiz.managesystem.R;
import com.faiz.managesystem.model.Province;


public class MyRecyclerViewAdapter extends RecyclerView.Adapter {

    private SortedList<Province> provinces;

    public MyRecyclerViewAdapter(SortedList<Province> provinces) {
        this.provinces = provinces;
    }

    public SortedList<Province> getData() {
        return provinces;
    }

    public void setData(SortedList<Province> provinces) {
        this.provinces = provinces;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//        View itemView = View.inflate(parent.getContext(), R.layout.item_device_list, null);
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_device_list,parent,false);
        return new MyHolder(itemView);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, final int position) {
        Province province = provinces.get(position);
        MyHolder holder = (MyHolder) viewHolder;
        //显示index
        if (position == 0 || !province.getNameIndex().equals(provinces.get(position - 1).getNameIndex())) {
            holder.tv_index.setVisibility(View.VISIBLE);
            holder.tv_index.setText(province.getNameIndex());
        } else {
            holder.tv_index.setVisibility(View.GONE);
        }

        holder.tv_name.setText(province.getProvinceName());

//        为RecyclerView的item添加点击事件（通过接口回调的方法）
        if (mOnItemClickListener != null) {
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mOnItemClickListener.onItemClick(view, position);
                }
            });
        }
//        为RecyclerVIew中的item上的图片添加点击事件
        if (mOnItemImageClickListener != null) {
            holder.iv_contact.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mOnItemImageClickListener.onItemClick(view, position);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return provinces == null ? 0 : provinces.size();
    }

    private class MyHolder extends RecyclerView.ViewHolder {

        TextView tv_index;
        TextView tv_name;
        ImageView iv_contact;
        View itemView;

        public MyHolder(View view) {
            super(view);
            itemView = view;
            tv_index = (TextView) view.findViewById(R.id.tv_index);
            tv_name = (TextView) view.findViewById(R.id.tv_name);
            iv_contact = (ImageView) view.findViewById(R.id.iv_contact);
        }
    }

    //设置列表项监听事件
    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }

    private OnItemClickListener mOnItemClickListener;

    public void setOnItemClickListener(OnItemClickListener mOnItemClickListener) {
        this.mOnItemClickListener = mOnItemClickListener;
    }

    public interface OnItemImageClickListener {
        void onItemImageClick(View view, int position);
    }

    private OnItemClickListener mOnItemImageClickListener;

    public void setOnItemImageCLickListener(OnItemImageClickListener mOnItemImageCLickListener) {
        this.mOnItemImageClickListener = mOnItemClickListener;
    }

}
