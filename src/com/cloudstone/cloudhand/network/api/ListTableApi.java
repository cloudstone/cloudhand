package com.cloudstone.cloudhand.network.api;

import java.lang.reflect.Type;
import java.util.List;

import com.cloudstone.cloudhand.constant.EmptyConst;
import com.cloudstone.cloudhand.constant.UrlConst;
import com.cloudstone.cloudhand.data.Dish;
import com.cloudstone.cloudhand.data.Table;
import com.cloudstone.cloudhand.network.api.base.AbsGetJsonArrayApi;
import com.google.gson.reflect.TypeToken;

public class ListTableApi extends AbsGetJsonArrayApi<Table, EmptyConst.EmptyForm> {

    public ListTableApi() {
        super(new UrlConst().LIST_TABLE_URL, EmptyConst.EmptyFormInstance);
    }

    @Override
    protected Type getType() {
        return new TypeToken<List<Table>>(){}.getType();
    }

}
