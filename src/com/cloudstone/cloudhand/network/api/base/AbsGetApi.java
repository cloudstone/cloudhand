/**
 * @(#)AbsGetApi.java, Jul 26, 2013. 
 * 
 */
package com.cloudstone.cloudhand.network.api.base;

import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;

import com.cloudstone.cloudhand.network.form.IForm;
import com.cloudstone.cloudhand.util.HttpUtils;

/**
 * @author xuhongfeng
 *
 */
public abstract class AbsGetApi<RESULT, FORM extends IForm> extends AbsApi<RESULT, FORM> {

    public AbsGetApi(String url, FORM form) {
        super(url, form);
    }
    
    @Override
    protected String getUrl() {
        String baseUrl = super.getUrl();
        return HttpUtils.generateGetUrl(baseUrl, form);
    }

    @Override
    protected HttpUriRequest onCreateRequest() {
        return new HttpGet(getUrl());
    }
}
