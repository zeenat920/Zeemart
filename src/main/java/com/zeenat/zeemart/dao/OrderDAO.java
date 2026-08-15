package com.zeenat.zeemart.dao;

import com.zeenat.zeemart.model.Order;
import java.sql.SQLException;
import java.util.List;

public interface OrderDAO {
    Order placeOrder(int buyerId) throws SQLException;
    List<Order> findByBuyer(int buyerId) throws SQLException;
}
