package com.mg.kode.kodebrowser.di.modules;

import android.app.Application;
import android.arch.persistence.room.Room;
import android.content.Context;
import android.content.SharedPreferences;

import com.mg.kode.kodebrowser.data.DefaultFavoritesRepository;
import com.mg.kode.kodebrowser.data.DefaultNewsRepository;
import com.mg.kode.kodebrowser.data.DefaultSearchRepository;
import com.mg.kode.kodebrowser.data.FavoritesRepository;
import com.mg.kode.kodebrowser.data.NewsRepository;
import com.mg.kode.kodebrowser.data.SearchRepository;
import com.mg.kode.kodebrowser.data.local.KodeDatabase;
import com.mg.kode.kodebrowser.data.network.GoogleAPI;
import com.mg.kode.kodebrowser.data.network.RetrofitClient;
import com.mg.kode.kodebrowser.di.ApplicationContext;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;


@Module
public class ApplicationModule {

    private final Application mApplication;

    public ApplicationModule(Application application) {
        mApplication = application;
    }

    @Provides
    @ApplicationContext
    Context provideContext() {
        return mApplication;
    }

    @Provides
    Application provideApplication() {
        return mApplication;
    }

    @Provides
    SharedPreferences provideSharedPreferences() {
        return mApplication.getSharedPreferences("kode-preferences", Context.MODE_PRIVATE);
    }

    @Provides
    FavoritesRepository provideFavoritesRepository(DefaultFavoritesRepository repository) {
        return repository;
    }

    @Provides
    NewsRepository provideNewsRepository(DefaultNewsRepository repository) {
        return repository;
    }

    @Provides
    SearchRepository provideSearchEngineRepository(DefaultSearchRepository repository) {
        return repository;
    }

    @Provides
    @Singleton
    KodeDatabase provideDatabase() {
        KodeDatabase db = Room.databaseBuilder(mApplication.getApplicationContext(), KodeDatabase.class, "KodeDatabase")
                .fallbackToDestructiveMigration().build();

        return db;
    }

    @Provides
    @Singleton
    GoogleAPI provideRestAPI() {
        return new RetrofitClient().getGoogleAPI(mApplication);
    }
}
