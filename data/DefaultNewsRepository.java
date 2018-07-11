package com.mg.kode.kodebrowser.data;

import com.mg.kode.kodebrowser.data.model.ArticleCategory;
import com.mg.kode.kodebrowser.data.model.NewsArticle;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

import javax.inject.Inject;

import io.reactivex.Observable;


public class DefaultNewsRepository implements NewsRepository {
	
    static List<NewsArticle> mResults = new ArrayList<>(10);

    static {
        ArticleCategory category = new ArticleCategory(Integer.toString(0), "lorem_ipsum", "Lorem Ipsum");
        for (Integer i = 0; i < 10; i++) {
            NewsArticle article = new NewsArticle(i.toString(), "Lorem Ipsum", "www.google.com", "www.google.com", "2018-1-1", category);
            mResults.add(article);
        }
    }
s
    @Inject
    public DefaultNewsRepository() {
    }

    @Override
    public Observable<List<NewsArticle>> getNewsFeed(String topicSlug, int pageNum) {
        return Observable.fromCallable(() -> mResults);
    }
}
