package com.mg.kode.kodebrowser.utils;

import android.support.annotation.StringRes;

import com.mg.kode.kodebrowser.KodeApplication;


public class StringUtils {

    public static String getString(@StringRes int stringRes) {
        return KodeApplication.getAppContext().getString(stringRes);
    }
}
