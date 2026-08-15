package com.zeenat.zeemart.dao;

import com.zeenat.zeemart.model.User;
import java.sql.SQLException;
import java.util.Optional;

public interface UserDAO {
    User create(User user) throws SQLException;
    Optional<User> findByEmail(String email) throws SQLException;
    Optional<User> findById(int id) throws SQLException;
    boolean emailExists(String email) throws SQLException;
}
