package com.zeenat.zeemart.controller;

import com.zeenat.zeemart.exception.ValidationException;
import com.zeenat.zeemart.model.User;
import com.zeenat.zeemart.service.UserService;
import com.zeenat.zeemart.util.JsonUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/api/v1/auth/register")
public class RegisterServlet extends HttpServlet {
    private final UserService userService = new UserService();

    static class RegisterRequest {
        String name, email, password, role;
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            RegisterRequest body = JsonUtil.readBody(req, RegisterRequest.class);
            if (body == null) {
                JsonUtil.error(resp, 400, "VALIDATION_ERROR", "Request body is required");
                return;
            }
            User user = userService.register(body.name, body.email, body.password, body.role);
            JsonUtil.created(resp, java.util.Map.of("id", user.getId(), "email", user.getEmail(), "role", user.getRole()));
        } catch (ValidationException e) {
            JsonUtil.error(resp, 400, "VALIDATION_ERROR", e.getMessage());
        } catch (SQLException e) {
            JsonUtil.error(resp, 500, "SERVER_ERROR", "Registration failed");
        }
    }
}
