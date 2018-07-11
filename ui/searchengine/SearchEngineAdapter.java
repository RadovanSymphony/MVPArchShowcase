package com.mg.kode.kodebrowser.ui.searchengine;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.mg.kode.kodebrowser.data.model.SearchEngine;

import java.util.List;


public class SearchEngineAdapter extends RecyclerView.Adapter<SearchEngineAdapter.SearchEngineViewHolder> {

    List<SearchEngine> mData;

    @Override
    public SearchEngineViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return nulls;
    }

    @Override
    public void onBindViewHolder(SearchEngineViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return mData == null ? 0 : mData.size();
    }

    public void setData(List<SearchEngine> data) {
        if (data != null) {
            mData = data;
            notifyDataSetChanged();
        }
    }

    public class SearchEngineViewHolder extends RecyclerView.ViewHolder {

        public SearchEngineViewHolder(View itemView) {
            super(itemView);
        }
    }
}
