/**
 * @(#)BaseData.java, Jul 26, 2013. 
 * 
 */
package com.cloudstone.cloudhand.data;

import com.cloudstone.cloudhand.util.JsonUtils;

/**
 * @author xuhongfeng
 *
 */
public class BaseData implements IJson {

    @Override
    public String toJson() {
        return JsonUtils.objectToJson(this);
    }

}
