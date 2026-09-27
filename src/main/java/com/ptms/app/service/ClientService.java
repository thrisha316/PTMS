package com.ptms.app.service;

import com.ptms.app.dao.ClientDAO;
import com.ptms.app.model.Client;

import java.util.List;
import java.util.logging.Logger;

public class ClientService {

    private static final Logger LOGGER =
            Logger.getLogger(ClientService.class.getName());

    private final ClientDAO clientDAO;

    public ClientService() {
        clientDAO = new ClientDAO();
    }

    public void addClient(Client client) {
        validateClient(client);
        clientDAO.addClient(client);
        LOGGER.info("Client added through service. ID: "
                + client.getId());
    }

    public Client getClientById(int id) {
        if (id <= 0) {
            throw new IllegalArgumentException("Invalid client ID.");
        }

        return clientDAO.getClientById(id);
    }

    public Client getClientByEmail(String email) {
        if (email == null || email.isBlank()) {
            throw new IllegalArgumentException("Email is required.");
        }

        return clientDAO.getClientByEmail(email);
    }

    public List<Client> getAllClients() {
        return clientDAO.getAllClients();
    }

    public List<Client> searchClients(String name) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException(
                    "Client name is required.");
        }

        return clientDAO.searchClients(name);
    }

    public void updateClient(Client client) {
        if (client == null || client.getId() <= 0) {
            throw new IllegalArgumentException("Invalid client.");
        }

        validateClient(client);
        clientDAO.updateClient(client);

        LOGGER.info("Client updated through service. ID: "
                + client.getId());
    }

    public void deleteClient(int id) {
        if (id <= 0) {
            throw new IllegalArgumentException("Invalid client ID.");
        }

        clientDAO.deleteClient(id);

        LOGGER.info("Client deleted through service. ID: " + id);
    }

    private void validateClient(Client client) {

        if (client == null) {
            throw new IllegalArgumentException(
                    "Client cannot be null.");
        }

        if (client.getName() == null ||
                client.getName().isBlank()) {
            throw new IllegalArgumentException(
                    "Client name is required.");
        }

        if (client.getEmail() == null ||
                client.getEmail().isBlank()) {
            throw new IllegalArgumentException(
                    "Client email is required.");
        }

        if (client.getPhone() == null ||
                client.getPhone().isBlank()) {
            throw new IllegalArgumentException(
                    "Client phone is required.");
        }

        if (client.getCompanyName() == null ||
                client.getCompanyName().isBlank()) {
            throw new IllegalArgumentException(
                    "Company name is required.");
        }
    }
}