package com.mg.kode.kodebrowser.data;


import com.mg.kode.kodebrowser.data.model.History;
import com.mg.kode.kodebrowser.data.model.SearchEngine;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.annotations.NonNull;


public interface SearchRepository {

    /**
     * Retrieves all available search engines.
     *
     * @return observable emitting list of {@link SearchEngine}.
     */
    @NonNull
    Observable<List<SearchEngine>> getAllEngines();

    /**
     * Adds new search engine to the database.
     *
     * @param title search engine's title.
     * @param url   search engine's url.
     */
    void addNewEngine(String title, String url);

    /**
     * Retrieves search suggestions based on provided search query entered by calling {@link #searchQueryChanged(String)}.
     *
     * @return observable emitting list of search suggestions.
     */
    @NonNull
    Observable<List<String>> getSearchSuggestions();

    /**
     * Retrieves history that matches search query entered by calling {@link #searchQueryChanged(String)}.
     *
     * @return observable emitting list of {@link History}.
     */
    @NonNull
    Observable<List<History>> getHistory();

    /**
     * Call when search query has changed, meaning that search suggestions and search history needs to change to.
     * Calling this method will trigger emitting new items on {@link #getHistory()} and {@link #getSearchSuggestions()}.
     *
     * @param query search query to match suggestions and history with.
     */
    void searchQueryChanged(String query);

    /**
     * Saves search query in search history with search engine currently in use.
     *
     * @param query executed search query.
     */
    void searchQueryExecuted(String query);

    /**
     * Saves search history as newly visited search result.
     *
     * @param history visited history.
     */
    void searchQueryExecuted(History history);

    /**
     * Saves search query in search history with provided engine currently.
     *
     * @param query  executed search query.
     * @param engine search engine in use.
     */
    void searchQueryExecuted(String query, SearchEngine engine);
}
