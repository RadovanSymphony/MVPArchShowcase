package com.mg.kode.kodebrowser.ui.home.search.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.mg.kode.kodebrowser.R;
import com.mg.kode.kodebrowser.data.model.SearchEngine;
import com.squareup.picasso.Picasso;

import java.util.List;


public class SearchEngineAdapter extends RecyclerView.Adapter<SearchEngineAdapter.SearchEngineViewHolder> {

    List<SearchEngine> mData;

    @Override
    public SearchEngineViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        return new SearchEngineViewHolder(inflater.inflate(R.layout.search_engine_item, parent, false));
    }

    @Override
    public void onBindViewHolder(SearchEngineViewHolder holder, int position) {
        Picasso.get()
                .load(mData.get(position).getWebpage().getFavicon())
                .fit().centerCrop()
                .into(holder.mView);
    }

    @Override
    public int getItemCount() {
        return mData == null ? 0 : mData.size();
    }

    public void setData(List<SearchEngine> data) {
        mData = data;
        notifyDataSetChanged();
    }

    class SearchEngineViewHolder extends RecyclerView.ViewHolder {

        ImageView mView;

        public SearchEngineViewHolder(View itemView) {
            super(itemView);
            mView = itemView.findViewById(R.id.iv_search_engine_icon);
        }
    }
}
