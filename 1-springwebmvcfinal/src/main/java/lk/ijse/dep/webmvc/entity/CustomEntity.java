package lk.ijse.dep.webmvc.entity;

import java.util.Date;

public class CustomEntity extends SuperEntity {

    private int orderId;
    private int customerId;
    private String customerName;
    private double orderTotal;
    private Date orderDate;

    private int orderDetailsCount;

    public CustomEntity() {
    }

    public CustomEntity(int orderId, double orderTotal) {
        this.orderId = orderId;
        this.orderTotal = orderTotal;
    }

    public CustomEntity(int orderId, int customerId, String customerName, Date orderDate, double orderTotal) {
        this.setOrderId(orderId);
        this.setCustomerId(customerId);
        this.setCustomerName(customerName);
        this.setOrderDate(orderDate);
        this.setOrderTotal(orderTotal);

    }

    public CustomEntity(int orderId, int customerId, int orderDetailsCount) {
        this.orderId = orderId;
        this.customerId = customerId;
        this.orderDetailsCount = orderDetailsCount;
    }



    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public double getOrderTotal() {
        return orderTotal;
    }

    public void setOrderTotal(double orderTotal) {
        this.orderTotal = orderTotal;
    }

    public Date getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }

    public int getOrderDetailsCount() {
        return orderDetailsCount;
    }

    public void setOrderDetailsCount(int orderDetailsCount) {
        this.orderDetailsCount = orderDetailsCount;
    }
}
