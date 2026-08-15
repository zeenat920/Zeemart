package com.zeenat.zeemart.dao;

import com.zeenat.zeemart.dto.CartItemView;
import com.zeenat.zeemart.model.Order;
import com.zeenat.zeemart.util.DataSourceManager;

import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class OrderDAOImpl implements OrderDAO {

    private final CartDAO cartDAO = new CartDAOImpl();
    private final ProductDAO productDAO = new ProductDAOImpl();

    @Override
    public Order placeOrder(int buyerId) throws SQLException {
        List<CartItemView> cartItems = cartDAO.findByUser(buyerId);
        if (cartItems.isEmpty()) {
            throw new SQLException("Cart is empty");
        }

        BigDecimal total = cartItems.stream()
                .map(c -> c.subtotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        try (Connection conn = DataSourceManager.get().getConnection()) {
            conn.setAutoCommit(false);
            try {
                int orderId;
                try (PreparedStatement ps = conn.prepareStatement(
                        "INSERT INTO orders (buyer_id, status, total_amount) VALUES (?,?,?)",
                        Statement.RETURN_GENERATED_KEYS)) {
                    ps.setInt(1, buyerId);
                    ps.setString(2, Order.Status.CONFIRMED.name()); // mock payment auto-confirms
                    ps.setBigDecimal(3, total);
                    ps.executeUpdate();
                    try (ResultSet keys = ps.getGeneratedKeys()) {
                        keys.next();
                        orderId = keys.getInt(1);
                    }
                }

                try (PreparedStatement itemPs = conn.prepareStatement(
                        "INSERT INTO order_items (order_id, product_id, quantity, unit_price) VALUES (?,?,?,?)")) {
                    for (CartItemView item : cartItems) {
                        itemPs.setInt(1, orderId);
                        itemPs.setInt(2, item.productId);
                        itemPs.setInt(3, item.quantity);
                        itemPs.setBigDecimal(4, item.unitPrice);
                        itemPs.addBatch();

                        productDAO.decrementStock(item.productId, item.quantity, conn);
                    }
                    itemPs.executeBatch();
                }

                cartDAO.clear(buyerId, conn);

                conn.commit();

                Order order = new Order();
                order.setId(orderId);
                order.setBuyerId(buyerId);
                order.setStatus(Order.Status.CONFIRMED);
                order.setTotalAmount(total);
                return order;
            } catch (SQLException e) {
                conn.rollback();
                throw e;
            } finally {
                conn.setAutoCommit(true);
            }
        }
    }

    @Override
    public List<Order> findByBuyer(int buyerId) throws SQLException {
        String sql = "SELECT * FROM orders WHERE buyer_id=? ORDER BY created_at DESC";
        try (Connection conn = DataSourceManager.get().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, buyerId);
            try (ResultSet rs = ps.executeQuery()) {
                List<Order> orders = new ArrayList<>();
                while (rs.next()) {
                    Order o = new Order();
                    o.setId(rs.getInt("id"));
                    o.setBuyerId(rs.getInt("buyer_id"));
                    o.setStatus(Order.Status.valueOf(rs.getString("status")));
                    o.setTotalAmount(rs.getBigDecimal("total_amount"));
                    o.setCreatedAt(rs.getTimestamp("created_at"));
                    orders.add(o);
                }
                return orders;
            }
        }
    }
}
