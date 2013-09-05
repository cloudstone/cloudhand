/**
 * @(#)OccupyTableApi.java, Aug 19, 2013. 
 * 
 */
package com.cloudstone.cloudhand.network.api;

import com.cloudstone.cloudhand.constant.UrlConst;
import com.cloudstone.cloudhand.data.Table;
import com.cloudstone.cloudhand.exception.ApiException;
import com.cloudstone.cloudhand.exception.HttpStatusError;
import com.cloudstone.cloudhand.network.api.OccupyTableApi.OccupyTableForm;
import com.cloudstone.cloudhand.network.api.base.AbsPutGetJsonApi;
import com.cloudstone.cloudhand.network.api.base.IApiCallback;
import com.cloudstone.cloudhand.network.form.BaseForm;

/**
 * @author xuhongfeng
 *
 */
public class OccupyTableApi
        extends AbsPutGetJsonApi<Table, OccupyTableForm> {
    
    public OccupyTableApi(int tableId, int customerNumber) {
        super(new UrlConst().occupyTableUrl(tableId),
                new OccupyTableForm(customerNumber));
    }
    
    public void asyncCall(OccupyTableCalback callback) {
        super.asyncCall(callback);
    }
    public static abstract class OccupyTableCalback
            implements IApiCallback<Table> {

        @Override
        public final void onFailed(ApiException exception) {
            if( exception instanceof HttpStatusError) {
                HttpStatusError e = (HttpStatusError) exception;
                int status = e.getStatus();
                if (status == 409) {
                    onOccupied();
                    return;
                }
            }
            onError(exception);
        }
        
        protected abstract void onOccupied();
        protected abstract void onError(ApiException e);
    }
    
    @Override
    protected Class<Table> getResultClass() {
        return Table.class;
    }
    
    public static class OccupyTableForm extends BaseForm {
        private static final String PARAM_CUSTOMER_NUMBER = "customerNumber";
        
        public OccupyTableForm(int customerNumber) {
            super();
            addParam(PARAM_CUSTOMER_NUMBER, customerNumber);
        }
    }
}