/**
 * @(#)AbsPutApi.java, Aug 19, 2013. 
 * 
 */
package com.cloudstone.cloudhand.network.api.base;

import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.methods.HttpUriRequest;

import com.cloudstone.cloudhand.network.RequestParams;
import com.cloudstone.cloudhand.network.form.IForm;
import com.cloudstone.cloudhand.util.HttpUtils;

/**
 * @author xuhongfeng
 *
 */
public abstract class AbsPutApi<RESULT, FORM extends IForm> extends AbsApi<RESULT, FORM> {

    public AbsPutApi(String url, FORM form) {
        super(url, form);
    }

    @Override
    protected HttpUriRequest onCreateRequest() {
        HttpPut put = new HttpPut(getUrl());
//        RequestParams params = HttpUtils.generatePostParams(form);
//        put.setEntity(params.getEntity());
        setPutEntity(put);
        return put;
    }
    
    protected void setPutEntity(HttpPut put) {
        RequestParams params = HttpUtils.generatePostParams(form);
        put.setEntity(params.getEntity());
    }

}