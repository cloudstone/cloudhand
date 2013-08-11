/**
 * @(#)MyCookieStore.java, Aug 9, 2013. 
 * 
 */
package com.cloudstone.cloudhand.network;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.http.client.CookieStore;
import org.apache.http.cookie.Cookie;

import android.content.SharedPreferences;
import android.text.TextUtils;

import com.cloudstone.cloudhand.storage.PreferenceStorage;

/**
 * A persistent cookie store which implements the Apache HttpClient
 * {@link CookieStore} interface. Cookies are stored and will persist on the
 * user's device between application sessions since they are serialized and
 * stored in {@link SharedPreferences}.
 * <p>
 * Instances of this class are designed to be used with
 * {@link AsyncHttpClient#setCookieStore}, but can also be used with a
 * regular old apache HttpClient/HttpContext if you prefer.
 */
public class MyCookieStore implements CookieStore {
    private static final String COOKIE_NAME_STORE = "names";
    private static final String COOKIE_NAME_PREFIX = "cookie_";

    private final ConcurrentHashMap<String, Cookie> cookies;
    private final SharedPreferences cookiePrefs;

    private static MyCookieStore me;

    public static MyCookieStore getInstance() {
        if(me == null) {
            synchronized (MyCookieStore.class) {
                if(me == null) {
                    me = new MyCookieStore();
                }
            }
        }
        return me;
    }

    /**
     * Construct a persistent cookie store.
     */
    private MyCookieStore() {
        cookiePrefs = PreferenceStorage.getInstance().cookiePreference();
        cookies = new ConcurrentHashMap<String, Cookie>();

        // Load any previously stored cookies into the store
        String storedCookieNames = cookiePrefs.getString(COOKIE_NAME_STORE, null);
        if(storedCookieNames != null) {
            String[] cookieNames = TextUtils.split(storedCookieNames, ",");
            for(String name : cookieNames) {
                String encodedCookie = cookiePrefs.getString(COOKIE_NAME_PREFIX + name, null);
                if(encodedCookie != null) {
                    Cookie decodedCookie = decodeCookie(encodedCookie);
                    if(decodedCookie != null) {
                        cookies.put(name, decodedCookie);
                    }
                }
            }

            // Clear out expired cookies
            clearExpired(new Date());
        }
    }

    @Override
    public void addCookie(Cookie cookie) {
        String name = cookie.getName();
//        L.e(this, "add cookie:"+name);

        // Save cookie into local store
        cookies.put(name, cookie);

        // Save cookie into persistent store
        SharedPreferences.Editor prefsWriter = cookiePrefs.edit();
        prefsWriter.putString(COOKIE_NAME_STORE, TextUtils.join(",", cookies.keySet()));
        prefsWriter.putString(COOKIE_NAME_PREFIX + name, encodeCookie(new SerializableCookie(cookie)));
        prefsWriter.commit();
    }

    @Override
    public void clear() {
        // Clear cookies from local store
        cookies.clear();

        // Clear cookies from persistent store
        SharedPreferences.Editor prefsWriter = cookiePrefs.edit();
        for(String name : cookies.keySet()) {
            prefsWriter.remove(COOKIE_NAME_PREFIX + name);
        }
        prefsWriter.remove(COOKIE_NAME_STORE);
        prefsWriter.commit();
    }

    @Override
    public boolean clearExpired(Date date) {
        boolean clearedAny = false;
        SharedPreferences.Editor prefsWriter = cookiePrefs.edit();

        for(ConcurrentHashMap.Entry<String, Cookie> entry : cookies.entrySet()) {
            String name = entry.getKey();
            Cookie cookie = entry.getValue();
            if(cookie.isExpired(date)) {
                // Clear cookies from local store
                cookies.remove(name);

                // Clear cookies from persistent store
                prefsWriter.remove(COOKIE_NAME_PREFIX + name);

                // We've cleared at least one
                clearedAny = true;
            }
        }

        // Update names in persistent store
        if(clearedAny) {
            prefsWriter.putString(COOKIE_NAME_STORE, TextUtils.join(",", cookies.keySet()));
        }
        prefsWriter.commit();

        return clearedAny;
    }

    @Override
    public List<Cookie> getCookies() {
//        L.e(this, "cookies.size="+cookies.values().size());
        Date now = new Date();
        List<Cookie> list = new ArrayList<Cookie>(cookies.values());
        Iterator<Cookie> it = list.iterator();
        boolean clearExpired = false;
        while (it.hasNext()) {
            Cookie c = it.next();
            if (c.isExpired(now)) {
                clearExpired = true;
                it.remove();
            }
        }
        if (clearExpired) {
            clearExpired(now);
        }
        return list;
    }

    //
    // Cookie serialization/deserialization
    //

    protected String encodeCookie(SerializableCookie cookie) {
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        try {
            ObjectOutputStream outputStream = new ObjectOutputStream(os);
            outputStream.writeObject(cookie);
        } catch (Exception e) {
            return null;
        }

        return byteArrayToHexString(os.toByteArray());
    }

    protected Cookie decodeCookie(String cookieStr) {
        byte[] bytes = hexStringToByteArray(cookieStr);
        ByteArrayInputStream is = new ByteArrayInputStream(bytes);
        Cookie cookie = null;
        try {
           ObjectInputStream ois = new ObjectInputStream(is);
           cookie = ((SerializableCookie)ois.readObject()).getCookie();
        } catch (Exception e) {
           e.printStackTrace();
        }

        return cookie;
    }
    // Using some super basic byte array <-> hex conversions so we don't have
    // to rely on any large Base64 libraries. Can be overridden if you like!
    protected String byteArrayToHexString(byte[] b) {
        StringBuffer sb = new StringBuffer(b.length * 2);
        for (byte element : b) {
            int v = element & 0xff;
            if(v < 16) {
                sb.append('0');
            }
            sb.append(Integer.toHexString(v));
        }
        return sb.toString().toUpperCase();
    }

    protected byte[] hexStringToByteArray(String s) {
        int len = s.length();
        byte[] data = new byte[len / 2];
        for(int i=0; i<len; i+=2) {
            data[i / 2] = (byte) ((Character.digit(s.charAt(i), 16) << 4) + Character.digit(s.charAt(i+1), 16));
        }
        return data;
    }
}