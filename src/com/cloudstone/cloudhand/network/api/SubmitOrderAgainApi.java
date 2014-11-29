package com.cloudstone.cloudhand.network.api;

import com.cloudstone.cloudhand.constant.UrlConst;
import com.cloudstone.cloudhand.data.Order;
import com.cloudstone.cloudhand.network.api.base.AbsPutJsonGetJsonApi;

public class SubmitOrderAgainApi extends AbsPutJsonGetJsonApi<Order, Order> {

    public SubmitOrderAgainApi(Order payload) {
        super(UrlConst.SUBMIT_ORDER_URL + "/" + payload.getId(), payload);
    }

    @Override
    protected Class<Order> getResultClass() {
        return Order.class;
    }

}
