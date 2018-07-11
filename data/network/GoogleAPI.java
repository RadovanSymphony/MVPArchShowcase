package com.mg.kode.kodebrowser.data.network;


import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;


public interface GoogleAPI {
	
    @GET("/complete/search?output=firefox&hl=en&")
    Call<ResponseBody> getSearchAutocomplete(@Query("q") String query);
}
