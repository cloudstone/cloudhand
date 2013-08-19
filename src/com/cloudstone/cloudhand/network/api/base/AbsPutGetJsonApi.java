/**
 * @(#)AbsPutGetJsonApi.java, Aug 19, 2013. 
 * 
 */
package com.cloudstone.cloudhand.network.api.base;

import org.apache.http.HttpResponse;

import com.cloudstone.cloudhand.data.IJson;
import com.cloudstone.cloudhand.exception.DecodeApiException;
import com.cloudstone.cloudhand.network.form.IForm;
import com.cloudstone.cloudhand.util.HttpUtils;
import com.cloudstone.cloudhand.util.JsonUtils;
import com.cloudstone.cloudhand.util.L;

/**
 * @author xuhongfeng
 *
 */
public abstract class AbsPutGetJsonApi<RESULT extends IJson, FORM extends IForm>
        extends AbsPutApi<RESULT, FORM>{
    

    public AbsPutGetJsonApi(String url, FORM form) {
        super(url, form);
    }


    @Override
    protected RESULT decodeResponse(HttpResponse response)
            throws DecodeApiException {
        String json = HttpUtils.responseToString(response);
        L.i(this, "HttpResponse : " + json);
        return JsonUtils.jsonToObject(json, getResultClass());
    }


    protected abstract Class<RESULT> getResultClass();
}
