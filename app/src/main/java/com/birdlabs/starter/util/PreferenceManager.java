package com.birdlabs.starter.util;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;


/**
 * Stores and loads from the shared preferences
 * Created by bijoy on 10/9/15.
 */
public abstract class PreferenceManager {

    public Context context;

    abstract public String getPreferencesFolder();

    /**
     * Load the data from the shared preferences
     *
     * @param key the key of the data
     * @return the value stored or a default
     */
    public String load(String key) {
        return load(key, "");
    }

    /**
     * Load the data from the shared preferences
     *
     * @param key           the key of the data
     * @param defaultString the default string value
     * @return the value stored or a default
     */
    public String load(String key, String defaultString) {
        SharedPreferences sp = context.getSharedPreferences(
                getPreferencesFolder(), Activity.MODE_PRIVATE);
        return sp.getString(key, defaultString);
    }

    /**
     * Saves the data into the shared preferences
     *
     * @param key   the key of the data
     * @param value the value to store
     */
    public void save(String key, String value) {
        SharedPreferences sp = context.getSharedPreferences(
                getPreferencesFolder(), Activity.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString(key, value);
        editor.apply();
    }

    /**
     * Saves a boolean variable
     *
     * @param key  the key
     * @param bool the bool to store
     */
    public void save(String key, Boolean bool) {
        SharedPreferences sp = context.getSharedPreferences(
                getPreferencesFolder(), Activity.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putBoolean(key, bool);
        editor.apply();
    }

    /**
     * Saves a integer variable
     *
     * @param key     the key
     * @param integer the integer to store
     */
    public void save(String key, Integer integer) {
        SharedPreferences sp = context.getSharedPreferences(
                getPreferencesFolder(), Activity.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putInt(key, integer);
        editor.apply();
    }

    /**
     * Gets the stored boolean value
     *
     * @param key         the key
     * @param defaultBool boolean if it wasnt stored before or is not a bool
     * @return the stored value as boolean
     */
    public Boolean load(String key, Boolean defaultBool) {
        SharedPreferences sp = context.getSharedPreferences(
                getPreferencesFolder(), Activity.MODE_PRIVATE);
        return sp.getBoolean(key, defaultBool);
    }


    /**
     * Gets the stored integer value
     *
     * @param key            the key
     * @param defaultInteger integer if it wasnt stored before
     * @return the stored value as integer
     */
    public Integer load(String key, Integer defaultInteger) {
        SharedPreferences sp = context.getSharedPreferences(
                getPreferencesFolder(), Activity.MODE_PRIVATE);
        return sp.getInt(key, defaultInteger);
    }

    /**
     * Private Constructor
     *
     * @param context activity context
     */
    public PreferenceManager(Context context) {
        this.context = context;
    }

}
