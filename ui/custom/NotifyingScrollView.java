package com.mg.kode.kodebrowser.ui.custom;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ScrollView;

/**
 *  Simple {@link android.widget.ScrollView} that has exposed methods for attaching onScrollListener.
 */
public class NotifyingScrollView extends ScrollView {

    private ScrollListener mScrollListener;

    public NotifyingScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public NotifyingScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void setScrollListener(ScrollListener scrollListener) {
        mScrollListener = scrollListener;
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        if(mScrollListener != null){
            mScrollListener.onScrollChanged(l, t, oldl, oldt);
        }
        super.onScrollChanged(l, t, oldl, oldt);
    }

    public interface ScrollListener {
        void onScrollChanged(int x, int y, int oldX, int oldY);
    }
}
