package com.faiz.managesystem.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.core.content.res.ResourcesCompat;

import com.arlib.floatingsearchview.FloatingSearchView;
import com.arlib.floatingsearchview.suggestions.SearchSuggestionsAdapter;
import com.arlib.floatingsearchview.suggestions.model.SearchSuggestion;
import com.arlib.floatingsearchview.util.Util;
import com.faiz.managesystem.R;
import com.faiz.managesystem.model.Province;
import com.faiz.managesystem.util.SearchHelper;

import java.util.List;


public class SearchFragment extends BaseFragment {

    private FloatingSearchView searchView;

    private String lastQuery;

    private boolean mIsDarkSearchTheme = false;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search, container, false);
        searchView = (FloatingSearchView) view.findViewById(R.id.floating_search_view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setupFloatingSearch();
    }


  private void setupFloatingSearch() {
//    为搜索框添加文本该改变监听事件
        searchView.setOnQueryChangeListener(new FloatingSearchView.OnQueryChangeListener() {
            @Override
            public void onSearchTextChanged(String oldQuery, final String newQuery) {
                if (!oldQuery.equals("") && newQuery.equals("")) {
                    searchView.clearSuggestions();
                } else {
//                    searchView.showProgress();
//                    Toast.makeText(SearchActivity.this,"ok",Toast.LENGTH_SHORT).show();
                    SearchHelper.findProvince(newQuery, new SearchHelper.OnFindProvinceListener() {
                        @Override
                        public void onResult(List<Province> results) {
                            searchView.swapSuggestions(results);
                        }
                    });
                }
            }
        });


//        用来处理点击事件
        searchView.setOnSearchListener(new FloatingSearchView.OnSearchListener() {
            @Override
            public void onSuggestionClicked(final SearchSuggestion searchSuggestion) {
                Province province = new Province();
                province.setHistory(true);
                province.updateAll("provinceName = ?", searchSuggestion.getBody());
                Toast.makeText(getContext(), searchSuggestion.getBody(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onSearchAction(String query) {
                lastQuery = query;
                Toast.makeText(getContext(), "onSuggestionClicked", Toast.LENGTH_SHORT).show();
            }
        });


        //  用来处理搜索栏获得与取消焦点的事件监听
        searchView.setOnFocusChangeListener(new FloatingSearchView.OnFocusChangeListener() {
            @Override
            public void onFocus() {
                searchView.swapSuggestions(SearchHelper.getHistory(3));

            }

            @Override
            public void onFocusCleared() {
//                searchView.setSearchBarTitle(lastQuery);
//                你也可以将已经打上的搜索字符保存，以致在下一次点击的时候，搜索栏内还保存着之前输入的字符
//                mSearchView.setSearchText(searchSuggestion.getBody());

            }
        });

//        自定义设置
        searchView.setOnBindSuggestionCallback(new SearchSuggestionsAdapter.OnBindSuggestionCallback() {
            @Override
            public void onBindSuggestion(View suggestionView, ImageView leftIcon,
                                         TextView textView, SearchSuggestion item, int itemPosition) {
                Province provinceSuggestion = (Province) item;

                String textColor = mIsDarkSearchTheme ? "#ffffff" : "#787878";
                String textLight = mIsDarkSearchTheme ? "#bfbfbf" : "#03A9F4";

                if (provinceSuggestion.isHistory()) {
                    leftIcon.setImageDrawable(ResourcesCompat.getDrawable(getResources(),
                            R.drawable.ic_history_black_24dp, null));

                    Util.setIconColor(leftIcon, Color.parseColor(textColor));
                    leftIcon.setAlpha(.36f);
                } else {
                    leftIcon.setAlpha(0.0f);
                    leftIcon.setImageDrawable(null);
                }

                textView.setTextColor(Color.parseColor(textColor));
                String text = provinceSuggestion.getBody()
                        .replaceFirst(searchView.getQuery(),
                                "<font color=\"" + textLight + "\">" + searchView.getQuery() + "</font>");
                textView.setText(Html.fromHtml(text));
            }

        });
    }

}
