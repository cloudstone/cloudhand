/**
 * @(#)AbsGetJsonArrayApi.java, Jul 26, 2013. 
 * 
 */
package com.cloudstone.cloudhand.network.api.base;

import java.util.List;

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
public abstract class AbsGetJsonArrayApi<RESULT extends IJson, FORM extends IForm> extends AbsGetApi<List<RESULT>, IForm> {
    
    public AbsGetJsonArrayApi(String url, IForm form) {
        super(url, form);
    }

    @Override
    protected final List<RESULT> decodeResponse(HttpResponse response)
            throws DecodeApiException {
        String json = HttpUtils.responseToString(response);
        return JsonUtils.jsonToList(json, getResultClass());
    }
    
    protected abstract Class<RESULT> getResultClass();
}