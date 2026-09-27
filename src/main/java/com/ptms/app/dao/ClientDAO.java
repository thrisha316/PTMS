package com.ptms.app.dao;

import com.ptms.app.model.Client;
import com.ptms.app.util.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ClientDAO {

    private static final Logger LOGGER =
            Logger.getLogger(ClientDAO.class.getName());

    public void addClient(Client client) {

        String sql = "INSERT INTO clients " +
                "(name, email, phone, company_name) " +
                "VALUES (?, ?, ?, ?)";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(
                             sql,
                             Statement.RETURN_GENERATED_KEYS)) {

            statement.setString(1, client.getName());
            statement.setString(2, client.getEmail());
            statement.setString(3, client.getPhone());
            statement.setString(4, client.getCompanyName());

            int rows = statement.executeUpdate();

            if (rows > 0) {
                try (ResultSet resultSet = statement.getGeneratedKeys()) {
                    if (resultSet.next()) {
                        client.setId(resultSet.getInt(1));
                    }
                }

                LOGGER.info("Client added successfully. ID: "
                        + client.getId());
            } else {
                LOGGER.warning("Client was not added.");
            }

        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE,
                    "Error while adding client", e);
        }
    }

    public Client getClientById(int id) {

        String sql = "SELECT id, name, email, phone, company_name " +
                "FROM clients WHERE id = ?";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(sql)) {

            statement.setInt(1, id);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return mapClient(resultSet);
                }
            }

        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE,
                    "Error while fetching client ID: " + id, e);
        }

        return null;
    }

    public Client getClientByEmail(String email) {

        String sql = "SELECT id, name, email, phone, company_name " +
                "FROM clients WHERE email = ?";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(sql)) {

            statement.setString(1, email);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return mapClient(resultSet);
                }
            }

        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE,
                    "Error while fetching client by email: " + email, e);
        }

        return null;
    }

    public List<Client> getAllClients() {

        String sql = "SELECT id, name, email, phone, company_name " +
                "FROM clients ORDER BY id";

        List<Client> clients = new ArrayList<>();

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                clients.add(mapClient(resultSet));
            }

        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE,
                    "Error while fetching all clients", e);
        }

        return clients;
    }

    public List<Client> searchClients(String name) {

        String sql = "SELECT id, name, email, phone, company_name " +
                "FROM clients WHERE name LIKE ? ORDER BY id";

        List<Client> clients = new ArrayList<>();

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(sql)) {

            statement.setString(1, "%" + name + "%");

            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    clients.add(mapClient(resultSet));
                }
            }

        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE,
                    "Error while searching clients", e);
        }

        return clients;
    }

    public void updateClient(Client client) {

        String sql = "UPDATE clients SET " +
                "name = ?, email = ?, phone = ?, company_name = ? " +
                "WHERE id = ?";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(sql)) {

            statement.setString(1, client.getName());
            statement.setString(2, client.getEmail());
            statement.setString(3, client.getPhone());
            statement.setString(4, client.getCompanyName());
            statement.setInt(5, client.getId());

            int rows = statement.executeUpdate();

            if (rows > 0) {
                LOGGER.info("Client updated successfully. ID: "
                        + client.getId());
            } else {
                LOGGER.warning("Client not found. ID: "
                        + client.getId());
            }

        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE,
                    "Error while updating client ID: "
                            + client.getId(), e);
        }
    }

    public void deleteClient(int id) {

        String sql = "DELETE FROM clients WHERE id = ?";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(sql)) {

            statement.setInt(1, id);

            int rows = statement.executeUpdate();

            if (rows > 0) {
                LOGGER.info("Client deleted successfully. ID: " + id);
            } else {
                LOGGER.warning("Client not found. ID: " + id);
            }

        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE,
                    "Error while deleting client ID: " + id, e);
        }
    }

    private Client mapClient(ResultSet resultSet)
            throws SQLException {

        Client client = new Client();

        client.setId(resultSet.getInt("id"));
        client.setName(resultSet.getString("name"));
        client.setEmail(resultSet.getString("email"));
        client.setPhone(resultSet.getString("phone"));
        client.setCompanyName(resultSet.getString("company_name"));

        return client;
    }
}