/**
 * @(#)ListUserApi.java, Jul 26, 2013. 
 * 
 */
package com.cloudstone.cloudhand.network.api;

import com.cloudstone.cloudhand.constant.EmptyConst;
import com.cloudstone.cloudhand.data.User;
import com.cloudstone.cloudhand.network.api.base.AbsGetJsonArrayApi;

/**
 * @author xuhongfeng
 *
 */
public class ListUserApi extends AbsGetJsonArrayApi<User, EmptyConst.EmptyForm> {

    public ListUserApi(String url) {
        super(url, EmptyConst.EmptyFormInstance);
    }

    @Override
    protected Class<User> getResultClass() {
        return User.class;
    }
}