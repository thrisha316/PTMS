package com.ptms.app.controller;

import com.ptms.app.model.TicketTracking;
import com.ptms.app.service.TicketTrackingService;

import java.util.List;
import java.util.logging.Logger;

public class TicketTrackingController {

    private static final Logger LOGGER =
            Logger.getLogger(TicketTrackingController.class.getName());

    private final TicketTrackingService ticketTrackingService;

    public TicketTrackingController() {
        ticketTrackingService = new TicketTrackingService();
    }

    public void addTracking(TicketTracking tracking) {
        ticketTrackingService.addTracking(tracking);
        LOGGER.info("Ticket tracking add operation completed.");
    }

    public TicketTracking getTrackingByTicketId(int ticketId) {
        return ticketTrackingService.getTrackingByTicketId(ticketId);
    }

    public List<TicketTracking> getAllTracking() {
        return ticketTrackingService.getAllTracking();
    }

    public void updateTracking(TicketTracking tracking) {
        ticketTrackingService.updateTracking(tracking);
        LOGGER.info("Ticket tracking update operation completed.");
    }

    public void deleteTracking(int id) {
        ticketTrackingService.deleteTracking(id);
        LOGGER.info("Ticket tracking delete operation completed.");
    }
}