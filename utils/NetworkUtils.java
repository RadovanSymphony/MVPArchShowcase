package com.mg.kode.kodebrowser.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.mg.kode.kodebrowser.KodeApplication;


public class NetworkUtils {

    /**
     * Checks if network connection is established.
     *
     * @return true if the device is connected to the internet.
     */
    public static boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) KodeApplication.getAppContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        if (cm != null) {
            NetworkInfo info = cm.getActiveNetworkInfo();
            return (info != null && info.isConnected());
        } else {
            return false;
        }

    }
}
