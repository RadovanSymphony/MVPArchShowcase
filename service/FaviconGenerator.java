package com.mg.kode.kodebrowser.service;


import android.util.Patterns;

import com.mg.kode.kodebrowser.data.model.WebPage;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.util.concurrent.Callable;

import io.reactivex.Single;
import io.reactivex.schedulers.Schedulers;


/**
 * Generates High-res favicon for a desired website if available, if not, generates regular 32x32 favicons.
 */
public class FaviconGenerator {

    private final String mWebsiteUrl;

    public FaviconGenerator(String url) {
        if (!url.startsWith("http")) {
            url = "http://" + url;
        }
        mWebsiteUrl = url;
    }

    /**
     * Return observable   which will emit url pointing to high-resolution favicon image if present or low-res (32x32 px) icon.
     * If finding favicon fails, it will throw en error.
     *
     * @return {@link Single} emitting favicon url.
     */
    public Single<String> getFaviconURL() {
        return Single.fromCallable(new Callable<String>() {
            @Override
            public String call() throws Exception {
                Document doc = Jsoup.connect(mWebsiteUrl).get();
                Elements els = doc.head().select("link[rel*=apple-touch-icon]");
                if (els.size() > 0) {
                    String attr = els.get(0).attr("href");
                    if (Patterns.WEB_URL.matcher(attr).matches()) {
                        return attr;
                    } else {
                        return mWebsiteUrl + attr;
                    }
                } else {
                    return new WebPage(mWebsiteUrl).getDefaultFaviconUrl();
                }
            }
        }).subscribeOn(Schedulers.computation());
    }
}
