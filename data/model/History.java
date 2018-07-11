package com.mg.kode.kodebrowser.data.model;

import android.arch.persistence.room.Embedded;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.support.annotation.NonNull;

import java.util.Date;


@Entity(tableName = "search_history", primaryKeys = {"webpage_url"})
public class History {
	
    private String title;
    @Embedded
    @NonNull
    private WebPage webPage;
    private long date;

    @Ignore
    public History(@NonNull String title, String url) {
        this.title = title;
        this.webPage = new WebPage(url);
        this.date = new Date().getTime();
    }

    public History(@NonNull String title, @NonNull WebPage webPage, long date) {
        this.title = title;
        this.webPage = webPage;
        this.date = date;
    }

    public long getDate() {
        return date;
    }

    @NonNull
    public String getTitle() {
        return title;
    }

    public WebPage getWebPage() {
        return webPage;
    }

    public String getUrl() {
        return webPage.getUrl();
    }
}
