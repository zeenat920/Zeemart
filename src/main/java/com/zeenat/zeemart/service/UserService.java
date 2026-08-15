package com.zeenat.zeemart.service;

import com.zeenat.zeemart.dao.UserDAO;
import com.zeenat.zeemart.dao.UserDAOImpl;
import com.zeenat.zeemart.exception.ValidationException;
import com.zeenat.zeemart.model.User;
import com.zeenat.zeemart.util.PasswordUtil;

import java.sql.SQLException;
import java.util.Optional;

public class UserService {
    private final UserDAO userDAO = new UserDAOImpl();

    public User register(String name, String email, String password, String role) throws ValidationException, SQLException {
        if (name == null || name.isBlank()) throw new ValidationException("name", "Name is required");
        if (email == null || !email.matches("^[^@\\s]+@[^@\\s]+\\.[^@\\s]+$"))
            throw new ValidationException("email", "Valid email is required");
        if (password == null || password.length() < 8)
            throw new ValidationException("password", "Password must be at least 8 characters");

        User.Role parsedRole;
        try {
            parsedRole = User.Role.valueOf(role == null ? "" : role.toUpperCase());
            if (parsedRole == User.Role.ADMIN) throw new IllegalArgumentException();
        } catch (IllegalArgumentException e) {
            throw new ValidationException("role", "Role must be BUYER or SELLER");
        }

        if (userDAO.emailExists(email)) {
            throw new ValidationException("email", "Email already registered");
        }

        User user = new User();
        user.setName(name);
        user.setEmail(email);
        user.setPasswordHash(PasswordUtil.hash(password));
        user.setRole(parsedRole);
        return userDAO.create(user);
    }

    public Optional<User> authenticate(String email, String password) throws SQLException {
        Optional<User> user = userDAO.findByEmail(email);
        if (user.isPresent() && PasswordUtil.matches(password, user.get().getPasswordHash())) {
            return user;
        }
        return Optional.empty();
    }
}
