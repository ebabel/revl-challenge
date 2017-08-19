package com.revl.babel.challenge.util;

import android.content.Context;
import android.content.SharedPreferences;

import com.revl.babel.challenge.RevlApplication;

public class RevlSharedPreferences {
    private static final String SEARCH_STRING = "SEARCH_STRING";
    private static SharedPreferences sharedPreferences;

    protected static SharedPreferences getSharedPreferences() {
        if (sharedPreferences == null) {
            sharedPreferences = RevlApplication.getInstance()
                    .getSharedPreferences(RevlApplication.getInstance().getPackageName(), Context.MODE_PRIVATE);
        }
        return sharedPreferences;
    }

    public static void saveSearchString(String searchString) {
        getSharedPreferences().edit().putString(SEARCH_STRING, searchString).apply();
    }

    public static String getSearchString() {
        return getSharedPreferences().getString(SEARCH_STRING, "kiteboarding");
    }
}
