package com.ptms.app.dao;

import com.ptms.app.model.TicketManagement;
import com.ptms.app.util.DBConnection;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class TicketManagementDAO {

    private static final Logger LOGGER =
            Logger.getLogger(TicketManagementDAO.class.getName());

    public void addTicket(TicketManagement ticket) {

        String sql = "INSERT INTO ticket_management " +
                "(project_id, title, description, priority, deadline, assigned_to, status) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            statement.setInt(1, ticket.getProjectId());
            statement.setString(2, ticket.getTitle());
            statement.setString(3, ticket.getDescription());
            statement.setString(4, ticket.getPriority());
            statement.setDate(5, Date.valueOf(ticket.getDeadline()));
            statement.setInt(6, ticket.getAssignedTo());
            statement.setString(7, ticket.getStatus());

            int rows = statement.executeUpdate();

            if (rows > 0) {
                try (ResultSet resultSet = statement.getGeneratedKeys()) {
                    if (resultSet.next()) {
                        ticket.setId(resultSet.getInt(1));
                    }
                }
                LOGGER.info("Ticket added successfully. ID: " + ticket.getId());
            } else {
                LOGGER.warning("Ticket was not added.");
            }

        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE,
                    "Error while adding ticket", e);
        }
    }

    public TicketManagement getTicketById(int id) {

        String sql =
                "SELECT id, project_id, title, description, priority, " +
                        "deadline, assigned_to, status " +
                        "FROM ticket_management WHERE id = ?";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, id);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return mapTicket(resultSet);
                }
            }

        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE,
                    "Error while fetching ticket ID: " + id, e);
        }

        return null;
    }

    public List<TicketManagement> getAllTickets() {

        String sql =
                "SELECT id, project_id, title, description, priority, " +
                        "deadline, assigned_to, status " +
                        "FROM ticket_management ORDER BY id";

        List<TicketManagement> tickets = new ArrayList<>();

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                tickets.add(mapTicket(resultSet));
            }

        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE,
                    "Error while fetching all tickets", e);
        }

        return tickets;
    }

    public List<TicketManagement> searchTicketsByStatus(String status) {

        String sql =
                "SELECT id, project_id, title, description, priority, " +
                        "deadline, assigned_to, status " +
                        "FROM ticket_management WHERE status = ? ORDER BY id";

        List<TicketManagement> tickets = new ArrayList<>();

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, status);

            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    tickets.add(mapTicket(resultSet));
                }
            }

        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE,
                    "Error while searching tickets by status", e);
        }

        return tickets;
    }

    public List<TicketManagement> searchTicketsByPriority(String priority) {

        String sql =
                "SELECT id, project_id, title, description, priority, " +
                        "deadline, assigned_to, status " +
                        "FROM ticket_management WHERE priority = ? ORDER BY id";

        List<TicketManagement> tickets = new ArrayList<>();

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, priority);

            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    tickets.add(mapTicket(resultSet));
                }
            }

        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE,
                    "Error while searching tickets by priority", e);
        }

        return tickets;
    }

    public List<TicketManagement> searchTicketsByAssignedTo(int userId) {

        String sql =
                "SELECT id, project_id, title, description, priority, " +
                        "deadline, assigned_to, status " +
                        "FROM ticket_management WHERE assigned_to = ? ORDER BY id";

        List<TicketManagement> tickets = new ArrayList<>();

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, userId);

            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    tickets.add(mapTicket(resultSet));
                }
            }

        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE,
                    "Error while searching tickets by assigned user", e);
        }

        return tickets;
    }

    public void updateTicket(TicketManagement ticket) {

        String sql =
                "UPDATE ticket_management SET " +
                        "project_id = ?, title = ?, description = ?, " +
                        "priority = ?, deadline = ?, assigned_to = ?, status = ? " +
                        "WHERE id = ?";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, ticket.getProjectId());
            statement.setString(2, ticket.getTitle());
            statement.setString(3, ticket.getDescription());
            statement.setString(4, ticket.getPriority());
            statement.setDate(5, Date.valueOf(ticket.getDeadline()));
            statement.setInt(6, ticket.getAssignedTo());
            statement.setString(7, ticket.getStatus());
            statement.setInt(8, ticket.getId());

            int rows = statement.executeUpdate();

            if (rows > 0) {
                LOGGER.info("Ticket updated successfully. ID: "
                        + ticket.getId());
            } else {
                LOGGER.warning("Ticket not found. ID: "
                        + ticket.getId());
            }

        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE,
                    "Error while updating ticket ID: "
                            + ticket.getId(), e);
        }
    }

    public void deleteTicket(int id) {

        String sql = "DELETE FROM ticket_management WHERE id = ?";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, id);

            int rows = statement.executeUpdate();

            if (rows > 0) {
                LOGGER.info("Ticket deleted successfully. ID: " + id);
            } else {
                LOGGER.warning("Ticket not found. ID: " + id);
            }

        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE,
                    "Error while deleting ticket ID: " + id, e);
        }
    }

    private TicketManagement mapTicket(ResultSet resultSet)
            throws SQLException {

        TicketManagement ticket = new TicketManagement();

        ticket.setId(resultSet.getInt("id"));
        ticket.setProjectId(resultSet.getInt("project_id"));
        ticket.setTitle(resultSet.getString("title"));
        ticket.setDescription(resultSet.getString("description"));
        ticket.setPriority(resultSet.getString("priority"));

        Date deadline = resultSet.getDate("deadline");

        if (deadline != null) {
            ticket.setDeadline(deadline.toLocalDate());
        }

        ticket.setAssignedTo(resultSet.getInt("assigned_to"));
        ticket.setStatus(resultSet.getString("status"));

        return ticket;
    }
}