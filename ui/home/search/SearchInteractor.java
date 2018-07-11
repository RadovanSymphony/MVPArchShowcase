package com.mg.kode.kodebrowser.ui.home.search;

import com.mg.kode.kodebrowser.data.SearchRepository;
import com.mg.kode.kodebrowser.data.model.History;
import com.mg.kode.kodebrowser.data.model.SearchEngine;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;


public class SearchInteractor implements ISearchInteractor {

    private final SearchRepository mRepository;

    @Inject
    public SearchInteractor(SearchRepository repository) {
        mRepository = repository;
    }

    @Override
    public Observable<List<SearchEngine>> getSearchEngines() {
        return mRepository.getAllEngines();
    }

    @Override
    public Observable<List<String>> getSearchSuggestions() {
        return mRepository.getSearchSuggestions();
    }

    @Override
    public Observable<List<History>> getSearchHistory() {
        return mRepository.getHistory();
    }

    @Override
    public void searchQueryChanged(String query) {
        mRepository.searchQueryChanged(query);
    }

    @Override
    public void searchQueryExecuted(String query) {
        mRepository.searchQueryExecuted(query);
    }

    @Override
    public void searchQueryExecuted(History historyItem) {
        mRepository.searchQueryExecuted(historyItem);
    }

    @Override
    public void searchQueryExecuted(String query, SearchEngine engine) {
        mRepository.searchQueryExecuted(query, engine);
    }
}
