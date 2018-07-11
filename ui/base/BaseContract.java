package com.mg.kode.kodebrowser.ui.base;


import android.support.annotation.StringRes;

public interface BaseContract {
	
    /**
     * Every presenter in the app must either implement this interface or extend BasePresenter
     * indicating the View type that wants to be attached with.
     */
    interface Presenter<V extends View> {

        void onAttach(V view);

        void onDetach();

        V getView();

        boolean isViewAttached();
    }

    /**
     * Base interface that any class that wants to act as a View in the MVP (Model View Presenter)
     * pattern must implement. Generally this interface will be extended by a more specific interface
     * that then usually will be implemented by an Activity or Fragment.
     */
    interface View {

        void showLoading();

        void hideLoading();

        void onError(@StringRes int resId);

        void onError(String message);

        void showMessage(String message);

        void showMessage(@StringRes int resId);

        boolean isNetworkConnected();
    }
}
