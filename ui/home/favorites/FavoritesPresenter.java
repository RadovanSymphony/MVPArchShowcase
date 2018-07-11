package com.mg.kode.kodebrowser.ui.home.favorites;


import com.mg.kode.kodebrowser.data.model.Favorites;
import com.mg.kode.kodebrowser.ui.base.BasePresenter;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Completable;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;


public class FavoritesPresenter<V extends FavoritesSectionContract.FavoritesView> extends BasePresenter<V> implements FavoritesSectionContract.FavoritesPresenter<V> {

    private IFavoritesInteractor mInteractor;

    @Inject
    public FavoritesPresenter(IFavoritesInteractor favoritesInteractor) {
        mInteractor = favoritesInteractor;
    }

    @Override
    public void onAttach(V view) {
        super.onAttach(view);
        mInteractor
                .getAllFavorites()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<List<Favorites>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        getCompositeDisposable().add(d);
                    }

                    @Override
                    public void onNext(List<Favorites> favorites) {
                        if (isViewAttached())
                            getView().setFavorites(favorites);
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    @Override
    public void onAddNewFavoriteClicked() {
        getView().displayAddNewFavoriteDialog();
    }

    @Override
    public void onAddFavoriteFormSubmitted(@NonNull String title, @NonNull String urlAddress) {
        mInteractor.saveFavorite(title, urlAddress);
    }

    @Override
    public void onEditFavoriteFormSubmitted(Favorites favorites, String newTitle, String newUrl) {
        mInteractor.saveFavorite(favorites, newTitle, newUrl);
    }

    @Override
    public Completable validateFavoriteFormURLInputField(String urlEntered) {
        return mInteractor.validateUrl(urlEntered);
    }

    @Override
    public void onDeleteFavoriteClicked(Favorites favorites) {
        mInteractor.deleteFavorite(favorites);
    }
}
