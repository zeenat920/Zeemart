package com.zeenat.zeemart.service;

import com.zeenat.zeemart.dao.ProductDAO;
import com.zeenat.zeemart.dao.ProductDAOImpl;
import com.zeenat.zeemart.model.Product;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class ProductService {
    private final ProductDAO productDAO = new ProductDAOImpl();

    public List<Product> browse(String keyword, String category) throws SQLException {
        return productDAO.search(keyword, category);
    }

    public Optional<Product> get(int id) throws SQLException {
        return productDAO.findById(id);
    }
}
