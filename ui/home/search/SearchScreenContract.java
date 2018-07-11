package com.mg.kode.kodebrowser.ui.home.search;


import com.mg.kode.kodebrowser.data.model.History;
import com.mg.kode.kodebrowser.data.model.SearchEngine;
import com.mg.kode.kodebrowser.ui.base.BaseContract;

import java.util.List;

public interface SearchScreenContract {
    interface SearchView extends BaseContract.View {
        /**
         * Displays new list of search suggestions. If suggestions parameter is <code>NULL</code>, it will clear out the list.
         *
         * @param suggestions list of search suggestions to be displayed.
         */
        void setSearchSuggestions(List<String> suggestions);

        /**
         * Displays new list of search engines. If engines parameter is <code>NULL</code>, it will clear out the list.
         *
         * @param engines list of {@link SearchEngine} to be displayed.
         */
        void setSearchEngines(List<SearchEngine> engines);


        /**
         * Displays new list of search history. If history parameter is <code>NULL</code>, it will clear out the list.
         *
         * @param history list of {@link History} to be displayed.
         */
        void setSearchHistory(List<History> history);

        /**
         * Clears history and suggestions lists, showing empty view.
         */
        void clearLists();
    }

    interface SearchPresenter<V extends SearchView> extends BaseContract.Presenter<V> {
        /**
         * Called when the user has entered new text in search box.
         *
         * @param query new query text.
         */
        void onSearchQueryTextTyped(String query);

        /**
         * Called when the user has executed the search query.
         *
         * @param query user typed query.
         */
        void onSearchQuerySubmit(String query);


        /**
         * Called when the user has executed the search query by clicking on history item.
         *
         * @param history
         */
        void onHistoryItemClicked(History history);

        /**
         * Called when the user has executed the search query by choosing one of the provided search engines.
         *
         * @param query  user typed query.
         * @param engine clicked search engine.
         */
        void onSearchQuerySubmit(String query, SearchEngine engine);
    }
}
