package com.zeenat.zeemart.controller;

import com.zeenat.zeemart.exception.ValidationException;
import com.zeenat.zeemart.model.Order;
import com.zeenat.zeemart.service.OrderService;
import com.zeenat.zeemart.util.JsonUtil;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet({"/api/v1/orders", "/api/v1/orders/*"})
public class OrderServlet extends HttpServlet {
    private final OrderService orderService = new OrderService();

    private int userId(HttpServletRequest req) {
        HttpSession session = req.getSession(false);
        return (int) session.getAttribute("userId");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        try {
            Order order = orderService.checkout(userId(req));
            JsonUtil.created(resp, order);
        } catch (ValidationException e) {
            JsonUtil.error(resp, 400, "VALIDATION_ERROR", e.getMessage());
        } catch (SQLException e) {
            JsonUtil.error(resp, 500, "SERVER_ERROR", "Checkout failed");
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        try {
            JsonUtil.ok(resp, orderService.history(userId(req)));
        } catch (SQLException e) {
            JsonUtil.error(resp, 500, "SERVER_ERROR", "Could not load orders");
        }
    }
}
