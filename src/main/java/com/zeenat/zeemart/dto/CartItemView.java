package com.zeenat.zeemart.dto;

import java.math.BigDecimal;

public class CartItemView {
    public int itemId;
    public int productId;
    public String productName;
    public BigDecimal unitPrice;
    public int quantity;
    public BigDecimal subtotal;
}
