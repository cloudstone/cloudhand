package com.cloudstone.cloudhand.network.api;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import com.cloudstone.cloudhand.constant.EmptyConst;
import com.cloudstone.cloudhand.constant.UrlConst;
import com.cloudstone.cloudhand.data.Table;
import com.cloudstone.cloudhand.network.api.base.AbsGetJsonArrayApi;
import com.cloudstone.cloudhand.network.api.base.IApiCallback;
import com.cloudstone.cloudhand.network.form.IForm;

public class ListTableApi extends AbsGetJsonArrayApi<Table, EmptyConst.EmptyForm> {

	public ListTableApi() {
		super(new UrlConst().LIST_TABLE_URL, EmptyConst.EmptyFormInstance);
	}

	@Override
	protected Type getType() {
		// TODO Auto-generated method stub
		return null;
	}

}
