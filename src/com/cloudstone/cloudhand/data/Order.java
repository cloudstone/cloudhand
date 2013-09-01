/**
 * @(#)Order.java, Aug 24, 2013. 
 * 
 */
package com.cloudstone.cloudhand.data;

import java.util.List;

import com.cloudstone.cloudhand.util.JsonUtils;

/**
 * @author xuhongfeng
 *
 */
public class Order implements IJson {
    private int id;
    private double originPrice;
    private double price;
    private int tableId;
    private int userId;
    private int customerNumber;
    private int status;
    private Table table;
    private List<OrderDish> dishes;

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public double getOriginPrice() {
        return originPrice;
    }
    public void setOriginPrice(double originPrice) {
        this.originPrice = originPrice;
    }
    public double getPrice() {
        return price;
    }
    public void setPrice(double price) {
        this.price = price;
    }
    public int getTableId() {
        return tableId;
    }
    public void setTableId(int tableId) {
        this.tableId = tableId;
    }
    public int getUserId() {
        return userId;
    }
    public void setUserId(int userId) {
        this.userId = userId;
    }
    public int getCustomerNumber() {
        return customerNumber;
    }
    public void setCustomerNumber(int customerNumber) {
        this.customerNumber = customerNumber;
    }
    public int getStatus() {
        return status;
    }
    public void setStatus(int status) {
        this.status = status;
    }
    public Table getTable() {
        return table;
    }
    public void setTable(Table table) {
        this.table = table;
    }
    public List<OrderDish> getDishes() {
        return dishes;
    }
    public void setDishes(List<OrderDish> dishes) {
        this.dishes = dishes;
    }
    
    @Override
    public String toJson() {
        return JsonUtils.objectToJson(this);
    }
}
