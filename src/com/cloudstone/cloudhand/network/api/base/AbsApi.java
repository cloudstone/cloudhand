/**
 * @(#)AbsApi.java, Jul 26, 2013. 
 * 
 */
package com.cloudstone.cloudhand.network.api.base;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.protocol.ClientContext;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;

import android.os.AsyncTask;

import com.cloudstone.cloudhand.exception.ApiException;
import com.cloudstone.cloudhand.exception.DecodeApiException;
import com.cloudstone.cloudhand.exception.HttpStatusError;
import com.cloudstone.cloudhand.exception.NetworkApiException;
import com.cloudstone.cloudhand.network.HttpClientInstance;
import com.cloudstone.cloudhand.network.MyCookieStore;
import com.cloudstone.cloudhand.network.form.IForm;
import com.cloudstone.cloudhand.util.L;


/**
 * @author xuhongfeng
 *
 */
public abstract class AbsApi<RESULT, FORM extends IForm> implements IServerApi<RESULT> {
    private String baseUrl;
    protected FORM form;
    
    public AbsApi(String url, FORM form) {
        super();
        this.baseUrl = url;
        this.form = form;
    }

    @Override
    public RESULT call() throws ApiException {
        return innerRun();
    }

    @Override
    public void asyncCall(IApiCallback<RESULT> callback) {
        new HttpTask(callback).execute();
    }
    
    /* ---------- protected ---------- */
    protected String getUrl() {
        return baseUrl;
    }
    
    /* ---------- private ---------- */
    private RESULT innerRun() throws ApiException {
        HttpUriRequest request = onCreateRequest();
        HttpClient client = HttpClientInstance.newInstance();
        HttpResponse response = null;
        try {
            L.i(this, "HttpRequest: URL = " + request.getURI().toString());
            L.i(this, "HttpRequest: Method = " + request.getMethod());
            L.i(this, "HttpRequest: Form = " + form);
            response = client.execute(request, createHttpContext());
            int statusCode = response.getStatusLine().getStatusCode();
            if (statusCode<200 || statusCode>=300){
                throw new HttpStatusError(statusCode);
            }
        } catch (Exception e) {
            throw new NetworkApiException(e);
        }
        return decodeResponse(response);
    }
    
    private HttpContext createHttpContext() {
        HttpContext localContext = new BasicHttpContext();
        localContext.setAttribute(ClientContext.COOKIE_STORE, MyCookieStore.getInstance());
        return localContext;
    }
    
    /* ---------- Inner Class ---------- */
    private class ResultWrap {
        private boolean success;
        private ApiException error;
        private RESULT result;
        
        public boolean isSuccess() {
            return success;
        }
        public ApiException getError() {
            return error;
        }
        public RESULT getResult() {
            return result;
        }
    }
    
    private class HttpTask extends AsyncTask<Void, Void, ResultWrap> {
        private final IApiCallback<RESULT> callback;

        public HttpTask(IApiCallback<RESULT> callback) {
            super();
            this.callback = callback;
        }

        @Override
        protected ResultWrap doInBackground(Void... params) {
            ResultWrap wrap = new ResultWrap();
            try {
                RESULT result = innerRun();
                wrap.success = true;
                wrap.result = result;
                return wrap;
            } catch (ApiException e) {
                wrap.success = false;
                wrap.error = e;
            }
            return wrap;
        }
        
        @Override
        protected void onPostExecute(ResultWrap wrap) {
            super.onPostExecute(wrap);
            if (wrap.isSuccess()) {
                callback.onSuccess(wrap.getResult());
            } else {
                callback.onFailed(wrap.getError());
            }
            callback.onFinish();
        }
    }

    /* ---------- abstract ---------- */
    protected abstract HttpUriRequest onCreateRequest();
    protected abstract RESULT decodeResponse(HttpResponse response) throws DecodeApiException;
}
