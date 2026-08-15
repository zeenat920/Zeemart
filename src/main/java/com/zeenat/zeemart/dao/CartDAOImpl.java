package com.zeenat.zeemart.dao;

import com.zeenat.zeemart.dto.CartItemView;
import com.zeenat.zeemart.util.DataSourceManager;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CartDAOImpl implements CartDAO {

    @Override
    public void addOrIncrement(int userId, int productId, int qty) throws SQLException {
        String find = "SELECT id, quantity FROM cart_items WHERE user_id=? AND product_id=?";
        try (Connection conn = DataSourceManager.get().getConnection()) {
            try (PreparedStatement ps = conn.prepareStatement(find)) {
                ps.setInt(1, userId);
                ps.setInt(2, productId);
                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) {
                        int newQty = rs.getInt("quantity") + qty;
                        try (PreparedStatement up = conn.prepareStatement(
                                "UPDATE cart_items SET quantity=? WHERE id=?")) {
                            up.setInt(1, newQty);
                            up.setInt(2, rs.getInt("id"));
                            up.executeUpdate();
                        }
                        return;
                    }
                }
            }
            try (PreparedStatement ins = conn.prepareStatement(
                    "INSERT INTO cart_items (user_id, product_id, quantity) VALUES (?,?,?)")) {
                ins.setInt(1, userId);
                ins.setInt(2, productId);
                ins.setInt(3, qty);
                ins.executeUpdate();
            }
        }
    }

    @Override
    public void updateQuantity(int userId, int itemId, int qty) throws SQLException {
        String sql = "UPDATE cart_items SET quantity=? WHERE id=? AND user_id=?";
        try (Connection conn = DataSourceManager.get().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, qty);
            ps.setInt(2, itemId);
            ps.setInt(3, userId);
            ps.executeUpdate();
        }
    }

    @Override
    public void remove(int userId, int itemId) throws SQLException {
        String sql = "DELETE FROM cart_items WHERE id=? AND user_id=?";
        try (Connection conn = DataSourceManager.get().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, itemId);
            ps.setInt(2, userId);
            ps.executeUpdate();
        }
    }

    @Override
    public List<CartItemView> findByUser(int userId) throws SQLException {
        String sql = "SELECT ci.id AS item_id, ci.product_id, ci.quantity, p.name, p.price " +
                "FROM cart_items ci JOIN products p ON ci.product_id = p.id " +
                "WHERE ci.user_id = ? ORDER BY ci.created_at";
        try (Connection conn = DataSourceManager.get().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, userId);
            try (ResultSet rs = ps.executeQuery()) {
                List<CartItemView> views = new ArrayList<>();
                while (rs.next()) {
                    CartItemView v = new CartItemView();
                    v.itemId = rs.getInt("item_id");
                    v.productId = rs.getInt("product_id");
                    v.productName = rs.getString("name");
                    v.unitPrice = rs.getBigDecimal("price");
                    v.quantity = rs.getInt("quantity");
                    v.subtotal = v.unitPrice.multiply(java.math.BigDecimal.valueOf(v.quantity));
                    views.add(v);
                }
                return views;
            }
        }
    }

    @Override
    public void clear(int userId, Connection conn) throws SQLException {
        try (PreparedStatement ps = conn.prepareStatement("DELETE FROM cart_items WHERE user_id=?")) {
            ps.setInt(1, userId);
            ps.executeUpdate();
        }
    }
}
