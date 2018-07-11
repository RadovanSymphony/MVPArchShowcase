package com.mg.kode.kodebrowser.ui.home.search.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.mg.kode.kodebrowser.R;
import com.mg.kode.kodebrowser.data.model.History;
import com.mg.kode.kodebrowser.ui.base.ItemClickedListener;
import com.squareup.picasso.Picasso;

import java.util.List;


public class SearchHistoryAdapter extends RecyclerView.Adapter<SearchHistoryAdapter.SearchHistoryViewHolder> {

    List<History> mData;
    private ItemClickedListener mListener;

    @Override
    public SearchHistoryViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new SearchHistoryViewHolder(
                LayoutInflater.from(parent.getContext()).inflate(R.layout.search_history_item, parent, false));
    }

    @Override
    public void onBindViewHolder(SearchHistoryViewHolder holder, int position) {
        History history = mData.get(position);
        holder.tvTitle.setText(history.getTitle());
        holder.tvUrl.setText(history.getUrl());
        Picasso.get()
                .load(history.getWebPage().getFavicon())
                .into(holder.ivIcon);
    }

    @Override
    public int getItemCount() {
        return mData == null ? 0 : mData.size();
    }

    public void setData(List<History> data) {
        mData = data;
        notifyDataSetChanged();
    }

    public void setItemClickListener(ItemClickedListener listener) {
        mListener = listener;
    }

    class SearchHistoryViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView ivIcon;
        TextView tvTitle;
        TextView tvUrl;

        public SearchHistoryViewHolder(View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tv_search_history_title);
            tvUrl = itemView.findViewById(R.id.tv_search_history_link);
            ivIcon = itemView.findViewById(R.id.iv_search_history_icon);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if(mListener != null) {
                mListener.onItemClicked(mData.get(getAdapterPosition()));
            }
        }
    }
}
