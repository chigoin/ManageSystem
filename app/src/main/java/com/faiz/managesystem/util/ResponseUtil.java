package com.faiz.managesystem.util;

import android.text.TextUtils;


import com.faiz.managesystem.model.Province;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * The type Response util.
 * 对从服务器请求来的JSON数据进行处理
 */
public class ResponseUtil {

    public static boolean handleProvinceResponse(String response){

        if (!TextUtils.isEmpty(response)){
            try{
                JSONArray allProvince = new JSONArray(response);
                for (int i= 0;i<allProvince.length();i++){
                    JSONObject provinceJSONObject = allProvince.getJSONObject(i);
                    Province province = new Province();
                    province.setProvinceName(provinceJSONObject.getString("name"));
                    province.setNameIndex(HanziToPinyin.toPinYin(provinceJSONObject.getString("name")));
                    province.save();
                }
                return true;
            }catch (JSONException e){
                e.printStackTrace();
            }
        }
        return false;
    }
}
