package com.zeenat.zeemart.dao;

import com.zeenat.zeemart.dto.CartItemView;
import java.sql.SQLException;
import java.util.List;

public interface CartDAO {
    void addOrIncrement(int userId, int productId, int qty) throws SQLException;
    void updateQuantity(int userId, int itemId, int qty) throws SQLException;
    void remove(int userId, int itemId) throws SQLException;
    List<CartItemView> findByUser(int userId) throws SQLException;
    void clear(int userId, java.sql.Connection conn) throws SQLException;
}
