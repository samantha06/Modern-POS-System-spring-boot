package lk.ijse.dep.webmvc.dto;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

public class CustomDTO {
    private int orderId;
    private int customerId;
    private String customerName;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date date;
    private double total;

    public CustomDTO() {
    }

    public CustomDTO(int orderId, int customerId, String customerName, Date date, double total) {
        this.orderId = orderId;
        this.customerId = customerId;
        this.customerName = customerName;
        this.date = date;
        this.total = total;
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

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    @Override
    public String toString() {
        return "CustomDTO{" +
                "orderId=" + orderId +
                ", customerId=" + customerId +
                ", customerName='" + customerName + '\'' +
                ", date=" + date +
                ", total=" + total +
                '}';
    }
}
