package com.cloudstone.cloudhand.network.api;

import com.cloudstone.cloudhand.constant.EmptyConst;
import com.cloudstone.cloudhand.constant.UrlConst;
import com.cloudstone.cloudhand.data.Order;
import com.cloudstone.cloudhand.network.api.base.AbsGetJsonApi;

public class GetOrderApi extends AbsGetJsonApi<Order , EmptyConst.EmptyForm> {

    public GetOrderApi(int orderId) {
        super(UrlConst.GET_ORDER_URL, EmptyConst.EmptyFormInstance);
    }

    @Override
    protected Class<Order> getResultClass() {
        return Order.class;
    }

}
