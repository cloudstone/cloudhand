package com.cloudstone.cloudhand.network.api.base;

import org.apache.http.HttpResponse;

import com.cloudstone.cloudhand.data.IJson;
import com.cloudstone.cloudhand.exception.DecodeApiException;
import com.cloudstone.cloudhand.network.form.IForm;
import com.cloudstone.cloudhand.util.HttpUtils;
import com.cloudstone.cloudhand.util.JsonUtils;
import com.cloudstone.cloudhand.util.L;

public abstract class AbsGetJsonApi<RESULT extends IJson, FORM extends IForm> extends AbsGetApi<RESULT, IForm> {

    public AbsGetJsonApi(String url, IForm form) {
        super(url, form);
    }

    @Override
    protected final RESULT decodeResponse(HttpResponse response)
            throws DecodeApiException {
        String json = HttpUtils.responseToString(response);
        L.i(this, "HttpResponse : " + json);
        return JsonUtils.jsonToObject(json, getResultClass());
    }
    
    protected abstract Class<RESULT> getResultClass();

}
