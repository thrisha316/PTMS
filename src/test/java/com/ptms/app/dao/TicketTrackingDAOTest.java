package com.ptms.app.dao;

import com.ptms.app.model.TicketTracking;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class TicketTrackingDAOTest {

    @Test
    void testTicketTrackingCRUD() {

        TicketTrackingDAO dao = new TicketTrackingDAO();

        TicketTracking tracking = new TicketTracking(
                1,
                5,
                "IN_PROGRESS",
                50,
                "Development is in progress"
        );

        dao.addTracking(tracking);

        TicketTracking savedTracking =
                dao.getTrackingByTicketId(1);

        assertNotNull(savedTracking);
        assertEquals(1, savedTracking.getTicketId());

        tracking.setId(savedTracking.getId());
        tracking.setStatus("COMPLETED");
        tracking.setProgress(100);
        tracking.setComment("Ticket completed successfully");

        dao.updateTracking(tracking);

        TicketTracking updatedTracking =
                dao.getTrackingByTicketId(1);

        assertNotNull(updatedTracking);
        assertEquals("COMPLETED",
                updatedTracking.getStatus());
        assertEquals(100,
                updatedTracking.getProgress());

        List<TicketTracking> trackingList =
                dao.getAllTracking();

        assertFalse(trackingList.isEmpty());

        dao.deleteTracking(tracking.getId());

        TicketTracking deletedTracking =
                dao.getTrackingByTicketId(1);

        assertNull(deletedTracking);
    }
}