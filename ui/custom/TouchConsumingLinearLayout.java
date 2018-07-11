package com.mg.kode.kodebrowser.ui.custom;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;


/**
 * Blocks touch events and stop event propagation to it's children if click listener is set up on it.
 */
public class TouchConsumingLinearLayout extends LinearLayout {

    private OnClickListener mClickListener;

    public TouchConsumingLinearLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public TouchConsumingLinearLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public void setOnClickListener(View.OnClickListener listener) {
        mClickListener = listener;
        super.setOnClickListener(mClickListener);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        if (mClickListener != null) {
            return true;
        } else
            return super.onInterceptTouchEvent(ev);
    }
}
