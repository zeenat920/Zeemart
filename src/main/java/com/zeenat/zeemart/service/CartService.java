package com.zeenat.zeemart.service;

import com.zeenat.zeemart.dao.CartDAO;
import com.zeenat.zeemart.dao.CartDAOImpl;
import com.zeenat.zeemart.dao.ProductDAO;
import com.zeenat.zeemart.dao.ProductDAOImpl;
import com.zeenat.zeemart.dto.CartItemView;
import com.zeenat.zeemart.exception.ValidationException;
import com.zeenat.zeemart.model.Product;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class CartService {
    private final CartDAO cartDAO = new CartDAOImpl();
    private final ProductDAO productDAO = new ProductDAOImpl();

    public void addItem(int userId, int productId, int qty) throws ValidationException, SQLException {
        if (qty <= 0) throw new ValidationException("quantity", "Quantity must be positive");
        Optional<Product> product = productDAO.findById(productId);
        if (product.isEmpty()) throw new ValidationException("productId", "Product not found");
        if (product.get().getStockQty() < qty) throw new ValidationException("quantity", "Not enough stock");
        cartDAO.addOrIncrement(userId, productId, qty);
    }

    public void updateItem(int userId, int itemId, int qty) throws ValidationException, SQLException {
        if (qty <= 0) throw new ValidationException("quantity", "Quantity must be positive");
        cartDAO.updateQuantity(userId, itemId, qty);
    }

    public void removeItem(int userId, int itemId) throws SQLException {
        cartDAO.remove(userId, itemId);
    }

    public CartSummary viewCart(int userId) throws SQLException {
        List<CartItemView> items = cartDAO.findByUser(userId);
        BigDecimal total = items.stream().map(i -> i.subtotal).reduce(BigDecimal.ZERO, BigDecimal::add);
        CartSummary summary = new CartSummary();
        summary.items = items;
        summary.total = total;
        return summary;
    }

    public static class CartSummary {
        public List<CartItemView> items;
        public BigDecimal total;
    }
}
