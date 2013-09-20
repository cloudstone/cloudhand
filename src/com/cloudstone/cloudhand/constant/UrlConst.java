/**
 * @(#)UrlConst.java, Jul 26, 2013. 
 * 
 */
package com.cloudstone.cloudhand.constant;

/**
 * @author xuhongfeng
 *
 */
public class UrlConst {
    
    public static final String DEFAULT_IP = "192.168.0.101";
    public static final int PORT = 8080;

    public static final String BASE_URL = "/api";
    
    public static final String LIST_USER_NAME_URL = BASE_URL + "/public/user-names";
    public static final String LOGIN_URL = BASE_URL + "/login";
    public static final String LIST_DISH_URL = BASE_URL + "/dishes";
    public static final String LIST_DISH_NOTE_URL = BASE_URL + "/dish/notes";
    public final String BASE_TABLE_URL = BASE_URL + "/tables";
    public final String LIST_TABLE_URL = BASE_TABLE_URL;
    private final String OCCUPY_TABLE_URL =  BASE_TABLE_URL + "/%d/occupy";
    public static final String SUBMIT_ORDER_URL = BASE_URL + "/orders";
    public static final String GET_ORDER_URL = BASE_URL + "/orders/";
    
    public String occupyTableUrl(int tableId) {
        return String.format(OCCUPY_TABLE_URL, tableId);
    }
    
    private final String CLEAR_TABLE_URL =  BASE_TABLE_URL + "/%d/clear";
    
    public String clearTableUrl(int tableId) {
        return String.format(CLEAR_TABLE_URL, tableId);
    }
    
    public static final String LIST_DISH_DOTE_URL = BASE_URL + "/dishes/notes";
}
