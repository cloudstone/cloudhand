/**
 * @(#)Dish.java, Aug 11, 2013. 
 * 
 */
package com.cloudstone.cloudhand.data;

import com.cloudstone.cloudhand.constant.Const;

/**
 * @author xuhongfeng
 *
 */
public class Dish extends BaseData {
    private int id;
    private String name;
    private double price;
    private double memberPrice;
    private int unit;
    private int spicy;
    private boolean specialPrice = false;
    private boolean nonInt = false; //是否允许小数份
    private String desc;
    private String imageId;
    private int status = Const.DishStatus.STATUS_INIT;
    private String pinyin;
    private boolean soldout = false;
    
    public double getPrice() {
        return price;
    }
    public void setPrice(double price) {
        this.price = price;
    }
    public double getMemberPrice() {
        return memberPrice;
    }
    public void setMemberPrice(double memberPrice) {
        this.memberPrice = memberPrice;
    }
    public int getUnit() {
        return unit;
    }
    public void setUnit(int unit) {
        this.unit = unit;
    }
    public int getSpicy() {
        return spicy;
    }
    public void setSpicy(int spicy) {
        this.spicy = spicy;
    }
    public boolean isSpecialPrice() {
        return specialPrice;
    }
    public void setSpecialPrice(boolean specialPrice) {
        this.specialPrice = specialPrice;
    }
    public boolean isNonInt() {
        return nonInt;
    }
    public void setNonInt(boolean nonInt) {
        this.nonInt = nonInt;
    }
    public String getDesc() {
        return desc;
    }
    public void setDesc(String desc) {
        this.desc = desc;
    }
    public String getImageId() {
        return imageId;
    }
    public void setImageId(String imageId) {
        this.imageId = imageId;
    }
    public int getStatus() {
        return status;
    }
    public void setStatus(int status) {
        this.status = status;
    }
    public String getPinyin() {
        return pinyin;
    }
    public void setPinyin(String pinyin) {
        this.pinyin = pinyin;
    }
    public boolean isSoldout() {
        return soldout;
    }
    public void setSoldout(boolean soldout) {
        this.soldout = soldout;
    }
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
}
