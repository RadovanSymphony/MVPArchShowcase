package com.mg.kode.kodebrowser.ui.base;

import android.content.Context;
import android.support.v4.app.Fragment;

import com.mg.kode.kodebrowser.di.components.ActivityComponent;
import com.mg.kode.kodebrowser.utils.NetworkUtils;

import butterknife.Unbinder;


public abstract class BaseFragment extends Fragment implements BaseContract.View {

    private BaseActivity mActivity;
    private Unbinder mUnBinder;

    public BaseFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof BaseActivity) {
            BaseActivity activity = (BaseActivity) context;
            this.mActivity = activity;
        }
    }

    @Override
    public void onDetach() {
        mActivity = null;
        super.onDetach();
    }

    @Override
    public boolean isNetworkConnected() {
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

    @Override
    public void onDestroy() {
        if (mUnBinder != null) {
            mUnBinder.unbind();
        }
        super.onDestroy();
    }

    public ActivityComponent getActivityComponent() {
        if (mActivity != null) {
            return mActivity.getActivityComponent();
        }
        return null;
    }

    public void setUnBinder(Unbinder unBinder) {
        mUnBinder = unBinder;
    }
}
