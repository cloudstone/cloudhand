/**
 * @(#)ListDishApi.java, Aug 14, 2013. 
 * 
 */
package com.cloudstone.cloudhand.network.api;

import java.lang.reflect.Type;
import java.util.List;

import com.cloudstone.cloudhand.constant.EmptyConst;
import com.cloudstone.cloudhand.constant.UrlConst;
import com.cloudstone.cloudhand.data.Dish;
import com.cloudstone.cloudhand.network.api.base.AbsGetJsonArrayApi;
import com.google.gson.reflect.TypeToken;

/**
 * @author xuhongfeng
 *
 */
public class ListDishApi extends AbsGetJsonArrayApi<Dish, EmptyConst.EmptyForm> {

    public ListDishApi() {
        super(new UrlConst().LIST_DISH_URL, EmptyConst.EmptyFormInstance);
    }
    
    @Override
    protected Type getType() {
        return new TypeToken<List<Dish>>(){}.getType();
    }
}
