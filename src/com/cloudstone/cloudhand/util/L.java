/**
 * @(#)L.java, Aug 3, 2013. 
 * 
 */
package com.cloudstone.cloudhand.util;

import android.util.Log;



/**
 * @author xuhongfeng
 *
 */
public class L {
    
    public static void i(Object tag, String message) {
        i(tag, message, null);
    }
    
    public static void i(Object tag, Throwable e) {
        i(tag, "", e);
    }
    
    public static void i(Object tag, String message, Throwable e) {
        if (e != null) {
            Log.i(getTag(tag), message, e);
        } else {
            Log.i(getTag(tag), message);
        }
    }
    
    public static void e(Object tag, String message) {
        e(tag, message, null);
    }
    
    public static void e(Object tag, Throwable e) {
        e(tag, "", e);
    }
    
    public static void e(Object tag, String message, Throwable e) {
        if (e != null) {
            Log.e(getTag(tag), message, e);
        } else {
            Log.e(getTag(tag), message);
        }
    }
    
    private static String getTag(Object tag) {
        if (tag.getClass() == String.class) {
            return (String) tag;
        }
        Class<?> clazz = null;
        if (tag instanceof Class<?>) {
            clazz = (Class<?>) tag;
        } else {
            clazz = tag.getClass();
        }
        return clazz.getSimpleName();
    }
}
