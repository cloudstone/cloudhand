/**
 * @(#)PreferenceStore.java, Aug 9, 2013. 
 * 
 */
package com.cloudstone.cloudhand.storage;

import android.content.Context;
import android.content.SharedPreferences;

import com.cloudstone.cloudhand.Cloudhand;

/**
 * @author xuhongfeng
 *
 */
public class PreferenceStorage extends BaseStorage {
    private static final String PREF_COOKIE = "CookiePreference";
    
    private static PreferenceStorage me;
    
    private PreferenceStorage() {}
    
    public static PreferenceStorage getInstance() {
        if (me == null) {
            synchronized (PreferenceStorage.class) {
                if (me == null) {
                    me = new PreferenceStorage();
                }
            }
        }
        return me;
    }

    public SharedPreferences cookiePreference() {
        return Cloudhand.getInstance().getSharedPreferences(PREF_COOKIE, Context.MODE_PRIVATE);
    }
}