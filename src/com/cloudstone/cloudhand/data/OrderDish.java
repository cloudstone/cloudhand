/**
 * @(#)OrderDish.java, Aug 24, 2013. 
 * 
 */
package com.cloudstone.cloudhand.data;

/**
 * @author xuhongfeng
 *
 */
public class OrderDish extends Dish {
    private double number;
    private String[] remarks;
    private int orderStatus;
    private double totalCost;
    private int orderId;

    public double getNumber() {
        return number;
    }
    public void setNumber(double number) {
        this.number = number;
    }
    public String[] getRemarks() {
        return remarks;
    }
    public void setRemarks(String[] remarks) {
        this.remarks = remarks;
    }
    public int getOrderStatus() {
        return orderStatus;
    }
    public void setOrderStatus(int orderStatus) {
        this.orderStatus = orderStatus;
    }
    public double getTotalCost() {
        return totalCost;
    }
    public void setTotalCost(double totalCost) {
        this.totalCost = totalCost;
    }
    public int getOrderId() {
        return orderId;
    }
    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }
}
