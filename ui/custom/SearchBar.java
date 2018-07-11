package com.mg.kode.kodebrowser.ui.custom;

import android.content.Context;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mg.kode.kodebrowser.R;


public class SearchBar extends FrameLayout {

    public interface SearchBarListener {
        void onSearchQueryExecuted(String searchQuery);
        void onSearchQueryChanged(String newSearchQuery);
    }

    private EditText x;
    private SearchBarListener mListener;

    public SearchBar(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public SearchBar(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        inflate(getContext(), R.layout.search_bar, this);
        mSearchInputBar = findViewById(R.id.et_searchbar_input);
    }

    public void setFullRectBackground() {
        this.getChildAt(0).setBackgroundResource(R.color.search_bg_white);
    }

    public void setSearchBarListener(SearchBarListener searchBarListener) {
        mListener = searchBarListener;
        mSearchInputBar.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    if (mListener != null) {
                        mListener.onSearchQueryExecuted(v.getText().toString());
                    }
                    return true;
                }
                return false;
            }
        });

        mSearchInputBar.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (mListener != null) {
                    mListener.onSearchQueryChanged(s.toString());
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    public void appendText(String text) {
        mSearchInputBar.append(text);
    }

    /**
     * Clears search bar if there's text in it. If no text is present, nothing will happen.
     */
    public void clearSearchBar() {
        String text = mSearchInputBar.getText().toString();
        if (!text.isEmpty())
            mSearchInputBar.setText(null);
    }

    public String getText() {
        return mSearchInputBar.getText().toString();
    }
}
