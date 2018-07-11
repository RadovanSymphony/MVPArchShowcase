package com.mg.kode.kodebrowser.ui.home.search;

import com.mg.kode.kodebrowser.data.model.History;
import com.mg.kode.kodebrowser.data.model.SearchEngine;
import com.mg.kode.kodebrowser.ui.base.BasePresenter;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class SearchScreenPresenter<V extends SearchScreenContract.SearchView> extends BasePresenter<V> implements SearchScreenContract.SearchPresenter<V> {

    private final ISearchInteractor mInteractor;

    @Inject
    SearchScreenPresenter(ISearchInteractor searchInteractor) {
        mInteractor = searchInteractor;
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
                            getView().setSearchEngines(searchEngines);
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });

        mInteractor.getSearchHistory()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<List<History>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        getCompositeDisposable().add(d);
                    }

                    @Override
                    public void onNext(List<History> history) {
                        if (isViewAttached())
                            getView().setSearchHistory(history);
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });

        mInteractor.getSearchSuggestions().subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<List<String>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        getCompositeDisposable().add(d);
                    }

                    @Override
                    public void onNext(List<String> suggestions) {
                        if (isViewAttached())
                            getView().setSearchSuggestions(suggestions);
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    @Override
    public void onSearchQueryTextTyped(String query) {
        if (query != null && !query.isEmpty()) {
            mInteractor.searchQueryChanged(query);
        } else if (isViewAttached()) {
            getView().clearLists();
        }
    }

    @Override
    public void onSearchQuerySubmit(String query) {
        mInteractor.searchQueryExecuted(query);
    }

    @Override
    public void onHistoryItemClicked(History history) {
        mInteractor.searchQueryExecuted(history);
    }

    @Override
    public void onSearchQuerySubmit(String query, SearchEngine engine) {
        mInteractor.searchQueryExecuted(query, engine);
    }
}
