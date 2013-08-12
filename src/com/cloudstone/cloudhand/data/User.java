/**
 * @(#)User.java, Jul 26, 2013. 
 * 
 */
package com.cloudstone.cloudhand.data;

/**
 * @author xuhongfeng
 *
 */
public class User extends BaseData {
    
    private int type;
    
    private String realName;
    
    private String name;
    
    public String getName() {
    	return name;
    }
    
    /* 备注 */
    private String comment = "";

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
