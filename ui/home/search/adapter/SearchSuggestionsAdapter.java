package com.mg.kode.kodebrowser.ui.home.search.adapter;

import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableString;
import android.text.style.StyleSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mg.kode.kodebrowser.R;
import com.mg.kode.kodebrowser.ui.base.ItemClickedListener;

import java.util.List;


public class SearchSuggestionsAdapter extends RecyclerView.Adapter<SearchSuggestionsAdapter.SuggestionViewHolder> {

    List<String> mData;
    private String mHighlighted;
    private ItemClickedListener mListener;

    @Override
    public SuggestionViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        TextView mText = (TextView) LayoutInflater.from(parent.getContext()).inflate(R.layout.search_suggestion_item, parent, false);
        return new SuggestionViewHolder(mText);
    }

    @Override
    public void onBindViewHolder(SuggestionViewHolder holder, int position) {
        String text = mData.get(position);
        SpannableString spannablecontent = new SpannableString(text);
        if (mHighlighted != null && !mHighlighted.isEmpty()) {
            int index = text.indexOf(mHighlighted);
            if (index != -1) {
                spannablecontent.setSpan(new StyleSpan(Typeface.BOLD),
                        index, index + mHighlighted.length(), 0);
            }
        }
        holder.mText.setText(spannablecontent);
    }

    @Override
    public int getItemCount() {
        return mData == null ? 0 : mData.size();
    }

    public void setData(List<String> data, String wordToHighlight) {
        mData = data;
        notifyDataSetChanged();
        mHighlighted = wordToHighlight;
    }

    public void setData(List<String> data) {
    	setData(data, "");
    }

    public void setItemClickListener(ItemClickedListener listener) {
        mListener = listener;
    }


    class SuggestionViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView mText;

        public SuggestionViewHolder(TextView item) {
            super(item);
            mText = item;
            mText.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (mListener != null) {
                mListener.onItemClicked(mData.get(getAdapterPosition()));
            }
        }
    }
}
