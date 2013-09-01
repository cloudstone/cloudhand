/**
 * @(#)AbsPostApi.java, Jul 26, 2013. 
 * 
 */
package com.cloudstone.cloudhand.network.api.base;

import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;

import com.cloudstone.cloudhand.network.RequestParams;
import com.cloudstone.cloudhand.network.form.IForm;
import com.cloudstone.cloudhand.util.HttpUtils;

/**
 * @author xuhongfeng
 *
 */
public abstract class AbsPostApi<RESULT, FORM extends IForm> extends AbsApi<RESULT, FORM> {

    protected AbsPostApi(String url, FORM form) {
        super(url, form);
    }

    @Override
    protected HttpUriRequest onCreateRequest() {
        HttpPost post = new HttpPost(getUrl());
        setPostEntity(post);
        return post;
    }
    
    protected void setPostEntity(HttpPost post) {
        RequestParams params = HttpUtils.generatePostParams(form);
        post.setEntity(params.getEntity());
    }
}
