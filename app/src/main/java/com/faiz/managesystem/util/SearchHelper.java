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


    public interface OnFindProvinceListener {
        void onResult(List<Province> results);
    }

//    public interface OnFindSuggestionListener {
//        void onResult(List<Province> results);
//    }


    /**
     * Find suggestions.1111111111
     * 用于查找推介
     *
     * @param query    the query
     * @param limit    the limit
     * @param delay    the delay
     * @param listener the listener
     */
//    public static void findSuggestions(String query, final int limit, long delay, final OnFindSuggestionListener listener) {
//        new Filter() {
//            @Override
//            protected FilterResults performFiltering(CharSequence constraint) {
//
////    重置所有的建议项的isHistory属性为false
////                SearchHelper.resetSuggestionsHistory();
//                List<Province> suggestionList = new ArrayList<>();
//                if (!(constraint == null && constraint.length() == 0)) {
//                    for (Province suggestion : provinceSuggestions) {
//                        if (suggestion.getProvinceName().startsWith(constraint.toString())) {
//                            suggestionList.add(suggestion);
//                            if (suggestionList.size() == limit) {
//                                break;
//                            }
//                        }
//                    }
//                }
//                FilterResults results = new FilterResults();
////                排序的意义在哪？
//                Collections.sort(suggestionList, new Comparator<Province>() {
//                    @Override
//                    public int compare(Province lhs, Province rhs) {
//                        return lhs.getNameIndex().compareTo(rhs.getNameIndex());
//                    }
//                });
//                results.values = suggestionList;
//                results.count = suggestionList.size();
//                return results;
//            }
//
//            @Override
//            protected void publishResults(CharSequence constraint, FilterResults results) {
//
//                if (listener != null) {
//                    listener.onResult((List<Province>) results.values);
//                }
//            }
//        }.filter(query);
//    }


    /**
     * Find province.
     * 模糊查找搜索项
     *
     * @param query    the query
     * @param listener the listener
     */
    public static void findProvince(String query,final OnFindProvinceListener listener) {
        new Filter() {

            @Override
            protected FilterResults performFiltering(CharSequence constraint) {


                List<Province> searchResultList = null;

                if (!(constraint == null && constraint.length() == 0)) {

                    searchResultList = LitePal.where("provinceName like ?",
                            "%" + constraint.toString() + "%").find(Province.class);

                }

                FilterResults results = new FilterResults();
                results.values = searchResultList;
                results.count = searchResultList.size();

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
//    public static void resetSuggestionsHistory() {
//        for (Province suggestion : provinceSuggestions) {
//            suggestion.setHistory(false);
//        }
//    }

    /**
     * Gets history.
     *这里就是瞎搞的，以后还需要进行一些改进
     * @param count the count
     * @return the history
     */
    public static List<Province> getHistory(int count) {

        List<Province> historyList = new ArrayList<>();
        List<Province> provinceList = LitePal.where("isHistory = ?", "1").find(Province.class);
        for (Province province : provinceList) {
            if (province.isHistory()) {
                historyList.add(province);
            }
            if (historyList.size() == count) {
                break;
            }
        }
        return historyList;
    }


}
