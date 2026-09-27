package com.ptms.app.dao;

import com.ptms.app.model.TicketManagement;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class TicketManagementDAOTest {

    @Test
    void testTicketCRUD() {

        TicketManagementDAO dao = new TicketManagementDAO();

        TicketManagement ticket = new TicketManagement(
                4,
                "JUnit Test Ticket",
                "Testing ticket CRUD operations",
                "HIGH",
                LocalDate.of(2026, 12, 31),
                4,
                "OPEN"
        );

        dao.addTicket(ticket);

        assertTrue(ticket.getId() > 0);

        TicketManagement savedTicket =
                dao.getTicketById(ticket.getId());

        assertNotNull(savedTicket);
        assertEquals("JUnit Test Ticket", savedTicket.getTitle());

        ticket.setTitle("Updated JUnit Ticket");
        ticket.setStatus("IN_PROGRESS");

        dao.updateTicket(ticket);

        TicketManagement updatedTicket =
                dao.getTicketById(ticket.getId());

        assertNotNull(updatedTicket);
        assertEquals("Updated JUnit Ticket",
                updatedTicket.getTitle());
        assertEquals("IN_PROGRESS",
                updatedTicket.getStatus());

        List<TicketManagement> tickets =
                dao.getAllTickets();

        assertFalse(tickets.isEmpty());

        dao.deleteTicket(ticket.getId());

        TicketManagement deletedTicket =
                dao.getTicketById(ticket.getId());

        assertNull(deletedTicket);
    }
}