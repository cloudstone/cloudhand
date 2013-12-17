/**
 * @(#)OccupyTableApi.java, Aug 19, 2013. 
 * 
 */
package com.cloudstone.cloudhand.network.api;

import com.cloudstone.cloudhand.constant.UrlConst;
import com.cloudstone.cloudhand.data.Table;
import com.cloudstone.cloudhand.exception.ApiException;
import com.cloudstone.cloudhand.exception.HttpStatusError;
import com.cloudstone.cloudhand.network.api.ChangeTableApi.ChangeTableForm;
import com.cloudstone.cloudhand.network.api.base.AbsPutGetJsonApi;
import com.cloudstone.cloudhand.network.api.base.IApiCallback;
import com.cloudstone.cloudhand.network.form.BaseForm;

/**
 * @author xuhongfeng
 *
 */
public class ChangeTableApi
        extends AbsPutGetJsonApi<Table, ChangeTableForm> {
    
    public ChangeTableApi(int fromId, int toldId) {
        super(UrlConst.CHANGE_TABLE_URL,
                new ChangeTableForm(fromId, toldId));
    }
    
    public void asyncCall(ChangeTableCalback callback) {
        super.asyncCall(callback);
    }
    public static abstract class ChangeTableCalback
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
    
    public static class ChangeTableForm extends BaseForm {
        private static final String PARAM_FROM_ID = "fromId";
        private static final String PARAM_TOLD_ID = "toldId";
        
        public ChangeTableForm(int fromId, int toldId) {
            super();
            addParam(PARAM_FROM_ID, fromId);
            addParam(PARAM_TOLD_ID, toldId);
        }
    }
}