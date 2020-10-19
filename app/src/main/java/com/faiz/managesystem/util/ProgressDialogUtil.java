package com.faiz.managesystem.util;

import android.content.Context;

import com.faiz.managesystem.Dialog.CustomProgressDialog;
import com.faiz.managesystem.R;

public class ProgressDialogUtil {

    private static CustomProgressDialog progressDialog;

    public static void showProgressDialog(Context context){
        if (progressDialog == null) {
            progressDialog = new CustomProgressDialog(context, R.style.MyDialogStyle,"加载中...");
        }
        progressDialog.show();
    }

    public static void closeProgressDialog(){
        if (progressDialog != null) {
            progressDialog.dismiss();
        }
    }


}
