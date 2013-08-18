/**
 * @(#)ListUserApi.java, Jul 26, 2013. 
 * 
 */
package com.cloudstone.cloudhand.network.api;

import com.cloudstone.cloudhand.constant.EmptyConst;
import com.cloudstone.cloudhand.constant.UrlConst;
import com.cloudstone.cloudhand.network.api.base.AbsGetStringArrayApi;

/**
 * @author xuhongfeng
 *
 */
public class ListUserNameApi extends AbsGetStringArrayApi<EmptyConst.EmptyForm> {

    public ListUserNameApi() {
        super(new UrlConst().LIST_USER_NAME_URL, EmptyConst.EmptyFormInstance);
    }
}