/**
 * @(#)DishNote.java, Aug 27, 2013. 
 * 
 */
package com.cloudstone.cloudhand.data;

import com.cloudstone.cloudhand.util.JsonUtils;

/**
 * @author xuhongfeng
 *
 */
public class DishNote implements IJson {
    private int id;
    private String name;
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toJson() {
        return JsonUtils.objectToJson(this);
    }
}
