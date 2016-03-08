package ch.ebu.cpa.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;

import ch.ebu.cpa.model.CpaClientIdentity;
import ch.ebu.cpa.model.CpaClientToken;
import ch.ebu.cpa.model.CpaUserToken;

/**
 * Copyright (c) European Broadcasting Union. All rights reserved.
 * <p/>
 * Licence information is available from the LICENCE file.
 */
public class PrefsUtils {

    private static final String CPA_SHARED_PREF = "cpa_shared_preference";
    private static final String CPA_SHARED_IDENTITY_KEY = "cpa_shared_preference_client_identity_key";

    public static void persistClientToken(Context context, CpaClientToken token, String domain) {
        SharedPreferences prefs = context.getSharedPreferences(CPA_SHARED_PREF, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        Gson gson = new Gson();
        String tokenString = gson.toJson(token);
        editor.putString(domain, tokenString);
        editor.apply();
    }

    public static CpaClientToken retrieveClientToken(Context context, String domain) {
        SharedPreferences prefs = context.getSharedPreferences(CPA_SHARED_PREF, Context.MODE_PRIVATE);
        if (!prefs.contains(domain)) {
            return null;
        } else {
            Gson gson = new Gson();
            return gson.fromJson(prefs.getString(domain, ""), CpaClientToken.class);
        }
    }

    public static void persistUserToken(Context context, CpaUserToken token, String domain) {
        SharedPreferences prefs = context.getSharedPreferences(CPA_SHARED_PREF, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        Gson gson = new Gson();
        String tokenString = gson.toJson(token);
        editor.putString(domain, tokenString);
        editor.apply();
    }

    public static CpaUserToken retrieveUserToken(Context context, String domain) {
        SharedPreferences prefs = context.getSharedPreferences(CPA_SHARED_PREF, Context.MODE_PRIVATE);
        if (!prefs.contains(domain)) {
            return null;
        } else {
            Gson gson = new Gson();
            return gson.fromJson(prefs.getString(domain, ""), CpaUserToken.class);
        }

    }

    public static void persistIdentity(Context context, CpaClientIdentity identity) {
        SharedPreferences prefs = context.getSharedPreferences(CPA_SHARED_PREF, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        Gson gson = new Gson();
        String tokenString = gson.toJson(identity);
        editor.putString(CPA_SHARED_IDENTITY_KEY, tokenString);
        editor.apply();
    }

    public static CpaClientIdentity retrieveIdentity(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(CPA_SHARED_PREF, Context.MODE_PRIVATE);
        if (!prefs.contains(CPA_SHARED_IDENTITY_KEY)) {
            return null;
        } else {
            Gson gson = new Gson();
            return gson.fromJson(prefs.getString(CPA_SHARED_IDENTITY_KEY, ""), CpaClientIdentity.class);
        }

    }

    public static void clearAllToken(Context context) {
        context.getSharedPreferences(CPA_SHARED_PREF, Context.MODE_PRIVATE).edit().clear().apply();
    }

}
