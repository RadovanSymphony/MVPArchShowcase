package com.mg.kode.kodebrowser.data.model;

import android.arch.persistence.room.Embedded;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;


@Entity(tableName = "search_engine")
public class SearchEngine {
	
    private String title;
    @Embedded
    private WebPage webpage;
    @PrimaryKey(autoGenerate = true)
    private int _id;

    @Ignore
    public SearchEngine(String title, String url) {
        this.title = title;
        webpage = new WebPage(url);
    }

    @Ignore
    public SearchEngine(String title, String url, int _id) {
        this.title = title;
        webpage = new WebPage(url);
        this._id = _id;
    }

    public SearchEngine(String title, WebPage webpage, int _id) {
        this.title = title;
        this.webpage = webpage;
        this._id = _id;
    }

    public String getTitle() {
        return title;
    }

    public int get_id() {
        return _id;
    }

    public WebPage getWebpage() {
        return webpage;
    }

    public String getSearchQueryTitle(String query) {
        return String.format("%s - %s", query, title);
    }

    public String getSearchQueryURL(String query) {
        // TODO build query
        return webpage.getUrl() + "?q=" + query;
    }
}
