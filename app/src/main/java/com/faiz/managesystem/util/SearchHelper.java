package com.faiz.managesystem.util;

import android.widget.Filter;


import com.faiz.managesystem.model.Province;

import org.litepal.LitePal;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;


//用于实现搜索功能
public class SearchHelper {

    private static List<Province> provinceWrappers = new ArrayList<>();
    private static List<Province> provinceSuggestions = new ArrayList<>(Arrays.asList(
            new Province("云南",true),
            new Province("贵州",true),
            new Province("四川",true),
            new Province("湖北",true),
            new Province("广东"),
            new Province("江苏"),
            new Province("安徽"),
            new Province("云北"),
            new Province("云和")
    ));

    private static void initProvinceWrappers() {
        provinceWrappers = LitePal.findAll(Province.class);
    }

    public interface OnFindProvinceListener {
        void onResult(List<Province> results);
    }

    public interface OnFindSuggestionListener {
        void onResult(List<Province> results);
    }


    /**
     * Find suggestions.
     * 用于查找推介
     *
     * @param query    the query
     * @param limit    the limit
     * @param delay    the delay
     * @param listener the listener
     */
    public static void findSuggestions(String query, final int limit, long delay, final OnFindSuggestionListener listener) {
        new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {

//    重置所有的建议项的isHistory属性为false
//                SearchHelper.resetSuggestionsHistory();
                List<Province> suggestionList = new ArrayList<>();
                if (!(constraint == null && constraint.length() == 0)) {
                    for (Province suggestion : provinceSuggestions) {
                        if (suggestion.getProvinceName().startsWith(constraint.toString())) {
                            suggestionList.add(suggestion);
                            if (suggestionList.size() == limit) {
                                break;
                            }
                        }
                    }
                }
                FilterResults results = new FilterResults();
//                排序的意义在哪？
                Collections.sort(suggestionList, new Comparator<Province>() {
                    @Override
                    public int compare(Province lhs, Province rhs) {
                        return lhs.getNameIndex().compareTo(rhs.getNameIndex());
                    }
                });
                results.values = suggestionList;
                results.count = suggestionList.size();
                return results;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {

                if (listener != null) {
                    listener.onResult((List<Province>) results.values);
                }
            }
        }.filter(query);
    }


    /**
     * Find province.
     * 模糊查找搜索项
     *
     * @param query    the query
     * @param listener the listener
     */
    public static void findProvince(String query,final OnFindProvinceListener listener) {
        initProvinceWrappers();
        new Filter() {

            @Override
            protected FilterResults performFiltering(CharSequence constraint) {


                List<Province> provinceList = new ArrayList<>();

                if (!(constraint == null && constraint.length() == 0)) {

                    for (Province province : provinceWrappers) {
                        if (province.getProvinceName()
                                .startsWith(constraint.toString())) {

                            provinceList.add(province);
                        }
                    }

                }

                FilterResults results = new FilterResults();
                results.values = provinceList;
                results.count = provinceList.size();

                return results;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {

                if (listener != null) {
                    listener.onResult((List<Province>) results.values);
                }
            }
        }.filter(query);
    }

    /**
     * Reset suggestions history.
     */
    public static void resetSuggestionsHistory() {
        for (Province suggestion : provinceSuggestions) {
            suggestion.setHistory(false);
        }
    }

    /**
     * Gets history.
     *这里就是瞎搞的，以后还需要进行一些改进
     * @param count the count
     * @return the history
     */
    public static List<Province> getHistory(int count) {

        List<Province> historyList = new ArrayList<>();
        for (Province province : provinceSuggestions) {
           if (province.isHistory()){
               historyList.add(province);
           }
            if (historyList.size() == count) {
                break;
            }
        }
        return historyList;
    }


}
