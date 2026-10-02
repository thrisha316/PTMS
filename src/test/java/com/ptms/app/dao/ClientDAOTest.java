package com.ptms.app.dao;

import com.ptms.app.model.Client;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ClientDAOTest {

    @Test
    void testAddClient() throws Exception {

        Client client = new Client(
                "Test Client",
                "testclient_" + System.currentTimeMillis() + "@gmail.com",
                "9876543211",
                "Test Company"
        );

        ClientDAO clientDAO = new ClientDAO();

        clientDAO.addClient(client);

        assertTrue(client.getId() > 0);
    }

    @Test
    void testGetClientById() {

        ClientDAO clientDAO = new ClientDAO();

        String email =
                "getid_" + System.currentTimeMillis() + "@gmail.com";

        Client client = new Client(
                "Get ID Client",
                email,
                "9876543212",
                "Get ID Company"
        );

        clientDAO.addClient(client);

        Client savedClient =
                clientDAO.getClientById(client.getId());

        assertNotNull(savedClient);
        assertEquals(client.getId(), savedClient.getId());
    }

    @Test
    void testGetClientByEmail() {

        ClientDAO clientDAO = new ClientDAO();

        String email =
                "getemail_" + System.currentTimeMillis() + "@gmail.com";

        Client client = new Client(
                "Get Email Client",
                email,
                "9876543213",
                "Get Email Company"
        );

        clientDAO.addClient(client);

        Client savedClient =
                clientDAO.getClientByEmail(email);

        assertNotNull(savedClient);
        assertEquals(email, savedClient.getEmail());
    }

    @Test
    void testGetAllClients() {

        ClientDAO clientDAO = new ClientDAO();

        assertNotNull(clientDAO.getAllClients());
    }

    @Test
    void testUpdateClient() {

        ClientDAO clientDAO = new ClientDAO();

        String email =
                "update_" + System.currentTimeMillis() + "@gmail.com";

        Client client = new Client(
                "Update Client",
                email,
                "9876543214",
                "Update Company"
        );

        clientDAO.addClient(client);

        Client savedClient =
                clientDAO.getClientById(client.getId());

        assertNotNull(savedClient);

        savedClient.setName("Updated Client");
        savedClient.setPhone("9999999999");

        clientDAO.updateClient(savedClient);

        Client updatedClient =
                clientDAO.getClientById(savedClient.getId());

        assertNotNull(updatedClient);
        assertEquals("Updated Client", updatedClient.getName());
        assertEquals("9999999999", updatedClient.getPhone());
    }

    @Test
    void testDeleteClient() {

        ClientDAO clientDAO = new ClientDAO();

        String email =
                "delete_" + System.currentTimeMillis() + "@gmail.com";

        Client client = new Client(
                "Delete Client",
                email,
                "9876543215",
                "Delete Company"
        );

        clientDAO.addClient(client);

        int clientId = client.getId();

        assertNotNull(clientDAO.getClientById(clientId));

        clientDAO.deleteClient(clientId);

        assertNull(clientDAO.getClientById(clientId));
    }
}