package com.mg.kode.kodebrowser.ui.base;


import io.reactivex.disposables.CompositeDisposable;

/**
 * Base class that implements the BaseContract.Presenter interface and provides a base implementation for
 * onAttach() and onDetach().
 */
public class BasePresenter<V extends BaseContract.View>
        implements BaseContract.Presenter<V> {

    private static final String TAG = "BasePresenter";

    private V mView;
    private CompositeDisposable mCompositeDisposable;


    public BasePresenter() {
        mCompositeDisposable = new CompositeDisposable();
    }


    @Override
    public void onAttach(V view) {
        mView = view;
    }

    @Override
    public void onDetach() {
        if (mCompositeDisposable != null && !mCompositeDisposable.isDisposed()) {
            mCompositeDisposable.dispose();
        }
        mView = null;
    }

    @Override
    public V getView() {
        return mView;
    }


    @Override
    public boolean isViewAttached() {
        return mView != null;
    }

    protected CompositeDisposable getCompositeDisposable() {
        return mCompositeDisposable;
    }

    public static class ViewNotAttachedException extends RuntimeException {
        public ViewNotAttachedException() {
            super("Please call Presenter.onAttach(View) before" +
                    " requesting data to the Presenter");
        }
    }
}