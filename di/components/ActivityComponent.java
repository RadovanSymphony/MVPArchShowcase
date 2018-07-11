package com.mg.kode.kodebrowser.di.components;


import com.mg.kode.kodebrowser.di.PerActivity;
import com.mg.kode.kodebrowser.di.modules.ActivityModule;
import com.mg.kode.kodebrowser.ui.home.favorites_section.FavoritesFragment;
import com.mg.kode.kodebrowser.ui.home.news_feed_section.NewsFeedHolderFragment;
import com.mg.kode.kodebrowser.ui.home.news_feed_section.news_listing.NewsListFragment;
import com.mg.kode.kodebrowser.ui.home.search.SearchFragment;
import com.mg.kode.kodebrowser.ui.searchengine.SearchEngineSettingsActivity;

import dagger.Component;


@PerActivity
@Component(modules = ActivityModule.class, dependencies = ApplicationComponent.class)
public interface ActivityComponent {
	
    void inject(FavoritesFragment fragment);

    void inject(NewsFeedHolderFragment fragment);
    
    void inject(NewsListFragment fragment);
    
    void inject(SearchEngineSettingsActivity activity);
    
    void inject(SearchFragment searchFragment);
}