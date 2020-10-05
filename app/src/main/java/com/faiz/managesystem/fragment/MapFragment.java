package com.faiz.managesystem.fragment;


import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;

import com.baidu.mapapi.map.BaiduMap;

import com.baidu.mapapi.map.MapView;

import com.faiz.managesystem.R;

import java.util.ArrayList;
import java.util.List;


public class MapFragment extends BaseFragment {

public LocationClient mLocationClient;
private TextView tvPosition;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        mLocationClient = new LocationClient(getContext().getApplicationContext());
        mLocationClient.registerLocationListener(new MyLocationListener());
        View view = inflater.inflate(R.layout.fragment_map, container, false);
        tvPosition = view.findViewById(R.id.tv_position);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        checkPermission();
    }

    private void checkPermission() {
        List<String> permissonList = new ArrayList<>();
        if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.
                ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            permissonList.add(Manifest.permission.ACCESS_FINE_LOCATION);
        }
        if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.
                READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
            permissonList.add(Manifest.permission.READ_PHONE_STATE);
        }
        if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.
                WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            permissonList.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
        }
        if (!permissonList.isEmpty()) {
            String[] permissons = permissonList.toArray(new String[permissonList.size()]);
            requestPermissions(permissons, 1);
        } else {
            requestLocation();
        }
    }

//    请求位置信息
    private void requestLocation(){
        mLocationClient.start();
    }

//    实现百度地图的位置监听器
    public class MyLocationListener implements BDLocationListener{
        @Override
        public void onReceiveLocation(BDLocation bdLocation) {
            StringBuilder currentPositon = new StringBuilder();
            currentPositon.append("纬度：").append(bdLocation.getLatitude()).append("\n");
            currentPositon.append("经度：").append(bdLocation.getLongitude()).append("\n");
//            Toast.makeText(MainActivity.this,,Toast.LENGTH_SHORT).show();
            currentPositon.append("国家：").append(bdLocation.getCountry()).append("\n");
            currentPositon.append("省：").append(bdLocation.getProvince()).append("\n");
            currentPositon.append("市：").append(bdLocation.getCity()).append("\n");
            currentPositon.append("区").append(bdLocation.getDistrict()).append("\n");
            currentPositon.append("街道").append(bdLocation.getStreet()).append("\n");
            currentPositon.append("定位方式：");
            if (bdLocation.getLocType() == BDLocation.TypeGpsLocation) {
                currentPositon.append("GPS");
            } else if (bdLocation.getLocType() == BDLocation.TypeNetWorkLocation) {
                currentPositon.append("网络");
            }
//            Toast.makeText(MainActivity.this, currentPositon.toString(), Toast.LENGTH_SHORT).show();
            tvPosition.setText(currentPositon.toString());
        }
    }


}
