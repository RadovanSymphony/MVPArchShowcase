package com.mg.kode.kodebrowser.data;

import android.util.Patterns;

import com.mg.kode.kodebrowser.BuildConfig;
import com.mg.kode.kodebrowser.data.local.KodeDatabase;
import com.mg.kode.kodebrowser.data.local.PreferenceManager;
import com.mg.kode.kodebrowser.data.model.History;
import com.mg.kode.kodebrowser.data.model.SearchEngine;
import com.mg.kode.kodebrowser.data.network.GoogleAPI;

import org.json.JSONArray;
import org.json.JSONException;
import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Completable;
import io.reactivex.Emitter;
import io.reactivex.FlowableSubscriber;
import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


@Singleton
public class DefaultSearchRepository implements SearchRepository {

    private final GoogleAPI mGoogleAPI;
    private final KodeDatabase mDatabase;
    private final PreferenceManager mPreferenceManager;
    private Emitter<List<History>> mSearchHistoryEmitter;
    private Emitter<List<String>> mSearchSuggestionsEmitter;
    private SearchEngine mDefaultSearchEngine;

    @Inject
    public DefaultSearchRepository(KodeDatabase database, GoogleAPI restapi, PreferenceManager preferenceManager) {
        mGoogleAPI = restapi;
        mDatabase = database;
        mPreferenceManager = preferenceManager;

        if (mPreferenceManager.getDefaultSearchEngine() == null) {
            mDefaultSearchEngine = createDefaultSearchEngine();
            Completable.fromAction(() -> {
                mDatabase.searchDao().saveSearchEngine(mDefaultSearchEngine);
            })
                    .andThen(mDatabase.searchDao().getSearchEngines())
                    .subscribeOn(Schedulers.io())
                    .subscribe(new FlowableSubscriber<List<SearchEngine>>() {
                        @Override
                        public void onSubscribe(Subscription s) {
                            s.request(1);
                        }

                        @Override
                        public void onNext(List<SearchEngine> searchEngines) {
                            mDefaultSearchEngine = searchEngines.get(0);
                            mPreferenceManager.setDefaultSearchEngine(mDefaultSearchEngine);
                        }

                        @Override
                        public void onError(Throwable t) {

                        }

                        @Override
                        public void onComplete() {

                        }
                    });
        } else {
            mDefaultSearchEngine = mPreferenceManager.getDefaultSearchEngine();
        }
    }

    @Override
    public Observable<List<SearchEngine>> getAllEngines() {
        return mDatabase.searchDao().getSearchEngines().toObservable();
    }

    @Override
    public void addNewEngine(String title, String url) {
        SearchEngine searchEngine = new SearchEngine(title, url);
        Completable.fromAction(() -> mDatabase.searchDao().saveSearchEngine(searchEngine))
                .subscribeOn(Schedulers.io())
                .onErrorComplete()
                .subscribe();
    }

    @Override
    public Observable<List<String>> getSearchSuggestions() {
        return Observable.create(emitter -> mSearchSuggestionsEmitter = emitter);
    }

    @Override
    public Observable<List<History>> getHistory() {
        return Observable.create(emitter -> mSearchHistoryEmitter = emitter);
    }

    @Override
    public void searchQueryChanged(String query) {
        Completable.fromAction(() -> {
            if (mSearchHistoryEmitter != null) {
                mSearchHistoryEmitter.onNext(mDatabase.searchDao().getHistory(query));
            }
            getAutocompleteSearchSuggestions(query);
        })
                .subscribeOn(Schedulers.io())
                .onErrorComplete()
                .subscribe();
    }

    @Override
    public void searchQueryExecuted(String query) {
        Completable.fromAction(() -> {
            History history;
            if (Patterns.WEB_URL.matcher(query).matches()) {
                history = new History(query, query);
            } else {
                history = new History(mDefaultSearchEngine.getSearchQueryTitle(query), mDefaultSearchEngine.getSearchQueryURL(query));
            }
            mDatabase.searchDao().saveHistory(history);
        })
                .subscribeOn(Schedulers.io())
                .onErrorComplete()
                .subscribe();
    }

    @Override
    public void searchQueryExecuted(History history) {
        Completable.fromAction(() -> {
            History newHistory = new History(history.getTitle(), history.getUrl());
            mDatabase.searchDao().saveHistory(newHistory);
        })
                .subscribeOn(Schedulers.io())
                .onErrorComplete()
                .subscribe();
    }

    @Override
    public void searchQueryExecuted(String query, SearchEngine engine) {
//        History history = new History(query, engine, new WebPage(""));
//        Completable.fromAction(() -> mDatabase.searchDao().saveHistory(history))
//                .subscribeOn(Schedulers.io())
//                .subscribe();
    }

    private void getAutocompleteSearchSuggestions(String query) {
        mGoogleAPI.getSearchAutocomplete(query).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful() && response.body() != null) {
                    ResponseBody body = response.body();
                    try {
                        // autocompleteOptions => ["tayl",["taylor swift","taylor lautner",...
                        String autocompleteOptions = body.string();
                        JSONArray jsonArray = new JSONArray(autocompleteOptions).getJSONArray(1);
                        // list => "taylor swift","taylor lautner",...
                        List<String> results = parseAutocompleteSuggestions(jsonArray);
                        if (mSearchSuggestionsEmitter != null) {
                            mSearchSuggestionsEmitter.onNext(results);
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                } else {
                    // ignore error
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                // ignore error
            }
        });
    }

    private List<String> parseAutocompleteSuggestions(JSONArray jsonArray) throws JSONException {
        ArrayList<String> list = new ArrayList<>();
        if (jsonArray != null) {
            for (int i = 0; i < jsonArray.length(); i++) {
                list.add(jsonArray.get(i).toString());
            }
        }
        return list;
    }

    private SearchEngine createDefaultSearchEngine() {
        SearchEngine e = new SearchEngine(BuildConfig.DEFAULT_SEARCH_ENGINE_NAME, BuildConfig.DEFAULT_SEARCH_ENGINE_URL);
        return e;
    }
}
