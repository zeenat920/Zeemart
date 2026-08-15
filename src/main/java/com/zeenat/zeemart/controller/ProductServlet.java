package com.zeenat.zeemart.controller;

import com.zeenat.zeemart.model.Product;
import com.zeenat.zeemart.service.ProductService;
import com.zeenat.zeemart.util.JsonUtil;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Optional;

@WebServlet({"/api/v1/products", "/api/v1/products/*"})
public class ProductServlet extends HttpServlet {
    private final ProductService productService = new ProductService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        try {
            String pathInfo = req.getPathInfo();
            if (pathInfo != null && pathInfo.length() > 1) {
                int id = Integer.parseInt(pathInfo.substring(1));
                Optional<Product> product = productService.get(id);
                if (product.isEmpty()) {
                    JsonUtil.error(resp, 404, "NOT_FOUND", "Product not found");
                    return;
                }
                JsonUtil.ok(resp, product.get());
                return;
            }

            String keyword = req.getParameter("q");
            String category = req.getParameter("category");
            JsonUtil.ok(resp, productService.browse(keyword, category));
        } catch (NumberFormatException e) {
            JsonUtil.error(resp, 400, "VALIDATION_ERROR", "Invalid product id");
        } catch (SQLException e) {
            JsonUtil.error(resp, 500, "SERVER_ERROR", "Could not load products");
        }
    }
}
