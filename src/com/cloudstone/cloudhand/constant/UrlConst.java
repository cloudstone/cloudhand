/**
 * @(#)UrlConst.java, Jul 26, 2013. 
 * 
 */
package com.cloudstone.cloudhand.constant;

/**
 * @author xuhongfeng
 *
 */
//TODO dont new this
public class UrlConst {
    
    public static final String DEFAULT_IP = "192.168.0.101";
    public static final int PORT = 8080;

	public static final String BASE_URL = "/api";
    
    public final String LIST_USER_NAME_URL = BASE_URL + "/public/user-names";
    public final String LOGIN_URL = BASE_URL + "/login";
    public final String LIST_DISH_URL = BASE_URL + "/dishes";
    
    public final String BASE_TABLE_URL = BASE_URL + "/tables";
    public final String LIST_TABLE_URL = BASE_TABLE_URL;
    private final String OCCUPY_TABLE_URL =  BASE_TABLE_URL + "/%d/occupy";
    
    public String occupyTableUrl(int tableId) {
        return String.format(OCCUPY_TABLE_URL, tableId);
    }
}
