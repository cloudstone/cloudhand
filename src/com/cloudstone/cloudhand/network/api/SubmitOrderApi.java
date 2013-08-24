/**
 * @(#)SubmitOrderApi.java, Aug 24, 2013. 
 * 
 */
package com.cloudstone.cloudhand.network.api;

import com.cloudstone.cloudhand.constant.UrlConst;
import com.cloudstone.cloudhand.data.Order;
import com.cloudstone.cloudhand.network.api.base.AbsPostJsonGetJsonApi;

/**
 * @author xuhongfeng
 *
 */
public class SubmitOrderApi extends AbsPostJsonGetJsonApi<Order, Order> {

    /**
     * @param url
     * @param payload
     */
    public SubmitOrderApi(Order payload) {
        super(UrlConst.SUBMIT_ORDER_URL, payload);
    }

    @Override
    protected Class<Order> getResultClass() {
        return Order.class;
    }

}
