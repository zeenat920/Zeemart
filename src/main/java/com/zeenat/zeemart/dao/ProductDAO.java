package com.zeenat.zeemart.dao;

import com.zeenat.zeemart.model.Product;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public interface ProductDAO {
    List<Product> search(String keyword, String category) throws SQLException;
    Optional<Product> findById(int id) throws SQLException;
    void decrementStock(int productId, int qty, Connection conn) throws SQLException;
    interface Connection extends java.sql.Connection {}
}
