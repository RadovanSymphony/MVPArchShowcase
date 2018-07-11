package com.mg.kode.kodebrowser.data;


import com.mg.kode.kodebrowser.data.model.Favorites;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Observable;
import io.reactivex.annotations.NonNull;


/**
 * Repository class for dealing with {@link com.mg.kode.kodebrowser.data.model.Favorites}. Provides basic loading/saving mechanism for Favorites.
 */
public interface FavoritesRepository {
	
    /**
     * Retrieves all favorites saved on current device or for current logged in user.
     *
     * @return observable emitting list of {@link Favorites}.
     */
    @NonNull
    Observable<List<Favorites>> getAllFavorites();

    /**
     * Saves favorite website on current device or for current logged in user.
     *
     * @param favorites {@link Favorites} model containing the website to be saved.
     * @param newTitle  new favorite's title.
     * @param newUrl    new favorite's url.
     */
    void saveFavorite(Favorites favorites, String newTitle, String newUrl);

    /**
     * Saves new {@link Favorites} website on current device or for current logged in user.
     *
     * @param title favorite's title
     * @param url   favorite's url
     */
    void addNewFavorite(String title, String url);

    /**
     * Deletes {@link Favorites} from the current device or for current logged in user.
     * @param favorites favorite to delete.
     */
    void deleteFavorite(Favorites favorites);

    /**
     * Validates favorite's url.
     * @param url
     * @return {@link Completable} which will complete if url is valida and return an error with {@link RuntimeException#getMessage()} returning invalid url reason.
     */
    Completable validateUrl(String url);
}
