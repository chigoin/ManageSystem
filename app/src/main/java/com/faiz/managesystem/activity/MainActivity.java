package com.faiz.managesystem.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;


import android.os.Bundle;
import android.view.MenuItem;

import com.baidu.mapapi.SDKInitializer;
import com.faiz.managesystem.R;
import com.faiz.managesystem.adapter.ViewPagerAdapter;
import com.faiz.managesystem.fragment.BaseFragment;
import com.faiz.managesystem.fragment.MapFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;


/**
 * The type Main activity.
 * 主活动，展示主要的功能业面
 */
public class MainActivity extends AppCompatActivity {

    //    tab栏控件
    private ViewPager viewPager;
    private MenuItem menuItem;
    private BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initBottomNavigator();


    }

    //    初始化底部tab栏
    private void initBottomNavigator() {
        viewPager = (ViewPager) findViewById(R.id.view_pager);
        bottomNavigationView = (BottomNavigationView) findViewById(R.id.bnv);
        bottomNavigationView.setOnNavigationItemSelectedListener(
                new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.tab_one:
                                viewPager.setCurrentItem(0);
                                break;
                            case R.id.tab_two:
                                viewPager.setCurrentItem(1);
                                break;
                            case R.id.tab_three:
                                viewPager.setCurrentItem(2);
                                break;
                        }
                        return false;
                    }
                });
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (menuItem != null) {
                    menuItem.setChecked(false);
                } else {
                    bottomNavigationView.getMenu().getItem(0).setChecked(false);
                }
                menuItem = bottomNavigationView.getMenu().getItem(position);
                menuItem.setChecked(true);
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });

        setupViewPager(viewPager);
    }

    //    为ViewPager添加适配器
    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        MapFragment mapFragment = new MapFragment();
        adapter.addFragment(mapFragment);
        adapter.addFragment(BaseFragment.newInstance("设备列表"));
        adapter.addFragment(BaseFragment.newInstance("我"));
        viewPager.setAdapter(adapter);
    }

}
