package com.mg.kode.kodebrowser.ui.home.favorites;

import com.mg.kode.kodebrowser.data.model.Favorites;
import java.util.List;
import io.reactivex.Completable;
import io.reactivex.Observable;

public interface IFavoritesInteractor {
	
    Observable<List<Favorites>> getAllFavorites();

    void saveFavorite(Favorites favorite, String newTitle, String newUrl);

    void saveFavorite(String title, String url);

    void deleteFavorite(Favorites favorite);

    Completable validateUrl(String url);
}
