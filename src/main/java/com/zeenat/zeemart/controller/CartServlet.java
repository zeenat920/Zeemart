package com.zeenat.zeemart.controller;

import com.zeenat.zeemart.exception.ValidationException;
import com.zeenat.zeemart.service.CartService;
import com.zeenat.zeemart.util.JsonUtil;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet({"/api/v1/cart", "/api/v1/cart/*"})
public class CartServlet extends HttpServlet {
    private final CartService cartService = new CartService();

    static class AddRequest { int productId; int quantity; }
    static class UpdateRequest { int itemId; int quantity; }

    private int userId(HttpServletRequest req) {
        HttpSession session = req.getSession(false);
        return (int) session.getAttribute("userId");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        try {
            JsonUtil.ok(resp, cartService.viewCart(userId(req)));
        } catch (SQLException e) {
            JsonUtil.error(resp, 500, "SERVER_ERROR", "Could not load cart");
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        try {
            AddRequest body = JsonUtil.readBody(req, AddRequest.class);
            if (body == null) {
                JsonUtil.error(resp, 400, "VALIDATION_ERROR", "Request body is required");
                return;
            }
            cartService.addItem(userId(req), body.productId, body.quantity);
            JsonUtil.created(resp, cartService.viewCart(userId(req)));
        } catch (ValidationException e) {
            JsonUtil.error(resp, 400, "VALIDATION_ERROR", e.getMessage());
        } catch (SQLException e) {
            JsonUtil.error(resp, 500, "SERVER_ERROR", "Could not add to cart");
        }
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        try {
            UpdateRequest body = JsonUtil.readBody(req, UpdateRequest.class);
            if (body == null) {
                JsonUtil.error(resp, 400, "VALIDATION_ERROR", "Request body is required");
                return;
            }
            cartService.updateItem(userId(req), body.itemId, body.quantity);
            JsonUtil.ok(resp, cartService.viewCart(userId(req)));
        } catch (ValidationException e) {
            JsonUtil.error(resp, 400, "VALIDATION_ERROR", e.getMessage());
        } catch (SQLException e) {
            JsonUtil.error(resp, 500, "SERVER_ERROR", "Could not update cart");
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        try {
            String itemIdParam = req.getParameter("itemId");
            if (itemIdParam == null) {
                JsonUtil.error(resp, 400, "VALIDATION_ERROR", "itemId query param is required");
                return;
            }
            cartService.removeItem(userId(req), Integer.parseInt(itemIdParam));
            JsonUtil.ok(resp, cartService.viewCart(userId(req)));
        } catch (NumberFormatException e) {
            JsonUtil.error(resp, 400, "VALIDATION_ERROR", "Invalid itemId");
        } catch (SQLException e) {
            JsonUtil.error(resp, 500, "SERVER_ERROR", "Could not remove item");
        }
    }
}
