package com.ptms.app.service;

import com.ptms.app.dao.TicketTrackingDAO;
import com.ptms.app.exception.ResourceNotFoundException;
import com.ptms.app.exception.ValidationException;
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
        validateTracking(tracking);

        ticketTrackingDAO.addTracking(tracking);

        LOGGER.info("Ticket tracking added through service.");
    }

    public TicketTracking getTrackingByTicketId(int ticketId) {
        if (ticketId <= 0) {
            throw new ValidationException("Invalid ticket ID.");
        }

        TicketTracking tracking =
                ticketTrackingDAO.getTrackingByTicketId(ticketId);

        if (tracking == null) {
            throw new ResourceNotFoundException(
                    "Ticket tracking not found for ticket ID: " + ticketId);
        }

        return tracking;
    }

    public List<TicketTracking> getAllTracking() {
        return ticketTrackingDAO.getAllTracking();
    }

    public void updateTracking(TicketTracking tracking) {
        validateTracking(tracking);

        if (tracking.getId() <= 0) {
            throw new ValidationException(
                    "Invalid tracking ID.");
        }

        ticketTrackingDAO.updateTracking(tracking);

        LOGGER.info("Ticket tracking updated through service. ID: "
                + tracking.getId());
    }

    public void deleteTracking(int id) {
        if (id <= 0) {
            throw new ValidationException(
                    "Invalid tracking ID.");
        }

        ticketTrackingDAO.deleteTracking(id);

        LOGGER.info("Ticket tracking deleted through service. ID: " + id);
    }

    private void validateTracking(TicketTracking tracking) {
        if (tracking == null) {
            throw new ValidationException(
                    "Tracking cannot be null.");
        }

        if (tracking.getTicketId() <= 0) {
            throw new ValidationException(
                    "Invalid ticket ID.");
        }

        if (tracking.getStatus() == null ||
                tracking.getStatus().isBlank()) {
            throw new ValidationException(
                    "Tracking status is required.");
        }

        if (tracking.getProgress() < 0 ||
                tracking.getProgress() > 100) {
            throw new ValidationException(
                    "Progress must be between 0 and 100.");
        }

        if (tracking.getUpdatedBy() <= 0) {
            throw new ValidationException(
                    "Invalid updated by user ID.");
        }
    }
}