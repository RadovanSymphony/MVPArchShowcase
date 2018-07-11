package com.mg.kode.kodebrowser.ui.launch;

import com.mg.kode.kodebrowser.ui.base.BaseContract;


public interface LaunchScreenContract {
    interface LaunchView extends BaseContract.View {
        void onLoadComplete();
    }

    interface LaunchPresenter<V extends LaunchView> extends BaseContract.Presenter<V> {

    }
}
