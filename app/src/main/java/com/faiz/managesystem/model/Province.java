package com.faiz.managesystem.model;


import android.os.Parcel;

import com.arlib.floatingsearchview.suggestions.model.SearchSuggestion;
import com.faiz.managesystem.util.HanziToPinyin;

import org.litepal.crud.LitePalSupport;

public class Province extends LitePalSupport implements SearchSuggestion {

    private int id;
    //    即省名的拼音的第一个字母索引
    private String nameIndex;
    private String provinceName;
    private boolean isHistory = false;

    public boolean isHistory() {
        return isHistory;
    }

    public void setHistory(boolean history) {
        this.isHistory = history;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNameIndex() {
        return nameIndex;
    }

    public void setNameIndex(String nameIndex) {
        this.nameIndex = nameIndex;
    }

    public String getProvinceName() {
        return provinceName;
    }

    public void setProvinceName(String provinceName) {
        this.provinceName = provinceName;
    }

    public Province() {
    }

    public Province(String provinceName) {
        this.provinceName = provinceName;
        this.nameIndex = HanziToPinyin.toPinYin(provinceName);
    }

    public Province(String provinceName, boolean isHistory){
        this.provinceName = provinceName;
        this.nameIndex = HanziToPinyin.toPinYin(provinceName);
        this.isHistory = isHistory;
    }

    public Province(Parcel source) {
        this.provinceName = source.readString();
        this.nameIndex = source.readString();
        this.isHistory = source.readInt() != 0;
    }

    @Override
    public String getBody() {
        return provinceName;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(provinceName);
        parcel.writeString(nameIndex);
        parcel.writeInt(isHistory ? 1 : 0);
    }

    public static final Creator<Province> CREATOR = new Creator<Province>() {
        @Override
        public Province createFromParcel(Parcel in) {
            return new Province(in);
        }

        @Override
        public Province[] newArray(int size) {
            return new Province[size];
        }
    };
}
