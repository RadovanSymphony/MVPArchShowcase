package com.mg.kode.kodebrowser.ui.launch;


import com.mg.kode.kodebrowser.ui.base.BasePresenter;

public class LaunchPresenter<V extends LaunchScreenContract.LaunchView> extends BasePresenter<V> implements LaunchScreenContract.LaunchPresenter<V> {

    private final SyncInteractor mSyncInteractor;

    public LaunchPresenter() {
        mSyncInteractor = new SyncInteractor();
    }

    public void loadInitialData() {
        getView().showLoading();
        mSyncInteractor.getDummyData(new SyncInteractor.DataLoadedCallback() {
            @Override
            public void onLoadComplete(String data) {
                if (isViewAttached()) {
                    getView().onLoadComplete();
                }
            }

            @Override
            public void onLoadFailed(String errorMessage) {
                if (isViewAttached()) {
                    getView().hideLoading();
                    getView().onError("Failed to load data.");
                }
            }
        });
    }
}
