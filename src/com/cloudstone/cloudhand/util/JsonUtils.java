/**
 * @(#)JsonUtils.java, Jul 26, 2013. 
 * 
 */
package com.cloudstone.cloudhand.util;

import java.lang.reflect.Type;
import java.util.List;

import com.cloudstone.cloudhand.data.IJson;
import com.google.gson.Gson;

/**
 * @author xuhongfeng
 *
 */
public class JsonUtils {
    private static final Gson GSON = new Gson();

    public static <T extends IJson> String objectToJson(T o) {
        return GSON.toJson(o);
    }
    
    public static <T extends IJson> T jsonToObject(String json, Class<T> clazz) {
        return GSON.fromJson(json, clazz);
    }
    
    public static <T extends IJson> List<T> jsonToList(String json, Type type) {
        return GSON.fromJson(json, type);
    }
}
