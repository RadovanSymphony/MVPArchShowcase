package com.mg.kode.kodebrowser.ui.searchengine;

import com.mg.kode.kodebrowser.data.model.SearchEngine;

import java.util.List;

import io.reactivex.Observable;


public interface ISearchEngineInteractor {
	
    Observable<List<SearchEngine>> getSearchEngines();
}
