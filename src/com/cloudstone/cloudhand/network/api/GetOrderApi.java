package com.cloudstone.cloudhand.network.api;

import com.cloudstone.cloudhand.constant.EmptyConst;
import com.cloudstone.cloudhand.constant.UrlConst;
import com.cloudstone.cloudhand.data.Order;
import com.cloudstone.cloudhand.network.api.base.AbsGetJsonApi;
import com.cloudstone.cloudhand.network.form.IForm;

public class GetOrderApi extends AbsGetJsonApi<Order , EmptyConst.EmptyForm> {

    public GetOrderApi(Order orderId) {
        super(UrlConst.SUBMIT_ORDER_URL, EmptyConst.EmptyFormInstance);
    }

    @Override
    protected Class<Order> getResultClass() {
        return Order.class;
    }



}
