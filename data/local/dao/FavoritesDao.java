package com.mg.kode.kodebrowser.data.local.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.mg.kode.kodebrowser.data.model.Favorites;

import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.Single;


@Dao
public interface FavoritesDao {

    @Query("SELECT * FROM favorite_page ORDER BY favorite_page.date ASC")
    List<Favorites> getFavorites();

    @Query("SELECT * FROM favorite_page ORDER BY favorite_page.date ASC")
    Flowable<List<Favorites>> getFavoritesObservable();

    @Query("SELECT * FROM favorite_page WHERE favorite_page.webpage_url = :url")
    Single<Favorites> getFavorite(String url);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void saveFavorite(Favorites... favorites);

    @Delete
    void deleteFavorite(Favorites... favorites);
}
