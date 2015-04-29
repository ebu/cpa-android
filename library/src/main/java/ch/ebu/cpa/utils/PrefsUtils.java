package ch.ebu.cpa.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;

import ch.ebu.cpa.model.CpaClientToken;
import ch.ebu.cpa.model.CpaUserToken;

/**
 *  Copyright (c) European Broadcasting Union. All rights reserved.
 *
 *  Licence information is available from the LICENCE file.
 */
public class PrefsUtils {

    private static final String CPA_SHARED_PREF = "cpa_shared_preference";

    private static SharedPreferences prefs;

    public static void persistClientToken(Context context, CpaClientToken token, String domain) {
        SharedPreferences prefs = context.getSharedPreferences(CPA_SHARED_PREF, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        Gson gson = new Gson();
        String tokenString = gson.toJson(token);
        editor.putString(domain, tokenString);
        editor.apply();
    }

    public static CpaClientToken retreiveClientToken(Context context, String domain) {
        SharedPreferences prefs = context.getSharedPreferences(CPA_SHARED_PREF, Context.MODE_PRIVATE);
        if (!prefs.contains(domain)) {
            return null;
        } else {
            Gson gson = new Gson();
            return gson.fromJson(prefs.getString(domain, ""), CpaClientToken.class);
        }
    }

    private void persistUserToken(Context context, CpaUserToken token, String domain) {
        SharedPreferences prefs = context.getSharedPreferences(CPA_SHARED_PREF, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        Gson gson = new Gson();
        String tokenString = gson.toJson(token);
        editor.putString(domain, tokenString);
        editor.apply();
    }

    private CpaUserToken retreiveUserToken(Context context, String domain) {
        SharedPreferences prefs = context.getSharedPreferences(CPA_SHARED_PREF, Context.MODE_PRIVATE);
        if (!prefs.contains(domain)) {
            return null;
        } else {
            Gson gson = new Gson();
            return gson.fromJson(prefs.getString(domain, ""), CpaUserToken.class);
        }

    }

}
