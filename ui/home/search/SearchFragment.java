package com.mg.kode.kodebrowser.ui.home.search;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.mg.kode.kodebrowser.R;
import com.mg.kode.kodebrowser.data.model.History;
import com.mg.kode.kodebrowser.data.model.SearchEngine;
import com.mg.kode.kodebrowser.di.components.ActivityComponent;
import com.mg.kode.kodebrowser.ui.base.BaseFragment;
import com.mg.kode.kodebrowser.ui.base.ItemClickedListener;
import com.mg.kode.kodebrowser.ui.home.search.adapter.SearchHistoryAdapter;
import com.mg.kode.kodebrowser.ui.home.search.adapter.SearchSuggestionsAdapter;
import com.mg.kode.kodebrowser.utils.UIUtils;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SearchFragment extends BaseFragment implements SearchScreenContract.SearchView, View.OnClickListener {


    public interface SearchFragmentCallback {
        void onSearchHelperTextEntered(String helperText);
    }

    @Inject
    SearchScreenContract.SearchPresenter<SearchScreenContract.SearchView> mPresenter;

    @BindView(R.id.rv_search_suggestions_list)
    RecyclerView mSuggestions;
    SearchSuggestionsAdapter mSuggestionAdapter;
    @BindView(R.id.rv_search_history_list)
    RecyclerView mSearchHistory;
    SearchHistoryAdapter mHistoryAdapter;
    @BindView(R.id.tv_www)
    TextView mTvWWW;
    @BindView(R.id.tv_backslash)
    TextView mTvBackslash;
    @BindView(R.id.tv_dot)
    TextView mTvDot;
    @BindView(R.id.tv_minus)
    TextView mTvMinus;
    @BindView(R.id.tv_two_dots)
    TextView mTvTwoDots;
    @BindView(R.id.tv_com)
    TextView mTvCom;
    @BindView(R.id.divider)
    View mDivider;

    private ItemClickedListener mSuggestionClickedListener;
    private ItemClickedListener mHistoryClickedListener;
    private String mLastSearchQuery = "";
    private SearchFragmentCallback mSearchCallback;

    @SuppressLint("PrivateResource")
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.activity_search, container, false);
        ActivityComponent component = getActivityComponent();
        if (component != null) {
            component.inject(this);
            setUnBinder(ButterKnife.bind(this, root));
            initListeners();
            mSuggestionAdapter = new SearchSuggestionsAdapter();
            mSuggestionAdapter.setItemClickListener(mSuggestionClickedListener);
            mSuggestions.setLayoutManager(new LinearLayoutManager(getContext()));
            mSuggestions.setAdapter(mSuggestionAdapter);
            mSuggestions.setNestedScrollingEnabled(false);
            mHistoryAdapter = new SearchHistoryAdapter();
            mHistoryAdapter.setItemClickListener(mHistoryClickedListener);
            mSearchHistory.setLayoutManager(new LinearLayoutManager(getContext()));
            mSearchHistory.setAdapter(mHistoryAdapter);
            mSearchHistory.setNestedScrollingEnabled(false);
            mTvWWW.setOnClickListener(this);
            mTvDot.setOnClickListener(this);
            mTvTwoDots.setOnClickListener(this);
            mTvBackslash.setOnClickListener(this);
            mTvMinus.setOnClickListener(this);
            mTvCom.setOnClickListener(this);
            UIUtils.hideViews(mDivider);
            mPresenter.onAttach(this);
        }

        return root;
    }

    private void initListeners() {
        mSuggestionClickedListener = item -> {
            mPresenter.onSearchQuerySubmit((String) item);
        };

        mHistoryClickedListener = history -> {
            mPresenter.onHistoryItemClicked((History) history);
        };
    }

    @Override
    public void setSearchSuggestions(List<String> suggestions) {
        mSuggestionAdapter.setData(suggestions, mLastSearchQuery);
    }

    @Override
    public void setSearchEngines(List<SearchEngine> engines) {
    }

    @Override
    public void setSearchHistory(List<History> history) {
        mHistoryAdapter.setData(history);
        if (history != null && history.size() > 0) {
            UIUtils.showViews(divider);
        } else {
            UIUtils.hideViews(divider);
        }
    }

    @Override
    public void clearLists() {
        mHistoryAdapter.setData(null);
        mSuggestionAdapter.setData(null);
        UIUtils.hideViews(divider);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_www:
            case R.id.tv_backslash:
            case R.id.tv_dot:
            case R.id.tv_two_dots:
            case R.id.tv_minus:
            case R.id.tv_com: {
                if (mSearchCallback != null) {
                    mSearchCallback.onSearchHelperTextEntered(((TextView) v).getText().toString());
                }
                break;
            }
        }
    }

    /**
     * Call when search query has changed and new search suggestions needs to be displayed according to entered param.
     *
     * @param query new search query entered by user.
     */
    public void searchQueryChanged(String query) {
        mPresenter.onSearchQueryTextTyped(query);
        mLastSearchQuery = query;
    }

    /**
     * Call when search query has been executed by the user.
     *
     * @param query executed search query.
     */
    public void onSearchQueryExecuted(String query) {
        mPresenter.onSearchQuerySubmit(query);
    }


    public void setSearchFragmentCallback(SearchFragmentCallback callback) {
        mSearchCallback = callback;
    }

    @Override
    public void onDestroy() {
        mSearchCallback = null;
        super.onDestroy();
    }
}
