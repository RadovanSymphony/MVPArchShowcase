package com.mg.kode.kodebrowser.ui.searchengine;

import com.mg.kode.kodebrowser.data.model.SearchEngine;
import com.mg.kode.kodebrowser.ui.base.BasePresenter;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;


public class SearchEngineSettingsPresenter<V extends SearchEngineScreenContract.SearchEngineSettingsView>
        extends BasePresenter<V> implements SearchEngineScreenContract.SearchEngineSettingsPresenter<V> {

    private final ISearchEngineInteractor mInteractor;

    @Inject
    public SearchEngineSettingsPresenter(ISearchEngineInteractor interactor) {
        mInteractor = interactor;
    }

    @Override
    public void onAttach(V view) {
        super.onAttach(view);
        mInteractor.getSearchEngines()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<List<SearchEngine>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        getCompositeDisposable().add(d);
                    }

                    @Override
                    public void onNext(List<SearchEngine> searchEngines) {
                        if (isViewAttached())
                            getView().setSearchEngineList(searchEngines);
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }
}
