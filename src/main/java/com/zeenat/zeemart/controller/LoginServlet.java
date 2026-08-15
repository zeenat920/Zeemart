package com.zeenat.zeemart.controller;

import com.zeenat.zeemart.model.User;
import com.zeenat.zeemart.service.UserService;
import com.zeenat.zeemart.util.JsonUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Optional;

@WebServlet("/api/v1/auth/login")
public class LoginServlet extends HttpServlet {
    private final UserService userService = new UserService();

    static class LoginRequest {
        String email, password;
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            LoginRequest body = JsonUtil.readBody(req, LoginRequest.class);
            if (body == null || body.email == null || body.password == null) {
                JsonUtil.error(resp, 400, "VALIDATION_ERROR", "Email and password are required");
                return;
            }
            Optional<User> user = userService.authenticate(body.email, body.password);
            if (user.isEmpty()) {
                JsonUtil.error(resp, 401, "INVALID_CREDENTIALS", "Invalid email or password");
                return;
            }

            HttpSession oldSession = req.getSession(false);
            if (oldSession != null) oldSession.invalidate();
            HttpSession session = req.getSession(true); // regenerates session ID
            session.setAttribute("userId", user.get().getId());
            session.setAttribute("role", user.get().getRole().name());

            JsonUtil.ok(resp, java.util.Map.of("id", user.get().getId(), "name", user.get().getName(), "role", user.get().getRole()));
        } catch (SQLException e) {
            JsonUtil.error(resp, 500, "SERVER_ERROR", "Login failed");
        }
    }
}
