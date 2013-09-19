/**
 * @(#)AbsGetStringArrayApi.java, Aug 3, 2013. 
 * 
 */
package com.cloudstone.cloudhand.network.api.base;

import java.io.UnsupportedEncodingException;

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
        try {
			s = new String(s.getBytes(), "ISO-8859-1");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
//        byte[] bytes = s.getBytes(); 
//        try {
//        	L.i(this, "ok");
//			s = new String(bytes, "HTF-8");
//		} catch (UnsupportedEncodingException e) {
//			e.printStackTrace();
//		} 
        L.i(this, "HttpResponse : " + s);
        return s.split(",");
    }

}
