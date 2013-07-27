/**
 * @(#)IServerApi.java, Jul 26, 2013. 
 * 
 */
package com.cloudstone.cloudhand.network.api.base;

import com.cloudstone.cloudhand.exception.ApiException;

/**
 * @author xuhongfeng
 *
 */
public interface IServerApi<R> {
    public R call() throws ApiException;
    public void asyncCall(IApiCallback<R> callback);
}
