package com.ptms.app.dao;

import com.ptms.app.model.Role;
import com.ptms.app.util.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;

public class RoleDAO {

    public void addRole(Role role) throws Exception {

        String sql = "INSERT INTO roles (role_name) VALUES (?)";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, role.getRoleName());

            statement.executeUpdate();
        }
    }
}