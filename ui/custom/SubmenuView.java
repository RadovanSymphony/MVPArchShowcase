package com.mg.kode.kodebrowser.ui.custom;

import android.animation.Animator;
import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.animation.DecelerateInterpolator;
import android.widget.RelativeLayout;

import com.mg.kode.kodebrowser.R;
import com.mg.kode.kodebrowser.utils.UIUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 * Relative layout submenu, which will hide itself when dimmed background is clicked.
 */
public class SubmenuView extends RelativeLayout {

    public interface SubmenuClosedListener {
        void onSubmenuClosed();
    }

    @BindView(R.id.submenu_items_wrapper)
    private ViewGroup mSubmenuWrapper;
    @BindView(R.id.submenu_bg)
    View mSubmenuBg;
    private int mSubmenuHeight;

    private SubmenuClosedListener mClosedListener;

    private boolean mSubmenuShown = false;
    private boolean mIsAnimationComplete = true;

    public SubmenuView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public SubmenuView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        inflate(getContext(), R.layout.home_submenu_layout, this);
        ButterKnife.bind(this);

        mSubmenuWrapper.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                mSubmenuWrapper.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                mSubmenuWrapper.setTranslationY(mSubmenuWrapper.getHeight());
                mSubmenuHeight = mSubmenuWrapper.getHeight();
                UIUtils.hideViews(mSubmenuWrapper);
            }
        });
        mSubmenuBg.setAlpha(0);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        if (mIsAnimationComplete && mSubmenuBg.getAlpha() != 0) {
            int x = Math.round(ev.getX());
            int y = Math.round(ev.getY());
            if (ev.getActionMasked() == MotionEvent.ACTION_UP &&
                    !(x > mSubmenuWrapper.getLeft() && x < mSubmenuWrapper.getRight() && y > mSubmenuWrapper.getTop() && y < mSubmenuWrapper.getBottom())) {
                // hide view
                hideSubmenu();
            }
            return true;
        } else {
            return false;
        }
    }

    @OnClick(R.id.iv_close_submenu)
    public void closeSubmenu() {
        hideSubmenu();
    }

    public void showSubmenu() {
        if (mIsAnimationComplete) {
            mIsAnimationComplete = false;
            mSubmenuBg.animate()
                    .alpha(1)
                    .setDuration(40)
                    .start();
            mSubmenuWrapper.animate()
                    .translationYBy(-mSubmenuHeight)
                    .setInterpolator(new DecelerateInterpolator())
                    .setListener(new Animator.AnimatorListener() {
                        @Override
                        public void onAnimationStart(Animator animation) {
                            UIUtils.showViews(mSubmenuWrapper);
                        }

                        @Override
                        public void onAnimationEnd(Animator animation) {
                            mIsAnimationComplete = true;
                            mSubmenuShown = true;
                        }

                        @Override
                        public void onAnimationCancel(Animator animation) {

                        }

                        @Override
                        public void onAnimationRepeat(Animator animation) {

                        }
                    }).start();
        }
    }

    public void hideSubmenu() {
        if (mIsAnimationComplete) {
            mIsAnimationComplete = false;
            mSubmenuBg.animate()
                    .alpha(0)
                    .setDuration(40)
                    .start();
            mSubmenuWrapper.animate()
                    .translationYBy(mSubmenuHeight)
                    .setListener(new Animator.AnimatorListener() {
                        @Override
                        public void onAnimationStart(Animator animation) {

                        }

                        @Override
                        public void onAnimationEnd(Animator animation) {
                            mIsAnimationComplete = true;
                            mSubmenuShown = false;
                            UIUtils.hideViews(mSubmenuWrapper);
                            if (mClosedListener != null) {
                                mClosedListener.onSubmenuClosed();
                            }
                        }

                        @Override
                        public void onAnimationCancel(Animator animation) {

                        }

                        @Override
                        public void onAnimationRepeat(Animator animation) {

                        }
                    }).start();
        }
    }

    public void setOnClosedListener(SubmenuClosedListener closedListener) {
        this.mClosedListener = closedListener;
    }

    public boolean isSubmenuShown() {
        return mSubmenuShown;
    }
}
