package com.ptms.app.controller;

import com.ptms.app.service.AdminService;
import com.ptms.app.service.ClientService;
import com.ptms.app.service.ProjectMemberService;
import com.ptms.app.service.ProjectService;
import com.ptms.app.service.TicketManagementService;
import com.ptms.app.service.TicketTrackingService;
import com.ptms.app.service.UserService;
import org.junit.jupiter.api.Test;
import org.mockito.MockedConstruction;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mockConstruction;

class ControllerLayerTest {

    @Test
    void testUserController() {
        try (MockedConstruction<UserService> mocked =
                     mockConstruction(UserService.class)) {

            UserController controller = new UserController();

            assertNotNull(controller);
            assertEquals(1, mocked.constructed().size());
        }
    }

    @Test
    void testClientController() {
        try (MockedConstruction<ClientService> mocked =
                     mockConstruction(ClientService.class)) {

            ClientController controller = new ClientController();

            assertNotNull(controller);
            assertEquals(1, mocked.constructed().size());
        }
    }

    @Test
    void testProjectController() {
        try (MockedConstruction<ProjectService> mocked =
                     mockConstruction(ProjectService.class)) {

            ProjectController controller = new ProjectController();

            assertNotNull(controller);
            assertEquals(1, mocked.constructed().size());
        }
    }

    @Test
    void testProjectMemberController() {
        try (MockedConstruction<ProjectMemberService> mocked =
                     mockConstruction(ProjectMemberService.class)) {

            ProjectMemberController controller =
                    new ProjectMemberController();

            assertNotNull(controller);
            assertEquals(1, mocked.constructed().size());
        }
    }

    @Test
    void testTicketManagementController() {
        try (MockedConstruction<TicketManagementService> mocked =
                     mockConstruction(TicketManagementService.class)) {

            TicketManagementController controller =
                    new TicketManagementController();

            assertNotNull(controller);
            assertEquals(1, mocked.constructed().size());
        }
    }

    @Test
    void testTicketTrackingController() {
        try (MockedConstruction<TicketTrackingService> mocked =
                     mockConstruction(TicketTrackingService.class)) {

            TicketTrackingController controller =
                    new TicketTrackingController();

            assertNotNull(controller);
            assertEquals(1, mocked.constructed().size());
        }
    }

    @Test
    void testAdminController() {
        try (MockedConstruction<AdminService> mocked =
                     mockConstruction(AdminService.class)) {

            AdminController controller = new AdminController();

            assertNotNull(controller);
            assertEquals(1, mocked.constructed().size());
        }
    }
}