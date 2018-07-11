package com.mg.kode.kodebrowser.ui.searchengine;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.mg.kode.kodebrowser.R;
import com.mg.kode.kodebrowser.data.model.SearchEngine;
import com.mg.kode.kodebrowser.di.components.ActivityComponent;
import com.mg.kode.kodebrowser.ui.base.BaseActivity;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;


public class SearchEngineSettingsActivity extends BaseActivity implements SearchEngineScreenContract.SearchEngineSettingsView {

    @Inject
    SearchEngineScreenContract.SearchEngineSettingsPresenter<SearchEngineScreenContract.SearchEngineSettingsView> mPresenter;

    @BindView(R.id.rv_search_engine_list)
    RecyclerView mList;

    SearchEngineAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ActivityComponent component = getActivityComponent();
        if (component != null) {
            component.inject(this);
            setUnBinder(ButterKnife.bind(this));
            mAdapter = new SearchEngineAdapter();
            mList.setAdapter(mAdapter);
            mList.setLayoutManager(new LinearLayoutManager(this));
            mPresenter.onAttach(this);
        }
    }

    @Override
    public void setSearchEngineList(List<SearchEngine> engines) {
        mAdapter.setData(engines);
    }

    @Override
    protected void onDestroy() {
        mPresenter.onDetach();
        super.onDestroy();
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_search_engine_settings;
    }
}
