/**
 * @(#)LoginApi.java, Jul 26, 2013. 
 * 
 */
package com.cloudstone.cloudhand.network.api;

import com.cloudstone.cloudhand.constant.UrlConst;
import com.cloudstone.cloudhand.data.User;
import com.cloudstone.cloudhand.exception.ApiException;
import com.cloudstone.cloudhand.exception.HttpStatusError;
import com.cloudstone.cloudhand.network.api.LoginApi.LoginForm;
import com.cloudstone.cloudhand.network.api.base.AbsPostFormGetJsonApi;
import com.cloudstone.cloudhand.network.api.base.IApiCallback;
import com.cloudstone.cloudhand.network.form.BaseForm;

/**
 * @author xuhongfeng
 *
 */
public class LoginApi extends AbsPostFormGetJsonApi<User, LoginForm> {
    
    public LoginApi(String name, String password) {
        super(new UrlConst().LOGIN_URL, new LoginForm(name, password));
    }
    
    public void asyncCall(LoginApiCallback callback) {
        super.asyncCall(callback);
    }
    
    @Override
    protected Class<User> getResultClass() {
        return User.class;
    }
    
    public static class LoginForm extends BaseForm {
        public LoginForm(String name, String password) {
            super();
            addParam("name", name);
            addParam("password", password);
        }
    }
    
    public static abstract class LoginApiCallback implements IApiCallback<User> {

        @Override
        public final void onFailed(ApiException exception) {
            if( exception instanceof HttpStatusError) {
                HttpStatusError e = (HttpStatusError) exception;
                int status = e.getStatus();
                if (status == 401) {
                    onAuthFailed();
                    return;
                }
            }
            onError(exception);
        }
        
        protected abstract void onAuthFailed();
        protected abstract void onError(ApiException e);
    }
}