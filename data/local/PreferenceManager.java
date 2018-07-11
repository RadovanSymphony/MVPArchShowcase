package com.mg.kode.kodebrowser.data.local;

import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.mg.kode.kodebrowser.data.model.SearchEngine;

import javax.inject.Inject;

import io.reactivex.annotations.Nullable;


public class PreferenceManager {
	
    private static final String KEY_DEF_SEARCH_ENGINE = "default_search_engine";

    private final SharedPreferences mPreferences;

    @Inject
    public PreferenceManager(SharedPreferences preferences) {
        mPreferences = preferences;
    }

    public @Nullable
    SearchEngine getDefaultSearchEngine() {
        String searchEngine = mPreferences.getString(KEY_DEF_SEARCH_ENGINE, null);
        if (searchEngine == null)
            return null;
        return new Gson().fromJson(searchEngine, SearchEngine.class);
    }

    public void setDefaultSearchEngine(SearchEngine searchEngine) {
        mPreferences.edit().putString(KEY_DEF_SEARCH_ENGINE, new Gson().toJson(searchEngine)).apply();
    }
}
