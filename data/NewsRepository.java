package com.mg.kode.kodebrowser.data;


import com.mg.kode.kodebrowser.data.model.NewsArticle;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.annotations.NonNull;


/**
 * Repository class for dealing with {@link NewsArticle}. Provides basic loading mechanism of article news feed.
 */
public interface NewsRepository {
	
    /**
     * Retrieves news feed for selected topic and page number.
     *
     * @param topicSlug news feed's topic.
     * @param pageNum   page number of the list.
     * @return list of {@link NewsArticle}.
     */
    @NonNull
    Observable<List<NewsArticle>> getNewsFeed(String topicSlug, int pageNum);
}
