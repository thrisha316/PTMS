package com.ptms.app.service;

import com.ptms.app.dao.TicketTrackingDAO;
import com.ptms.app.model.TicketTracking;

import java.util.List;
import java.util.logging.Logger;

public class TicketTrackingService {

    private static final Logger LOGGER =
            Logger.getLogger(TicketTrackingService.class.getName());

    private final TicketTrackingDAO ticketTrackingDAO;

    public TicketTrackingService() {
        ticketTrackingDAO = new TicketTrackingDAO();
    }

    public void addTracking(TicketTracking tracking) {
        ticketTrackingDAO.addTracking(tracking);
        LOGGER.info("Ticket tracking added through service.");
    }

    public TicketTracking getTrackingByTicketId(int ticketId) {
        return ticketTrackingDAO.getTrackingByTicketId(ticketId);
    }

    public List<TicketTracking> getAllTracking() {
        return ticketTrackingDAO.getAllTracking();
    }

    public void updateTracking(TicketTracking tracking) {
        ticketTrackingDAO.updateTracking(tracking);
        LOGGER.info("Ticket tracking updated through service. ID: "
                + tracking.getId());
    }

    public void deleteTracking(int id) {
        ticketTrackingDAO.deleteTracking(id);
        LOGGER.info("Ticket tracking deleted through service. ID: " + id);
    }
}