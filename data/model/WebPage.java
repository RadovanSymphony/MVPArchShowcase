package com.mg.kode.kodebrowser.data.model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Ignore;
import android.support.annotation.NonNull;

import com.mg.kode.kodebrowser.BuildConfig;


public class WebPage {
	
    public static final String NO_FAVICON = "no_favicon";

    @ColumnInfo(name = "webpage_url")
    @NonNull
    private String url;
    @ColumnInfo(name = "webpage_title")
    private String title;
    private String favicon;

    public WebPage(@NonNull String url, String title,
                   String favicon) {
        this.url = url;
        this.title = title;
        this.favicon = favicon;
    }

    @Ignore
    public WebPage(@NonNull String url, String title) {
        this(url, title, NO_FAVICON);
    }

    @Ignore
    public WebPage(@NonNull String url) {
        this(url, url);
    }

    public String getFavicon() {
        return favicon;
    }

    public String getTitle() {
        return title;
    }

    public String getUrl() {
        return url;
    }

    public String getDefaultFaviconUrl() {
        return BuildConfig.FAVICON_API_URL + url;
    }
}
