package com.mg.kode.kodebrowser.ui.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.mg.kode.kodebrowser.KodeApplication;
import com.mg.kode.kodebrowser.di.components.ActivityComponent;
import com.mg.kode.kodebrowser.di.components.DaggerActivityComponent;
import com.mg.kode.kodebrowser.di.modules.ActivityModule;
import com.mg.kode.kodebrowser.utils.NetworkUtils;

import butterknife.ButterKnife;
import butterknife.Unbinder;


public abstract class BaseActivity extends AppCompatActivity implements BaseContract.View {

    private ActivityComponent mActivityComponent;
    private Unbinder mUnBinder;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivityComponent = DaggerActivityComponent.builder()
                .activityModule(new ActivityModule(this))
                .applicationComponent(((KodeApplication) getApplication()).getApplicationComponent())
                .build();
        if (getLayout() != 0) {
            setContentView(getLayout());
            setUnBinder(ButterKnife.bind(this));
        }
    }

    public ActivityComponent getActivityComponent() {
        return mActivityComponent;
    }

    @Override
    public final boolean isNetworkConnected() {
        return NetworkUtils.isNetworkConnected();
    }

    @Override
    public void onError(int resId) {
        this.onError(getString(resId));
    }

    @Override
    public void showMessage(int resId) {
        this.showMessage(getString(resId));
    }

    @Override
    public void showLoading() {
    }

    @Override
    public void hideLoading() {
    }

    @Override
    public void onError(String message) {
    }

    @Override
    public void showMessage(String message) {
    }

    public void setUnBinder(Unbinder unBinder) {
        mUnBinder = unBinder;
    }

    @Override
    protected void onDestroy() {
        if (mUnBinder != null) {
            mUnBinder.unbind();
        }
        super.onDestroy();
    }

    abstract protected int getLayout();
}
