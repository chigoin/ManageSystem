package com.faiz.managesystem.fragment;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SortedList;

import com.faiz.managesystem.R;
import com.faiz.managesystem.adapter.MyRecyclerViewAdapter;
import com.faiz.managesystem.adapter.MySortedListCallback;
import com.faiz.managesystem.model.Province;
import com.faiz.managesystem.util.HttpUtil;
import com.faiz.managesystem.util.ResponseUtil;
import com.gjiazhe.wavesidebar.WaveSideBar;

import org.jetbrains.annotations.NotNull;
import org.litepal.LitePal;

import java.io.IOException;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class DeviceListFragment extends BaseFragment {

    private Toast toast;
    private RecyclerView recyclerView;
    private WaveSideBar waveSideBar;
    private Context context;
    //   用于绑定显示在ecyclerView上的数据
    private SortedList<Province> provinces;
    //    用于批量获取从数据库中得到的数据
    private List<Province> provinceList;
    //    progressDialog
    private ProgressDialog progressDialog;

    private MyRecyclerViewAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_device_list,container,false);
        //        创建本地数据库
        LitePal.getDatabase();
//        LitePal.deleteAll(Province.class);
        context = getContext();
        toast = Toast.makeText(context, "", Toast.LENGTH_SHORT);
        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        waveSideBar = (WaveSideBar) view.findViewById(R.id.waveSideBar);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initRecyclerView();
        initWaveSideBar();
    }


    private void initWaveSideBar() {
        waveSideBar.setTextColor(Color.BLACK);
        waveSideBar.setMaxOffset(200);//字母偏移量
        waveSideBar.setPosition(WaveSideBar.POSITION_RIGHT);//侧边栏在左边还是右边
        waveSideBar.setLazyRespond(false);//false:列表随侧边栏的滚动滚动

        waveSideBar.setOnSelectIndexItemListener(new WaveSideBar.OnSelectIndexItemListener() {
            @Override
            public void onSelectIndexItem(String index) {
//                toast.setText(index);
//                toast.show();
                for (int i = 0; i < provinces.size(); i++) {
                    if (index.equals(provinces.get(i).getNameIndex())) {
                        //ecyclerView.scrollToPosition(i);
                        //recyclerView.smoothScrollToPosition(i);
                        //或者
                        ((LinearLayoutManager) recyclerView.getLayoutManager()).scrollToPositionWithOffset(i, 0);
                        return;
                    }
                }
            }
        });
    }

    private void initRecyclerView() {
        initData();
        recyclerView.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new DividerItemDecoration(context, DividerItemDecoration.VERTICAL));

        queryProvinces();

        adapter.setOnItemClickListener(new MyRecyclerViewAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                toast.setText(provinces.get(position).getProvinceName());
                toast.show();
            }
        });
    }

    private void initData() {

    }

    private void queryProvinces() {
//        recyclerView.setVisibility(View.GONE);
        provinceList = LitePal.findAll(Province.class);
        adapter = new MyRecyclerViewAdapter(null);
        recyclerView.setAdapter(adapter);
        MySortedListCallback callback = new MySortedListCallback(adapter);
        provinces = new SortedList<>(Province.class, callback);
        if (provinceList.size() > 0) {
            provinces.clear();
            for (Province province : provinceList) {
                provinces.add(province);
            }
            adapter.setData(provinces);
        } else {
            String address = "http://guolin.tech/api/china";
//            TODO:从服务器查询数据
            queryFromServer(address);
        }
    }

    private void queryFromServer(String address) {
        showProgressDialog();
        HttpUtil.sendOkHttpRequest(address, new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        closeProgressDialog();
                        Toast.makeText(context, "加载失败", Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                String respondText = response.body().string();
                boolean result = ResponseUtil.handleProvinceResponse(respondText);
                if (result) {
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            queryProvinces();
                            closeProgressDialog();
                        }
                    });
                } else {
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            toast.setText("加载失败，未知错误！");
                            toast.show();
                        }
                    });
                }
            }
        });
    }
    //    TODO:定制一个progressDialog
    private void showProgressDialog() {
        if (progressDialog == null) {
            progressDialog = new ProgressDialog(context);
            progressDialog.setMessage("正在加载...");
            progressDialog.setCanceledOnTouchOutside(false);
        }
        progressDialog.show();
    }

    private void closeProgressDialog() {
        if (progressDialog != null) {
            progressDialog.dismiss();
        }
    }


}
