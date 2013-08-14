/**
 * @(#)ListDishApi.java, Aug 14, 2013. 
 * 
 */
package com.cloudstone.cloudhand.network.api;

import com.cloudstone.cloudhand.constant.EmptyConst;
import com.cloudstone.cloudhand.constant.UrlConst;
import com.cloudstone.cloudhand.data.Dish;
import com.cloudstone.cloudhand.network.api.base.AbsGetJsonArrayApi;

/**
 * @author xuhongfeng
 *
 */
public class ListDishApi extends AbsGetJsonArrayApi<Dish, EmptyConst.EmptyForm> {

    public ListDishApi() {
        super(UrlConst.LIST_DISH_URL, EmptyConst.EmptyFormInstance);
    }

    @Override
    protected Class<Dish> getResultClass() {
        return Dish.class;
    }

}
