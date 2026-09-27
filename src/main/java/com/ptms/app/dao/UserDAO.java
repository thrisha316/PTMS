package com.ptms.app.dao;

import com.ptms.app.model.User;
import com.ptms.app.util.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class UserDAO {

    private static final Logger LOGGER =
            Logger.getLogger(UserDAO.class.getName());

    public void addUser(User user) {

        String sql = "INSERT INTO users " +
                "(name, email, password_hash, role_id) " +
                "VALUES (?, ?, ?, ?)";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, user.getName());
            statement.setString(2, user.getEmail());
            statement.setString(3, user.getPasswordHash());
            statement.setInt(4, user.getRoleId());

            int rows = statement.executeUpdate();

            if (rows > 0) {
                LOGGER.info("User added successfully.");
            }

        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE,
                    "Error while adding user", e);
        }
    }

    public User getUserById(int id) {

        String sql = "SELECT id, name, email, password_hash, role_id " +
                "FROM users WHERE id = ?";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, id);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return mapUser(resultSet);
                }
            }

        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE,
                    "Error while fetching user ID: " + id, e);
        }

        return null;
    }

    public User getUserByEmail(String email) {

        String sql = "SELECT id, name, email, password_hash, role_id " +
                "FROM users WHERE email = ?";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, email);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return mapUser(resultSet);
                }
            }

        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE,
                    "Error while fetching user by email: " + email, e);
        }

        return null;
    }

    public List<User> getAllUsers() {

        String sql = "SELECT id, name, email, password_hash, role_id " +
                "FROM users ORDER BY id";

        List<User> users = new ArrayList<>();

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                users.add(mapUser(resultSet));
            }

        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE,
                    "Error while fetching users", e);
        }

        return users;
    }

    public List<User> searchUsers(String name) {

        String sql = "SELECT id, name, email, password_hash, role_id " +
                "FROM users WHERE name LIKE ? ORDER BY id";

        List<User> users = new ArrayList<>();

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, "%" + name + "%");

            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    users.add(mapUser(resultSet));
                }
            }

        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE,
                    "Error while searching users", e);
        }

        return users;
    }

    public void updateUser(User user) {

        String sql = "UPDATE users SET " +
                "name = ?, email = ?, password_hash = ?, role_id = ? " +
                "WHERE id = ?";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, user.getName());
            statement.setString(2, user.getEmail());
            statement.setString(3, user.getPasswordHash());
            statement.setInt(4, user.getRoleId());
            statement.setInt(5, user.getId());

            int rows = statement.executeUpdate();

            if (rows > 0) {
                LOGGER.info("User updated successfully. ID: "
                        + user.getId());
            } else {
                LOGGER.warning("User not found. ID: "
                        + user.getId());
            }

        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE,
                    "Error while updating user ID: "
                            + user.getId(), e);
        }
    }

    public void updateUserRole(int userId, int roleId) {

        String sql = "UPDATE users SET role_id = ? WHERE id = ?";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, roleId);
            statement.setInt(2, userId);

            int rows = statement.executeUpdate();

            if (rows > 0) {
                LOGGER.info("User role updated successfully. User ID: "
                        + userId);
            } else {
                LOGGER.warning("User not found. ID: " + userId);
            }

        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE,
                    "Error while updating user role", e);
        }
    }

    public void deleteUser(int id) {

        String sql = "DELETE FROM users WHERE id = ?";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, id);

            int rows = statement.executeUpdate();

            if (rows > 0) {
                LOGGER.info("User deleted successfully. ID: " + id);
            } else {
                LOGGER.warning("User not found. ID: " + id);
            }

        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE,
                    "Error while deleting user ID: " + id, e);
        }
    }

    private User mapUser(ResultSet resultSet)
            throws SQLException {

        User user = new User();

        user.setId(resultSet.getInt("id"));
        user.setName(resultSet.getString("name"));
        user.setEmail(resultSet.getString("email"));
        user.setPasswordHash(resultSet.getString("password_hash"));
        user.setRoleId(resultSet.getInt("role_id"));

        return user;
    }
}