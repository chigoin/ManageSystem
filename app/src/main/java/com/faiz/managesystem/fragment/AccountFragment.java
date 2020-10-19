package com.faiz.managesystem.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;

import com.faiz.managesystem.R;
import com.faiz.managesystem.util.ProgressDialogUtil;
import com.google.android.material.button.MaterialButton;

public class AccountFragment extends BaseFragment {

    private MaterialButton btnLogout;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.fragment_account, container, false);
        btnLogout = (MaterialButton) view.findViewById(R.id.btn_logout);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        btnLogout.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                ProgressDialogUtil.showProgressDialog(getContext());
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            Thread.sleep(3000);
                        }catch (InterruptedException e){
                            e.printStackTrace();
                        }
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                ProgressDialogUtil.closeProgressDialog();
                            }
                        });
                    }
                }).start();
            }
        });
    }
}
