package com.mg.kode.kodebrowser.ui.home.favorites;

import com.mg.kode.kodebrowser.data.FavoritesRepository;
import com.mg.kode.kodebrowser.data.model.Favorites;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Completable;
import io.reactivex.Observable;


public class FavoritesInteractor implements IFavoritesInteractor {

    private final FavoritesRepository mFavoritesRepo;

    @Inject
    public FavoritesInteractor(FavoritesRepository favoritesRepository) {
        mFavoritesRepo = favoritesRepository;
    }

    @Override
    public Observable<List<Favorites>> getAllFavorites() {
        return mFavoritesRepo.getAllFavorites();
    }

    @Override
    public void saveFavorite(Favorites favorite, String newTitle, String newUrl) {
        mFavoritesRepo.saveFavorite(favorite, newTitle, newUrl);
    }


    @Override
    public void saveFavorite(String title, String url) {
        mFavoritesRepo.addNewFavorite(title, url);
    }

    @Override
    public void deleteFavorite(Favorites favorite) {
        mFavoritesRepo.deleteFavorite(favorite);
    }

    @Override
    public Completable validateUrl(String url) {
        return mFavoritesRepo.validateUrl(url);
    }
}
