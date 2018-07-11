package com.mg.kode.kodebrowser.data.network;

import android.content.Context;

import java.io.File;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.mg.kode.kodebrowser.utils.Constants.CACHE_FOLDER;


public class RetrofitClient {
    
    private static final int CACHE_SIZE = 10 * 1024 * 1024; // 10Mib
    public static final int TIME_OUT_SECONDS = 30;

    public GoogleAPI getGoogleAPI(final Context appContext) {
        final HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        File httpCacheDirectory = new File(appContext.getCacheDir(), CACHE_FOLDER);
        Cache cache = new Cache(httpCacheDirectory, CACHE_SIZE);

        OkHttpClient okHttpClient = new OkHttpClient.Builder()

                .addNetworkInterceptor(loggingInterceptor)
                .readTimeout(TIME_OUT_SECONDS, TimeUnit.SECONDS)
                .connectTimeout(TIME_OUT_SECONDS, TimeUnit.SECONDS)
                .cache(cache)
                .build();


        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://suggestqueries.google.com")
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        return retrofit.create(GoogleAPI.class);
    }

}
