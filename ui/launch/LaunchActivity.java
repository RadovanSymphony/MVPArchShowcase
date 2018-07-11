package com.mg.kode.kodebrowser.ui.launch;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;

import com.mg.kode.kodebrowser.R;
import com.mg.kode.kodebrowser.ui.base.BaseActivity;
import com.mg.kode.kodebrowser.ui.onboarding.OnboardingActivity;
import com.mg.kode.kodebrowser.utils.UIUtils;

import butterknife.BindView;
import butterknife.ButterKnife;

public class LaunchActivity extends BaseActivity implements LaunchScreenContract.LaunchView {

    private LaunchPresenter<LaunchScreenContract.LaunchView> mPresenter;

    @BindView(R.id.pb_launch_loader)
    ProgressBar mPbLoader;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPresenter = new LaunchPresenter<>();
        mPresenter.onAttach(this);
        mPresenter.loadInitialData();
    }

    @Override
    public void onLoadComplete() {
        startActivity(new Intent(this, OnboardingActivity.class));
        this.finish();
    }

    @Override
    public void showLoading() {
        mPbLoader.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLoading() {
        mPbLoader.setVisibility(View.INVISIBLE);
    }

    @Override
    public void onError(String message) {
        UIUtils.showShortToast(this, message);
    }

    @Override
    public void showMessage(String message) {
        UIUtils.showShortToast(this, message);
    }

    @Override
    protected void onDestroy() {
        mPresenter.onDetach();
        super.onDestroy();
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_launch;
    }
}
