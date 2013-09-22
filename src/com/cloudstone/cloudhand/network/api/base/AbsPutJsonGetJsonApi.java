package com.cloudstone.cloudhand.network.api.base;

import java.io.UnsupportedEncodingException;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.apache.http.protocol.HTTP;

import com.cloudstone.cloudhand.constant.EmptyConst;
import com.cloudstone.cloudhand.constant.EmptyConst.EmptyForm;
import com.cloudstone.cloudhand.data.IJson;
import com.cloudstone.cloudhand.exception.DecodeApiException;
import com.cloudstone.cloudhand.util.HttpUtils;
import com.cloudstone.cloudhand.util.JsonUtils;
import com.cloudstone.cloudhand.util.L;

/**
 * 
 * @author xhc
 *
 * @param <PAYLOAD>
 * @param <RESULT>
 */
public abstract class AbsPutJsonGetJsonApi<PAYLOAD extends IJson, RESULT extends IJson>
    extends AbsPutApi<RESULT, EmptyForm> {
    private final PAYLOAD payload;
    
    public AbsPutJsonGetJsonApi(String url, PAYLOAD payload) {
        super(url, EmptyConst.EmptyFormInstance);
        this.payload = payload;
    }
    
    @Override
    protected void setPutEntity(HttpPut put) {
        try {
            put.setEntity(new StringEntity(payload.toJson(), HTTP.UTF_8));
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
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
