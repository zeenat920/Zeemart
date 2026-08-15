package com.zeenat.zeemart.model;

import java.math.BigDecimal;
import java.sql.Timestamp;

public class Order {
    public enum Status { PENDING, CONFIRMED, SHIPPED, DELIVERED, CANCELLED }

    private int id;
    private int buyerId;
    private Status status;
    private BigDecimal totalAmount;
    private Timestamp createdAt;

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public int getBuyerId() { return buyerId; }
    public void setBuyerId(int buyerId) { this.buyerId = buyerId; }
    public Status getStatus() { return status; }
    public void setStatus(Status status) { this.status = status; }
    public BigDecimal getTotalAmount() { return totalAmount; }
    public void setTotalAmount(BigDecimal totalAmount) { this.totalAmount = totalAmount; }
    public Timestamp getCreatedAt() { return createdAt; }
    public void setCreatedAt(Timestamp createdAt) { this.createdAt = createdAt; }
}
