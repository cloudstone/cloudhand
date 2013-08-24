/**
 * @(#)AbsPostJsonGetJsonApi.java, Aug 24, 2013. 
 * 
 */
package com.cloudstone.cloudhand.network.api.base;

import java.io.UnsupportedEncodingException;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;

import com.cloudstone.cloudhand.constant.EmptyConst;
import com.cloudstone.cloudhand.constant.EmptyConst.EmptyForm;
import com.cloudstone.cloudhand.data.IJson;
import com.cloudstone.cloudhand.exception.DecodeApiException;
import com.cloudstone.cloudhand.util.HttpUtils;
import com.cloudstone.cloudhand.util.JsonUtils;
import com.cloudstone.cloudhand.util.L;

/**
 * @author xuhongfeng
 *
 */
public abstract class AbsPostJsonGetJsonApi<PAYLOAD extends IJson, RESULT extends IJson>
    extends AbsPostApi<RESULT, EmptyForm> {
    private final PAYLOAD payload;

    public AbsPostJsonGetJsonApi(String url, PAYLOAD payload) {
        super(url, EmptyConst.EmptyFormInstance);
        this.payload = payload;
    }
    
    @Override
    protected void setPostEntity(HttpPost post) {
        try {
            post.setEntity(new StringEntity(payload.toJson()));
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected final RESULT decodeResponse(HttpResponse response) throws DecodeApiException {
        String json = HttpUtils.responseToString(response);
        L.i(this, "HttpResponse : " + json);
        return JsonUtils.jsonToObject(json, getResultClass());
    }

    protected abstract Class<RESULT> getResultClass();
}
