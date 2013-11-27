/**
 * @(#)AbsGetStringArrayApi.java, Aug 3, 2013. 
 * 
 */
package com.cloudstone.cloudhand.network.api.base;

import java.lang.reflect.Type;
import java.util.List;

import org.apache.http.HttpResponse;

import com.cloudstone.cloudhand.exception.DecodeApiException;
import com.cloudstone.cloudhand.network.form.IForm;
import com.cloudstone.cloudhand.util.HttpUtils;
import com.cloudstone.cloudhand.util.JsonUtils;
import com.cloudstone.cloudhand.util.L;
import com.google.gson.reflect.TypeToken;

/**
 * @author xuhongfeng
 *
 */
public class AbsGetStringArrayApi<FORM extends IForm> extends AbsGetApi<String[], FORM> {
    private static final Type type = new TypeToken<List<String>>(){}.getType();

    public AbsGetStringArrayApi(String url, FORM form) {
        super(url, form);
    }

    @Override
    protected String[] decodeResponse(HttpResponse response)
            throws DecodeApiException {
        String json = HttpUtils.responseToString(response);
        L.i(this, "HttpResponse : " + json);
        List<String> names = JsonUtils.jsonToList(json, type);
        return names.toArray(new String[names.size()]);
    }

}
