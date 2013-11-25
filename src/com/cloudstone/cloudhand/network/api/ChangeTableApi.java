/**
 * @(#)OccupyTableApi.java, Aug 19, 2013. 
 * 
 */
package com.cloudstone.cloudhand.network.api;

import com.cloudstone.cloudhand.constant.EmptyConst;
import com.cloudstone.cloudhand.constant.UrlConst;
import com.cloudstone.cloudhand.data.Table;
import com.cloudstone.cloudhand.exception.ApiException;
import com.cloudstone.cloudhand.exception.HttpStatusError;
import com.cloudstone.cloudhand.network.api.base.AbsPutGetJsonApi;
import com.cloudstone.cloudhand.network.api.base.IApiCallback;

/**
 * @author xhc
 *
 */
public class ChangeTableApi
        extends AbsPutGetJsonApi<Table, EmptyConst.EmptyForm> {
    
public ChangeTableApi(int fromId, int toldId) {
        super(new UrlConst().changeTableUrl(fromId, toldId), EmptyConst.EmptyFormInstance);
    }

    public void asyncCall(ChangeTableCallback callback) {
        super.asyncCall(callback);
    }
    public static abstract class ChangeTableCallback
            implements IApiCallback<Table> {

        @Override
        public final void onFailed(ApiException exception) {
            if( exception instanceof HttpStatusError) {
                HttpStatusError e = (HttpStatusError) exception;
                int status = e.getStatus();
                if (status == 409) {
                    onChanged();
                    return;
                }
            }
            onError(exception);
        }
        
        protected abstract void onChanged();
        protected abstract void onError(ApiException e);
    }
    
    @Override
    protected Class<Table> getResultClass() {
        return Table.class;
    }
    
}