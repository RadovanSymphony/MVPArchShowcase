package com.mg.kode.kodebrowser.data.local.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.mg.kode.kodebrowser.data.model.History;
import com.mg.kode.kodebrowser.data.model.SearchEngine;

import java.util.List;

import io.reactivex.Flowable;

@Dao
public abstract class SearchDao {

    @Query("SELECT * FROM search_engine")
    public abstract Flowable<List<SearchEngine>> getSearchEngines();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public abstract void saveSearchEngine(SearchEngine... engines);

    @Delete
    public abstract void deleteEngine(SearchEngine... engines);

    public List<History> getHistory(String searchQuery) {
        String query = "%" + searchQuery + "%";
        return executeHistoryQuery(query);
    }

    @Query("SELECT * FROM search_history" +
            " WHERE search_history.title LIKE :searchQuery" +
            " OR search_history.webpage_url LIKE :searchQuery" +
            " ORDER BY search_history.date DESC")
    abstract List<History> executeHistoryQuery(String searchQuery);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public abstract void saveHistory(History... history);

    @Query("DELETE FROM search_history")
    public abstract void deleteAllHistory();
}
