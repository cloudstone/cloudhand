/**
 * @(#)ListDishApi.java, Aug 14, 2013. 
 * 
 */
package com.cloudstone.cloudhand.network.api;

import java.lang.reflect.Type;
import java.util.List;

import com.cloudstone.cloudhand.constant.EmptyConst;
import com.cloudstone.cloudhand.constant.UrlConst;
import com.cloudstone.cloudhand.data.DishNote;
import com.cloudstone.cloudhand.network.api.base.AbsGetJsonArrayApi;
import com.google.gson.reflect.TypeToken;

/**
 * @author xuhongfeng
 *
 */
public class ListDishNoteApi extends AbsGetJsonArrayApi<DishNote, EmptyConst.EmptyForm> {

    public ListDishNoteApi() {
        super(UrlConst.LIST_DISH_NOTE_URL, EmptyConst.EmptyFormInstance);
    }
    
    @Override
    protected Type getType() {
        return new TypeToken<List<DishNote>>(){}.getType();
    }
}
