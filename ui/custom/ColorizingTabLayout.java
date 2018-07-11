package com.mg.kode.kodebrowser.ui.custom;


import android.content.Context;
import android.graphics.Color;
import android.support.design.widget.TabLayout;
import android.util.AttributeSet;
import android.view.View;

import com.mg.kode.kodebrowser.R;
import com.mg.kode.kodebrowser.utils.UIUtils;

/**
 * Normal {@link android.support.design.widget.TabLayout}.
 * It will colorize it's background when it's parent is scrolled to the top of ScrollView.
 */
public class ColorizingTabLayout extends TabLayout {

    // Offset in pixels from to top of scroll container to start colorizing the background. 
    private static int OFFSET_PX_TO_COLORIZE_FROM = -1;
    private static int PRIMARY_COLOR;

    public ColorizingTabLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public ColorizingTabLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        PRIMARY_COLOR = getResources().getColor(R.color.colorPrimary);
    }

    /**
     * Called when parent view has been scrolled.
     *
     * @param scrollYoffset vertical scroll offset from the top.
     */
    public void onParentScroll(int scrollYoffset) {
        if (OFFSET_PX_TO_COLORIZE_FROM == -1) {
            OFFSET_PX_TO_COLORIZE_FROM = this.getHeight();
        }
        int parentTopPos = ((View) this.getParent()).getTop();
        if (scrollYoffset <= parentTopPos - OFFSET_PX_TO_COLORIZE_FROM) {
            setBackgroundColor(Color.TRANSPARENT);
        } else if (scrollYoffset < parentTopPos) {
            float alpha = 1 - (((float) parentTopPos - scrollYoffset) / (OFFSET_PX_TO_COLORIZE_FROM));
            int color = UIUtils.adjustAlpha(PRIMARY_COLOR, alpha);
            setBackgroundColor(color);
        } else {
            setBackgroundColor(PRIMARY_COLOR);
        }
    }
}
