package com.ptms.app.controller;

import com.ptms.app.model.Client;
import com.ptms.app.service.ClientService;

import java.util.List;
import java.util.logging.Logger;

public class ClientController {

    private static final Logger LOGGER =
            Logger.getLogger(ClientController.class.getName());

    private final ClientService clientService;

    public ClientController() {
        clientService = new ClientService();
    }

    public void addClient(Client client) {
        clientService.addClient(client);
        LOGGER.info("Client add operation completed.");
    }

    public Client getClientById(int id) {
        return clientService.getClientById(id);
    }

    public Client getClientByEmail(String email) {
        return clientService.getClientByEmail(email);
    }

    public List<Client> getAllClients() {
        return clientService.getAllClients();
    }

    public List<Client> searchClients(String name) {
        return clientService.searchClients(name);
    }

    public void updateClient(Client client) {
        clientService.updateClient(client);
        LOGGER.info("Client update operation completed.");
    }

    public void deleteClient(int id) {
        clientService.deleteClient(id);
        LOGGER.info("Client delete operation completed.");
    }
}