package com.mg.kode.kodebrowser.ui.searchengine;

import com.mg.kode.kodebrowser.data.model.SearchEngine;
import com.mg.kode.kodebrowser.ui.base.BaseContract;

import java.util.List;

public interface SearchEngineScreenContract {
	
    interface SearchEngineSettingsView extends BaseContract.View {
        /**
         * Displays new list of search engines.
         *
         * @param engines list of {@link SearchEngine} to be displayed.
         */
        void setSearchEngineList(List<SearchEngine> engines);
    }


    interface SearchEngineSettingsPresenter<V extends SearchEngineSettingsView> extends BaseContract.Presenter<V> {
    }
}
