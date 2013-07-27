/**
 * @(#)HttpUtils.java, Jul 26, 2013. 
 * 
 */
package com.cloudstone.cloudhand.util;

import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.zip.GZIPInputStream;

import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;

import com.cloudstone.cloudhand.exception.ApiException;
import com.cloudstone.cloudhand.exception.DecodeApiException;
import com.cloudstone.cloudhand.network.RequestParams;
import com.cloudstone.cloudhand.network.form.IForm;

/**
 * @author xuhongfeng
 *
 */
public class HttpUtils {

    public static String responseToString(HttpResponse response) throws DecodeApiException {
        InputStream is = null;
        try {
            is = responseToStream(response);
            String str = IOUtils.toString(is);
            return str;
        } catch (Exception e) {
            throw new DecodeApiException(e);
        } finally {
            IOUtils.closeQuietly(is);
        }
    }

    public static InputStream responseToStream(HttpResponse response) throws ApiException {
        try {
            InputStream instream = response.getEntity().getContent();
            Header contentEncoding = response.getFirstHeader("Content-Encoding");
            if (contentEncoding != null && contentEncoding.getValue().equalsIgnoreCase("gzip")) {
                instream = new GZIPInputStream(instream);
            }
            return instream;
        } catch (Throwable e) {
            throw new ApiException(e);
        }
    }

    public static String generateGetUrl(String baseUrl, IForm form) {
        StringBuilder sb = new StringBuilder();
        sb.append(baseUrl);
        if(form != null) {
            boolean isFirst = true;
            for(NameValuePair pair:form.listParams()) {
                if(isFirst) {
                    sb.append("?");
                    isFirst = false;
                } else {
                    sb.append("&");
                }
                sb.append(pair.getName());
                sb.append("=");
                try {
                    sb.append(URLEncoder.encode(pair.getValue(), "utf-8"));
                } catch (UnsupportedEncodingException e) {
                    throw new RuntimeException(e);
                }
            }
        }
        return sb.toString();
    }

    public static RequestParams generatePostParams(IForm form) {
        RequestParams params = new RequestParams();
        for(NameValuePair e:form.listParams()) {
            params.put(e.getName(), e.getValue());
        }
        return params;
    }

}
