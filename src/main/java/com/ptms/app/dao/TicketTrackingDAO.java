package com.ptms.app.dao;

import com.ptms.app.model.TicketTracking;
import com.ptms.app.util.DBConnection;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class TicketTrackingDAO {

    private static final Logger LOGGER =
            Logger.getLogger(TicketTrackingDAO.class.getName());

    public void addTracking(TicketTracking tracking) {

        String sql = "INSERT INTO ticket_tracking " +
                "(ticket_id, updated_by, status, progress, comment) " +
                "VALUES (?, ?, ?, ?, ?)";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, tracking.getTicketId());
            statement.setInt(2, tracking.getUpdatedBy());
            statement.setString(3, tracking.getStatus());
            statement.setInt(4, tracking.getProgress());
            statement.setString(5, tracking.getComment());

            int rows = statement.executeUpdate();

            if (rows > 0) {
                LOGGER.info("Ticket tracking added successfully.");
            }

        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE,
                    "Error while adding ticket tracking", e);
        }
    }

    public TicketTracking getTrackingByTicketId(int ticketId) {

        String sql =
                "SELECT id, ticket_id, updated_by, status, progress, " +
                        "comment, updated_at " +
                        "FROM ticket_tracking " +
                        "WHERE ticket_id = ?";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(sql)) {

            statement.setInt(1, ticketId);

            try (ResultSet resultSet = statement.executeQuery()) {

                if (resultSet.next()) {
                    return mapTracking(resultSet);
                }
            }

        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE,
                    "Error while fetching tracking for ticket ID: "
                            + ticketId, e);
        }

        return null;
    }

    public List<TicketTracking> getAllTracking() {

        String sql =
                "SELECT id, ticket_id, updated_by, status, progress, " +
                        "comment, updated_at " +
                        "FROM ticket_tracking ORDER BY id";

        List<TicketTracking> trackingList = new ArrayList<>();

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                trackingList.add(mapTracking(resultSet));
            }

        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE,
                    "Error while fetching ticket tracking", e);
        }

        return trackingList;
    }

    public void updateTracking(TicketTracking tracking) {

        String sql =
                "UPDATE ticket_tracking " +
                        "SET ticket_id = ?, " +
                        "updated_by = ?, " +
                        "status = ?, " +
                        "progress = ?, " +
                        "comment = ? " +
                        "WHERE id = ?";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(sql)) {

            statement.setInt(1, tracking.getTicketId());
            statement.setInt(2, tracking.getUpdatedBy());
            statement.setString(3, tracking.getStatus());
            statement.setInt(4, tracking.getProgress());
            statement.setString(5, tracking.getComment());
            statement.setInt(6, tracking.getId());

            int rows = statement.executeUpdate();

            if (rows > 0) {
                LOGGER.info("Ticket tracking updated successfully. ID: "
                        + tracking.getId());
            }

        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE,
                    "Error while updating ticket tracking ID: "
                            + tracking.getId(), e);
        }
    }

    public void deleteTracking(int id) {

        String sql =
                "DELETE FROM ticket_tracking WHERE id = ?";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(sql)) {

            statement.setInt(1, id);

            int rows = statement.executeUpdate();

            if (rows > 0) {
                LOGGER.info("Ticket tracking deleted successfully. ID: "
                        + id);
            }

        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE,
                    "Error while deleting ticket tracking ID: "
                            + id, e);
        }
    }

    private TicketTracking mapTracking(ResultSet resultSet)
            throws SQLException {

        TicketTracking tracking = new TicketTracking();

        tracking.setId(resultSet.getInt("id"));
        tracking.setTicketId(resultSet.getInt("ticket_id"));
        tracking.setUpdatedBy(resultSet.getInt("updated_by"));
        tracking.setStatus(resultSet.getString("status"));
        tracking.setProgress(resultSet.getInt("progress"));
        tracking.setComment(resultSet.getString("comment"));

        Timestamp updatedAt = resultSet.getTimestamp("updated_at");

        if (updatedAt != null) {
            tracking.setUpdatedAt(updatedAt.toLocalDateTime());
        }

        return tracking;
    }
}