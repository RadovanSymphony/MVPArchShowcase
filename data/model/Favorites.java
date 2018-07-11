package com.mg.kode.kodebrowser.data.model;


import android.arch.persistence.room.Embedded;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.support.annotation.NonNull;

import java.util.Date;


/**
 * Favorite website
 */
@Entity(tableName = "favorite_page", primaryKeys = {"webpage_url"})
public class Favorites {
	
    private String title;
    @Embedded
    @NonNull
    private WebPage website;
    private long date;

    @Ignore
    public Favorites(String title, @NonNull String url) {
        this.title = title;
        website = new WebPage(url);
        this.date = new Date().getTime();
    }

    @Ignore
    public Favorites(String title, WebPage webPage) {
        this.title = title;
        website = webPage;
        this.date = new Date().getTime();
    }

    public Favorites(String title, WebPage website, long date) {
        this.title = title;
        this.website = website;
        this.date = date;
    }

    public String getTitle() {
        return title;
    }

    public WebPage getWebsite() {
        return website;
    }

    public long getDate() {
        return date;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null)
            return false;
        if (obj instanceof Favorites) {
            Favorites compareTo = (Favorites) obj;
            return compareTo.getWebsite() != null && this.website.getUrl().equals(compareTo.getWebsite().getUrl());
        } else {
            return false;
        }
    }
}
