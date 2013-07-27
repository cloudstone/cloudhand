/**
 * @(#)AbsPostFormGetJsonApi.java, Jul 26, 2013. 
 * 
 */
package com.cloudstone.cloudhand.network.api.base;

import org.apache.http.HttpResponse;

import com.cloudstone.cloudhand.data.IJson;
import com.cloudstone.cloudhand.exception.DecodeApiException;
import com.cloudstone.cloudhand.network.form.IForm;
import com.cloudstone.cloudhand.util.HttpUtils;
import com.cloudstone.cloudhand.util.JsonUtils;

/**
 * @author xuhongfeng
 *
 */
public abstract class AbsPostFormGetJsonApi<RESULT extends IJson, FORM extends IForm> extends AbsPostApi<RESULT, FORM> {
    public AbsPostFormGetJsonApi(String url, FORM form) {
        super(url, form);
    }

    @Override
    protected final RESULT decodeResponse(HttpResponse response) throws DecodeApiException {
        String json = HttpUtils.responseToString(response);
        return JsonUtils.jsonToObject(json, getResultClass());
    }

    protected abstract Class<RESULT> getResultClass();
}
