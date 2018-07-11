package com.mg.kode.kodebrowser.ui.onboarding;

import android.content.res.TypedArray;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.mg.kode.kodebrowser.R;
import com.mg.kode.kodebrowser.ui.custom.KodeStepProgressBar;
import com.mg.kode.kodebrowser.ui.home.HomeActivity;
import com.mg.kode.kodebrowser.utils.UIUtils;

import butterknife.BindArray;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class OnboardingActivity extends AppCompatActivity {

    // Total on boarding screen number
    private static final int SCREEN_NUM = 6;
    
    @BindArray(R.array.onboard_title)
    String[] mTitles = new String[SCREEN_NUM];
    @BindArray(R.array.onboard_mInfo)
    String[] mInfo = new String[SCREEN_NUM];
    @BindArray(R.array.onboard_mIcons)
    TypedArray mIcons;
    @BindArray(R.array.onboard_bg_images)
    TypedArray mBackgroundImgs;
    @BindArray(R.array.onboard_gradient_bgs)
    TypedArray mGradientBgs;
    @BindArray(R.array.onboard_active_colors)
    TypedArray mActiveStepColors;
    @BindView(R.id.vp_onboard_pager)
    ViewPager mPager;
    @BindView(R.id.pb_onboard_step_progress)
    KodeStepProgressBar mStepProgress;
    @BindView(R.id.btn_onboard_get_started)
    View mBtnGetStarted;
    @BindView(R.id.tv_onboard_skip)
    View mSkip;
    @BindView(R.id.ib_onboard_next)
    View mNext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_on_boarding);
        ButterKnife.bind(this);
        OnboardPagerAdapter adapter = new OnboardPagerAdapter(getSupportFragmentManager());
        mPager.setAdapter(adapter);
        mPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (position < SCREEN_NUM - 1) {
                    UIUtils.showViews(mStepProgress, mSkip, mNext);
                    UIUtils.hideViews(mBtnGetStarted);
                    mStepProgress.setVisibility(View.VISIBLE);
                    mStepProgress.setCurrentStep(position);
                    mStepProgress.setActiveProgressColorWithDefaultColor(mActiveStepColors.getColor(position, 0));
                    mStepProgress.updateView();
                } else {
                    UIUtils.hideViews(mStepProgress, mSkip, mNext);
                    UIUtils.showViews(mBtnGetStarted);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        mStepProgress.setTotalSteps(SCREEN_NUM - 1);
        mStepProgress.setCurrentStep(0);
        mStepProgress.setActiveProgressColorWithDefaultColor(mActiveStepColors.getColor(0, 0));
        mStepProgress.updateView();
    }

    @OnClick(R.id.tv_onboard_skip)
    public void onSkipClicked(View v) {
        mPager.setCurrentItem(SCREEN_NUM - 1, false);
    }

    @OnClick(R.id.ib_onboard_next)
    public void onNextClicked(View v) {
        mPager.setCurrentItem(mPager.getCurrentItem() + 1);
    }

    @OnClick(R.id.btn_onboard_get_started)
    public void onGetStartedClicked(View v) {
        HomeActivity.startActivity(this);
    }

    private class OnboardPagerAdapter extends FragmentPagerAdapter {

        public OnboardPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            if (position == 0) {
                return StartOnboardingFragment.newInstance();
            } else if (position != SCREEN_NUM - 1) {
                return OnboardingFragment.newInstance(mTitles[position], mInfo[position], mIcons.getResourceId(position, 0),
                        mBackgroundImgs.getResourceId(position, 0), mGradientBgs.getResourceId(position, 0));
            } else {
                return EndOnboardingFragment.newInstance();
            }
        }

        @Override
        public int getCount() {
            return SCREEN_NUM;
        }
    }
}
