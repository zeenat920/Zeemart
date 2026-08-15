package com.zeenat.zeemart.service;

import com.zeenat.zeemart.dao.OrderDAO;
import com.zeenat.zeemart.dao.OrderDAOImpl;
import com.zeenat.zeemart.exception.ValidationException;
import com.zeenat.zeemart.model.Order;

import java.sql.SQLException;
import java.util.List;

public class OrderService {
    private final OrderDAO orderDAO = new OrderDAOImpl();

    public Order checkout(int buyerId) throws ValidationException, SQLException {
        try {
            return orderDAO.placeOrder(buyerId);
        } catch (SQLException e) {
            if (e.getMessage() != null && e.getMessage().contains("Cart is empty")) {
                throw new ValidationException("cart", "Cannot place order with an empty cart");
            }
            throw e;
        }
    }

    public List<Order> history(int buyerId) throws SQLException {
        return orderDAO.findByBuyer(buyerId);
    }
}
