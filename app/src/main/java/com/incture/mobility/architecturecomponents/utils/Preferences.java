package com.incture.mobility.architecturecomponents.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.incture.mobility.architecturecomponents.ArchitectureComponents;

/**
 * Created by satiswardash on 14/02/18.
 */

public class Preferences {

    private static SharedPreferences _sharedPreferences;

    private static final String PREFERENCE_NAME = ArchitectureComponents.class.getName()+"_PREFS";
    private static final String ACCESS_TOKEN = "ACCESS_TOKEN";
    public static final String EMPTY_STRING_DEFAULT_VALUE = "null";

    /**
     *
     * @param context
     */
    public static void init(Context context) {
        if (_sharedPreferences == null) {
            _sharedPreferences = context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE);
        }
    }

    /**
     *
     * @param accessToken
     */
    public static void setAccessToken(String accessToken) {
        SharedPreferences.Editor _editor = _sharedPreferences.edit();
        _editor.putString(ACCESS_TOKEN, accessToken);
        _editor.commit();
        _editor = null;
    }

    /**
     *
     * @return
     */
    public static String getAccessToken() {
        return _sharedPreferences.getString(ACCESS_TOKEN, EMPTY_STRING_DEFAULT_VALUE);
    }
}
