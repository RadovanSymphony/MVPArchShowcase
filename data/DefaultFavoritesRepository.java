package com.mg.kode.kodebrowser.data;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Patterns;

import com.mg.kode.kodebrowser.R;
import com.mg.kode.kodebrowser.data.local.KodeDatabase;
import com.mg.kode.kodebrowser.data.model.Favorites;
import com.mg.kode.kodebrowser.data.model.WebPage;
import com.mg.kode.kodebrowser.di.ApplicationContext;
import com.mg.kode.kodebrowser.service.FaviconGenerator;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Completable;
import io.reactivex.Observable;
import io.reactivex.SingleObserver;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subjects.CompletableSubject;


@Singleton
public class DefaultFavoritesRepository implements FavoritesRepository {

    private final KodeDatabase mDatabase;
    private final Context mContext;

    @Inject
    public DefaultFavoritesRepository(KodeDatabase database, @ApplicationContext Context context) {
        mDatabase = database;
        mContext = context;
    }

    @NonNull
    @Override
    public Observable<List<Favorites>> getAllFavorites() {
        return mDatabase.favoritesDao().getFavoritesObservable().toObservable();
    }


    @Override
    public void addNewFavorite(String title, String url) {
        Favorites f = new Favorites(title, url);
        addNewFavorite(f);
    }

    private void addNewFavorite(Favorites favorites) {
        FaviconGenerator g = new FaviconGenerator(favorites.getWebsite().getUrl());
        g.getFaviconURL()
                .subscribeOn(Schedulers.io())
                .subscribe(new SingleObserver<String>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                    }

                    @Override
                    public void onSuccess(String s) {
                        WebPage webPage = new WebPage(favorites.getWebsite().getTitle(), favorites.getWebsite().getUrl(), s);
                        Favorites fav = new Favorites(favorites.getTitle(), webPage, favorites.getDate());
                        saveFavorite(fav);
                    }

                    @Override
                    public void onError(Throwable e) {
                    	// ignore error, meaning no favorite icon will be added s
                    }
                });


        saveFavorite(favorites);
    }

    private void saveFavorite(Favorites favorites) {
        Completable.fromAction(() -> mDatabase.favoritesDao().saveFavorite(favorites))
                .subscribeOn(Schedulers.io())
                .onErrorComplete()
                .subscribe();
    }

    @Override
    public void deleteFavorite(Favorites favorites) {
        Completable.fromAction(() -> mDatabase.favoritesDao().deleteFavorite(favorites))
                .subscribeOn(Schedulers.io())
                .onErrorComplete()
                .subscribe();
    }

    @Override
    public Completable validateUrl(String url) {
        CompletableSubject completable = CompletableSubject.create();

        if (Patterns.WEB_URL.matcher(url).matches()) {
            mDatabase.favoritesDao().getFavorite(url)
                    .subscribeOn(Schedulers.io())
                    .observeOn(Schedulers.io())
                    .subscribe((item) -> completable.onError(new RuntimeException(mContext.getString(R.string.already_favorited_url_error)))
                            , throwable -> completable.onComplete());
        } else {
            completable.onError(new RuntimeException(mContext.getString(R.string.ivalid_url_error)));
        }

        return completable;
    }

    @Override
    public void saveFavorite(Favorites favorites, String newTitle, String newUrl) {
        if (favorites.getWebsite().getUrl().equals(newUrl)) {
            Favorites editedFav = new Favorites(newTitle, favorites.getWebsite(), favorites.getDate());
            saveFavorite(editedFav);
        } else {
            Completable.fromAction(() -> {
                mDatabase.favoritesDao().deleteFavorite(favorites);
                Favorites newFav = new Favorites(favorites.getTitle(), new WebPage(newUrl), favorites.getDate());
                addNewFavorite(newFav);
            })
                    .subscribeOn(Schedulers.io())
                    .onErrorComplete()
                    .subscribe();
        }
    }
}
