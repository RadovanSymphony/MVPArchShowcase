package com.mg.kode.kodebrowser;

import android.app.Application;
import android.content.Context;

import com.mg.kode.kodebrowser.di.components.ApplicationComponent;
import com.mg.kode.kodebrowser.di.components.DaggerApplicationComponent;
import com.mg.kode.kodebrowser.di.modules.ApplicationModule;

import timber.log.Timber;


public class KodeApplication extends Application {

    private ApplicationComponent mApplicationComponent;

    private static Context mAppContext;

    @Override
    public void onCreate() {
        super.onCreate();
        mAppContext = this;

        if (BuildConfig.DEBUG) {
            Timber.plant(new Timber.DebugTree());
        }

        mApplicationComponent = DaggerApplicationComponent.builder()
                .applicationModule(new ApplicationModule(this))
                .build();
    }

    public ApplicationComponent getApplicationComponent() {
        return mApplicationComponent;
    }

    public static Context getAppContext() {
        return mAppContext;
    }
}
