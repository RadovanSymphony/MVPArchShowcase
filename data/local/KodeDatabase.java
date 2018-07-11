package com.mg.kode.kodebrowser.data.local;


import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import com.mg.kode.kodebrowser.data.local.dao.FavoritesDao;
import com.mg.kode.kodebrowser.data.local.dao.SearchDao;
import com.mg.kode.kodebrowser.data.model.Favorites;
import com.mg.kode.kodebrowser.data.model.SearchEngine;
import com.mg.kode.kodebrowser.data.model.History;

import javax.inject.Singleton;


@Database(version = 4, exportSchema = false, entities = {Favorites.class, SearchEngine.class, History.class})
public abstract class KodeDatabase extends RoomDatabase {

    public abstract FavoritesDao favoritesDao();
    
    public abstract SearchDao searchDao();
}
