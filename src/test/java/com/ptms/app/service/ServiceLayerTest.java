package com.ptms.app.service;

import com.ptms.app.dao.ClientDAO;
import com.ptms.app.dao.ProjectDAO;
import com.ptms.app.dao.TicketManagementDAO;
import com.ptms.app.dao.TicketTrackingDAO;
import com.ptms.app.model.Client;
import com.ptms.app.model.Project;
import com.ptms.app.model.TicketManagement;
import com.ptms.app.model.TicketTracking;
import org.junit.jupiter.api.Test;
import org.mockito.MockedConstruction;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.mockConstruction;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class ServiceLayerTest {

    @Test
    void testClientService() {
        try (MockedConstruction<ClientDAO> mocked =
                     mockConstruction(ClientDAO.class)) {

            ClientService service = new ClientService();
            Client client = mock(Client.class);

            when(mocked.constructed().get(0).getClientById(1))
                    .thenReturn(client);

            Client result = service.getClientById(1);

            assertSame(client, result);
            verify(mocked.constructed().get(0))
                    .getClientById(1);
        }
    }

    @Test
    void testProjectService() {
        try (MockedConstruction<ProjectDAO> mocked =
                     mockConstruction(ProjectDAO.class)) {

            ProjectService service = new ProjectService();
            Project project = mock(Project.class);

            when(mocked.constructed().get(0).getProjectById(4))
                    .thenReturn(project);

            Project result = service.getProjectById(4);

            assertSame(project, result);
            verify(mocked.constructed().get(0))
                    .getProjectById(4);
        }
    }

    @Test
    void testTicketManagementService() {
        try (MockedConstruction<TicketManagementDAO> mocked =
                     mockConstruction(TicketManagementDAO.class)) {

            TicketManagementService service =
                    new TicketManagementService();

            TicketManagement ticket =
                    mock(TicketManagement.class);

            when(mocked.constructed().get(0).getTicketById(6))
                    .thenReturn(ticket);

            TicketManagement result =
                    service.getTicketById(6);

            assertSame(ticket, result);

            verify(mocked.constructed().get(0))
                    .getTicketById(6);
        }
    }

    @Test
    void testTicketTrackingService() {
        try (MockedConstruction<TicketTrackingDAO> mocked =
                     mockConstruction(TicketTrackingDAO.class)) {

            TicketTrackingService service =
                    new TicketTrackingService();

            TicketTracking tracking =
                    mock(TicketTracking.class);

            when(mocked.constructed().get(0)
                    .getTrackingByTicketId(6))
                    .thenReturn(tracking);

            TicketTracking result =
                    service.getTrackingByTicketId(6);

            assertSame(tracking, result);

            verify(mocked.constructed().get(0))
                    .getTrackingByTicketId(6);
        }
    }
}