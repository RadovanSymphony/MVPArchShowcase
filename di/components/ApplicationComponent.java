package com.mg.kode.kodebrowser.di.components;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import com.mg.kode.kodebrowser.data.FavoritesRepository;
import com.mg.kode.kodebrowser.data.NewsRepository;
import com.mg.kode.kodebrowser.data.SearchRepository;
import com.mg.kode.kodebrowser.data.local.PreferenceManager;
import com.mg.kode.kodebrowser.data.network.GoogleAPI;
import com.mg.kode.kodebrowser.di.ApplicationContext;
import com.mg.kode.kodebrowser.di.modules.ApplicationModule;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = ApplicationModule.class)
public interface ApplicationComponent {

    @ApplicationContext
    Context getContext();

    Application getApplication();

    SharedPreferences getApplicationPreferences();

    FavoritesRepository getFavoritesRepository();

    NewsRepository getNewsRepository();

    SearchRepository getEngineRepository();

    GoogleAPI getGoogleAPI();
}
