package com.mg.kode.kodebrowser.di.modules;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;

import com.mg.kode.kodebrowser.di.ActivityContext;
import com.mg.kode.kodebrowser.di.PerActivity;
import com.mg.kode.kodebrowser.ui.home.favorites_section.FavoritesInteractor;
import com.mg.kode.kodebrowser.ui.home.favorites_section.FavoritesPresenter;
import com.mg.kode.kodebrowser.ui.home.favorites_section.FavoritesSectionContract;
import com.mg.kode.kodebrowser.ui.home.favorites_section.IFavoritesInteractor;
import com.mg.kode.kodebrowser.ui.home.news_feed_section.INewsInteractor;
import com.mg.kode.kodebrowser.ui.home.news_feed_section.NewsFeedPresenter;
import com.mg.kode.kodebrowser.ui.home.news_feed_section.NewsFeedSectionContract;
import com.mg.kode.kodebrowser.ui.home.news_feed_section.NewsInteractor;
import com.mg.kode.kodebrowser.ui.home.news_feed_section.news_listing.NewsListContract;
import com.mg.kode.kodebrowser.ui.home.news_feed_section.news_listing.NewsListPresenter;
import com.mg.kode.kodebrowser.ui.searchengine.ISearchEngineInteractor;
import com.mg.kode.kodebrowser.ui.searchengine.SearchEngineInteractor;
import com.mg.kode.kodebrowser.ui.searchengine.SearchEngineScreenContract;
import com.mg.kode.kodebrowser.ui.searchengine.SearchEngineSettingsPresenter;
import com.mg.kode.kodebrowser.ui.home.search.ISearchInteractor;
import com.mg.kode.kodebrowser.ui.home.search.SearchInteractor;
import com.mg.kode.kodebrowser.ui.home.search.SearchScreenContract;
import com.mg.kode.kodebrowser.ui.home.search.SearchScreenPresenter;

import dagger.Module;
import dagger.Provides;


@Module
public class ActivityModule {
	
    private AppCompatActivity mActivity;

    public ActivityModule(AppCompatActivity activity) {
        mActivity = activity;
    }

    @Provides
    @ActivityContext
    Context provideContext() {
        return mActivity;
    }

    // Favorites
    @Provides
    @PerActivity
    FavoritesSectionContract.FavoritesPresenter<FavoritesSectionContract.FavoritesView> provideFavoritesPresenter(
            FavoritesPresenter<FavoritesSectionContract.FavoritesView> presenter) {
        return presenter;
    }

    @Provides
    @PerActivity
    IFavoritesInteractor providesFavoritesInteractor(
            FavoritesInteractor interactor) {
        return interactor;
    }

    // News
    @Provides
    @PerActivity
    NewsFeedSectionContract.NewsFeedPresenter<NewsFeedSectionContract.NewsFeedView> provideNewsFeedPresenter(
            NewsFeedPresenter<NewsFeedSectionContract.NewsFeedView> presenter) {
        return presenter;
    }

    @Provides
    @PerActivity
    NewsListContract.NewsListPresenter<NewsListContract.NewsListView> provideNewsListPresenter(
            NewsListPresenter<NewsListContract.NewsListView> presenter) {
        return presenter;
    }

    @Provides
    @PerActivity
    INewsInteractor providesNewsInteractorInteractor(
            NewsInteractor interactor) {
        return interactor;
    }

    // Search engine
    @Provides
    @PerActivity
    ISearchEngineInteractor providesSearchEngineInteractor(SearchEngineInteractor interactor) {
        return interactor;
    }

    @Provides
    @PerActivity
    SearchEngineScreenContract.SearchEngineSettingsPresenter<SearchEngineScreenContract.SearchEngineSettingsView> providesSearchEngineSettingsPresenter(
            SearchEngineSettingsPresenter<SearchEngineScreenContract.SearchEngineSettingsView> presenter) {
        return presenter;
    }

    // Search view
    @Provides
    @PerActivity
    SearchScreenContract.SearchPresenter<SearchScreenContract.SearchView> provideSearchScreenPresenter(
            SearchScreenPresenter<SearchScreenContract.SearchView> presenter) {
        return presenter;
    }

    @Provides
    @PerActivity
    ISearchInteractor provideSearchInteractor(
                    SearchInteractor interactor) {
        return interactor;
    }
}
