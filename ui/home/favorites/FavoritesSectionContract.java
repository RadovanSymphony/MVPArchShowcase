package com.mg.kode.kodebrowser.ui.home.favorites;


import com.mg.kode.kodebrowser.data.model.Favorites;
import com.mg.kode.kodebrowser.ui.base.BaseContract;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Single;
import io.reactivex.annotations.NonNull;


public interface FavoritesSectionContract {

    interface FavoritesView extends BaseContract.View {
    	
        /**
         * Displays new list of favorites.
         *
         * @param favorites list of favorites to be displayed.
         */
        void setFavorites(List<Favorites> favorites);

        /**
         * Adds new favorite website to already existing list and displays it.
         *
         * @param newFavorite the new favorite website that has been added.
         */
        void newFavoriteAdded(Favorites newFavorite);

        /**
         * Removes a favorite website from display list.
         *
         * @param removedFavorite the favorite website that has been removed.
         */
        void favoriteRemoved(Favorites removedFavorite);

        /**
         * Displays 'add new favorite' dialog, presenting the user with 'add favorites' form.
         */
        void displayAddNewFavoriteDialog();
    }

    interface FavoritesPresenter<V extends FavoritesView> extends BaseContract.Presenter<V> {

        /**
         * Called when the user has clicked 'add new favorite' button.
         */
        void onAddNewFavoriteClicked();

        /**
         * Called when the user has submitted the 'add favorites' form with non-empty fields.
         *
         * @param title      favorite's title
         * @param urlAddress favorite's url address
         */
        void onAddFavoriteFormSubmitted(@NonNull String title, @NonNull String urlAddress);

        /**
         * Called when the user has submitted the 'edit favorites' form with non-empty fields.
         *
         * @param favorites favorite item that is edited.
         * @param newTitle  new title string.
         * @param newUrl    new url string.
         */
        void onEditFavoriteFormSubmitted(Favorites favorites, String newTitle, String newUrl);

        /**
         * Called when view is requesting URL input field form validation.
         * @param urlEntered user entered url.
         * @return {@link Completable} which will deliver error message if url is not valid, and complete if url is valid.
         */
        Completable validateFavoriteFormURLInputField(String urlEntered);

        /**
         * Called when the user has delete a favorite item.
         * @param favorites favorite item to be deleted.
         */
        void onDeleteFavoriteClicked(Favorites favorites);
    }
}
