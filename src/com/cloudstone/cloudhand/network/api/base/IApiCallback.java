/**
 * @(#)IApiCallback.java, Jul 26, 2013. 
 * 
 */
package com.cloudstone.cloudhand.network.api.base;

import com.cloudstone.cloudhand.exception.ApiException;

/**
 * @author xuhongfeng
 *
 */
public interface IApiCallback<R> {
    public void onSuccess(R result);
    public void onFailed(ApiException exception);
    public void onFinish();
}
