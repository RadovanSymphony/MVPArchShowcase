package com.mg.kode.kodebrowser.ui.home.search;

import com.mg.kode.kodebrowser.data.model.History;
import com.mg.kode.kodebrowser.data.model.SearchEngine;

import java.util.List;

import io.reactivex.Observable;


public interface ISearchInteractor {
    Observable<List<SearchEngine>> getSearchEngines();

    Observable<List<String>> getSearchSuggestions();

    Observable<List<History>> getSearchHistory();

    void searchQueryChanged(String query);

    void searchQueryExecuted(String query);

    void searchQueryExecuted(History historyItem);

    void searchQueryExecuted(String query, SearchEngine engine);
}
