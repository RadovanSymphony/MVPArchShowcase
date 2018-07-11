package com.mg.kode.kodebrowser.ui.searchengine;

import com.mg.kode.kodebrowser.data.SearchRepository;
import com.mg.kode.kodebrowser.data.model.SearchEngine;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;


public class SearchEngineInteractor implements ISearchEngineInteractor {

    private final SearchRepository mRepository;

    @Inject
    public SearchEngineInteractor(SearchRepository repository) {
        mRepository = repository;
    }

    @Override
    public Observable<List<SearchEngine>> getSearchEngines() {
        return mRepository.getAllEngines();
    }
}
