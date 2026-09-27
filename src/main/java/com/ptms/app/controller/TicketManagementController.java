package com.ptms.app.controller;

import com.ptms.app.model.TicketManagement;
import com.ptms.app.service.TicketManagementService;

import java.util.List;
import java.util.logging.Logger;

public class TicketManagementController {

    private static final Logger LOGGER =
            Logger.getLogger(TicketManagementController.class.getName());

    private final TicketManagementService ticketManagementService;

    public TicketManagementController() {
        ticketManagementService = new TicketManagementService();
    }

    public void addTicket(TicketManagement ticket) {
        ticketManagementService.addTicket(ticket);
        LOGGER.info("Ticket add operation completed.");
    }

    public TicketManagement getTicketById(int id) {
        return ticketManagementService.getTicketById(id);
    }

    public List<TicketManagement> getAllTickets() {
        return ticketManagementService.getAllTickets();
    }

    public List<TicketManagement> searchTicketsByStatus(String status) {
        return ticketManagementService.searchTicketsByStatus(status);
    }

    public List<TicketManagement> searchTicketsByPriority(String priority) {
        return ticketManagementService.searchTicketsByPriority(priority);
    }

    public List<TicketManagement> searchTicketsByAssignedTo(int userId) {
        return ticketManagementService.searchTicketsByAssignedTo(userId);
    }

    public void updateTicket(TicketManagement ticket) {
        ticketManagementService.updateTicket(ticket);
        LOGGER.info("Ticket update operation completed.");
    }

    public void deleteTicket(int id) {
        ticketManagementService.deleteTicket(id);
        LOGGER.info("Ticket delete operation completed.");
    }
}