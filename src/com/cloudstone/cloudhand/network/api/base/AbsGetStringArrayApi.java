/**
 * @(#)AbsGetStringArrayApi.java, Aug 3, 2013. 
 * 
 */
package com.cloudstone.cloudhand.network.api.base;

import org.apache.http.HttpResponse;

import com.cloudstone.cloudhand.exception.DecodeApiException;
import com.cloudstone.cloudhand.network.form.IForm;
import com.cloudstone.cloudhand.util.HttpUtils;
import com.cloudstone.cloudhand.util.L;

/**
 * @author xuhongfeng
 *
 */
public class AbsGetStringArrayApi<FORM extends IForm> extends AbsGetApi<String[], FORM> {

    public AbsGetStringArrayApi(String url, FORM form) {
        super(url, form);
    }

    @Override
    protected String[] decodeResponse(HttpResponse response)
            throws DecodeApiException {
        String s = HttpUtils.responseToString(response);
        L.i(this, "HttpResponse : " + s);
        return s.split(",");
    }

}
