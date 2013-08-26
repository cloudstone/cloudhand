/**
 * @(#)PreferenceStore.java, Aug 9, 2013. 
 * 
 */
package com.cloudstone.cloudhand.storage;

import android.content.Context;
import android.content.SharedPreferences;

import com.cloudstone.cloudhand.Cloudhand;
import com.cloudstone.cloudhand.constant.UrlConst;
import com.cloudstone.cloudhand.data.IJson;
import com.cloudstone.cloudhand.data.User;
import com.cloudstone.cloudhand.util.JsonUtils;

/**
 * @author xuhongfeng
 *
 */
public class PreferenceStorage extends BaseStorage {
    private static final String PREF_COOKIE = "CookiePreference";
    private static final String PREF_MAIN = "MainPreference";
    
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
    
    private SharedPreferences preferences() {
        return Cloudhand.getInstance().getSharedPreferences(PREF_MAIN,
                Context.MODE_PRIVATE);
    }
    
    private <T extends IJson> T readJson(String key, Class<T> clazz) {
        if (!preferences().contains(key)) {
            return  null;
        }
        String json = preferences().getString(key, "");
        return JsonUtils.jsonToObject(json, clazz);
    }
    
    private <T extends IJson> void writeJson(String key, T value) {
        if (value == null) {
            preferences().edit().remove(key).commit();
        } else {
            preferences().edit().putString(key, value.toJson()).commit();
        }
    }
    
    /* ---------- preferences ---------- */
    private static final String KEY_USER = "user";
    private static final String KEY_IP = "ip";
    private static final String KEY_CURRENT_USER = "current_user";
    private static final String KEY_PASSWORD_PREFIX = "pw_";
    
    public String getIp() {
        return preferences().getString(KEY_IP, UrlConst.DEFAULT_IP);
    }
    
    public User getUser() {
        return readJson(KEY_USER, User.class);
    }
    
    public void setUser(User user) {
        writeJson(KEY_USER, user);
    }
    
    public String getPassword(String userName) {
        return preferences().getString(KEY_PASSWORD_PREFIX + userName, "");
    }
    
    public void setPassword(String userName, String password) {
        preferences().edit().putString(KEY_PASSWORD_PREFIX + userName, password).commit();
    }
    
    public void removePassword(String userName) {
        preferences().edit().remove(KEY_PASSWORD_PREFIX + userName).commit();
    }
    
    public String getCurrentUser() {
        return preferences().getString(KEY_CURRENT_USER, "");
    }
    
    public void setCurrentUser(String userName) {
        preferences().edit().putString(KEY_CURRENT_USER, userName).commit();
    }
}