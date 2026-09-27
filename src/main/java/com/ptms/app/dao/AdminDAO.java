package com.ptms.app.dao;

import com.ptms.app.model.Admin;
import com.ptms.app.util.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

public class AdminDAO {

    private static final Logger LOGGER =
            Logger.getLogger(AdminDAO.class.getName());

    public void addAdmin(Admin admin) {

        String sql = "INSERT INTO admin (user_id, access_level) VALUES (?, ?)";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            statement.setInt(1, admin.getUserId());
            statement.setString(2, admin.getAccessLevel());

            int rows = statement.executeUpdate();

            if (rows > 0) {
                try (ResultSet resultSet = statement.getGeneratedKeys()) {
                    if (resultSet.next()) {
                        admin.setId(resultSet.getInt(1));
                    }
                }

                LOGGER.info("Admin added successfully. ID: "
                        + admin.getId());
            }

        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE,
                    "Error while adding admin", e);
        }
    }

    public Admin getAdminById(int id) {

        String sql =
                "SELECT id, user_id, access_level " +
                        "FROM admin WHERE id = ?";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(sql)) {

            statement.setInt(1, id);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return mapAdmin(resultSet);
                }
            }

        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE,
                    "Error while fetching admin ID: " + id, e);
        }

        return null;
    }

    public Admin getAdminByUserId(int userId) {

        String sql =
                "SELECT id, user_id, access_level " +
                        "FROM admin WHERE user_id = ?";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(sql)) {

            statement.setInt(1, userId);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return mapAdmin(resultSet);
                }
            }

        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE,
                    "Error while fetching admin for user ID: "
                            + userId, e);
        }

        return null;
    }

    public void updateAdmin(Admin admin) {

        String sql =
                "UPDATE admin SET user_id = ?, access_level = ? " +
                        "WHERE id = ?";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(sql)) {

            statement.setInt(1, admin.getUserId());
            statement.setString(2, admin.getAccessLevel());
            statement.setInt(3, admin.getId());

            int rows = statement.executeUpdate();

            if (rows > 0) {
                LOGGER.info("Admin updated successfully. ID: "
                        + admin.getId());
            } else {
                LOGGER.warning("Admin not found. ID: "
                        + admin.getId());
            }

        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE,
                    "Error while updating admin ID: "
                            + admin.getId(), e);
        }
    }

    public void deleteAdmin(int id) {

        String sql = "DELETE FROM admin WHERE id = ?";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(sql)) {

            statement.setInt(1, id);

            int rows = statement.executeUpdate();

            if (rows > 0) {
                LOGGER.info("Admin deleted successfully. ID: " + id);
            } else {
                LOGGER.warning("Admin not found. ID: " + id);
            }

        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE,
                    "Error while deleting admin ID: " + id, e);
        }
    }

    private Admin mapAdmin(ResultSet resultSet)
            throws SQLException {

        Admin admin = new Admin();

        admin.setId(resultSet.getInt("id"));
        admin.setUserId(resultSet.getInt("user_id"));
        admin.setAccessLevel(resultSet.getString("access_level"));

        return admin;
    }
}