/**
 * @(#)UrlConst.java, Jul 26, 2013. 
 * 
 */
package com.cloudstone.cloudhand.constant;

import com.cloudstone.cloudhand.logic.URLLogic;

/**
 * @author xuhongfeng
 *
 */
public class UrlConst {

//    public static final String BASE_URL = "http://192.168.0.101:8080/api";
	public static final String BASE_URL = URLLogic.getInstance().getURL().getUrl();
    
    public static final String LIST_USER_NAME_URL = BASE_URL + "/public/user-names";
    public static final String LOGIN_URL = BASE_URL + "/login";
    public static final String LIST_TABLE_URL = BASE_URL + "/tables";
}
