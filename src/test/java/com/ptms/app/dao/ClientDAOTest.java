package com.ptms.app.dao;

import com.ptms.app.model.Client;
import org.junit.jupiter.api.Test;

class ClientDAOTest {

    @Test
    void testAddClient() throws Exception {

        Client client = new Client(
                "Test Client 2",
                "testclient2@gmail.com",
                "9876543211",
                "Test Company 2"
        );

        ClientDAO clientDAO = new ClientDAO();

        clientDAO.addClient(client);
    }

    @Test
    void testGetClientById() {

        ClientDAO clientDAO = new ClientDAO();

        Client client = clientDAO.getClientById(5);

        System.out.println(client);
    }

    @Test
    void testGetClientByEmail() {

        ClientDAO clientDAO = new ClientDAO();

        Client client = clientDAO.getClientByEmail(
                "testclient2@gmail.com"
        );

        System.out.println(client);
    }

    @Test
    void testGetAllClients() {

        ClientDAO clientDAO = new ClientDAO();

        System.out.println(clientDAO.getAllClients());
    }

    @Test
    void testUpdateClient() {

        ClientDAO clientDAO = new ClientDAO();

        Client client = clientDAO.getClientById(5);

        client.setName("Updated Client");
        client.setPhone("9999999999");

        clientDAO.updateClient(client);

        System.out.println(clientDAO.getClientById(5));
    }

    @Test
    void testDeleteClient() {

        ClientDAO clientDAO = new ClientDAO();

        clientDAO.deleteClient(5);
    }
}