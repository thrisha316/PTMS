package com.ptms.app.service;

import com.ptms.app.dao.ProjectDAO;
import com.ptms.app.dao.ProjectMemberDAO;
import com.ptms.app.dao.TicketManagementDAO;
import com.ptms.app.exception.ResourceNotFoundException;
import com.ptms.app.exception.ValidationException;
import com.ptms.app.model.Project;
import com.ptms.app.model.ProjectMember;
import com.ptms.app.model.TicketManagement;

import java.util.List;
import java.util.Set;
import java.util.logging.Logger;

public class TicketManagementService {

    private static final Logger LOGGER =
            Logger.getLogger(TicketManagementService.class.getName());

    private static final Set<String> VALID_STATUSES = Set.of(
            "IN_DEVELOPMENT",
            "IN_PROGRESS",
            "IMPLEMENTED",
            "COMPLETED"
    );

    private static final Set<String> VALID_PRIORITIES = Set.of(
            "LOW",
            "MEDIUM",
            "HIGH"
    );

    private final TicketManagementDAO ticketManagementDAO;
    private final ProjectDAO projectDAO;
    private final ProjectMemberDAO projectMemberDAO;

    public TicketManagementService() {
        ticketManagementDAO = new TicketManagementDAO();
        projectDAO = new ProjectDAO();
        projectMemberDAO = new ProjectMemberDAO();
    }

    public void addTicket(TicketManagement ticket) {
        validateTicket(ticket);

        Project project = projectDAO.getProjectById(ticket.getProjectId());

        if (project == null) {
            throw new ResourceNotFoundException(
                    "Project does not exist. ID: " + ticket.getProjectId());
        }

        validateProjectMember(
                ticket.getProjectId(),
                ticket.getAssignedTo()
        );

        ticketManagementDAO.addTicket(ticket);

        LOGGER.info("Ticket added through service. ID: "
                + ticket.getId());
    }

    public TicketManagement getTicketById(int id) {
        if (id <= 0) {
            throw new ValidationException("Invalid ticket ID.");
        }

        TicketManagement ticket =
                ticketManagementDAO.getTicketById(id);

        if (ticket == null) {
            throw new ResourceNotFoundException(
                    "Ticket not found with ID: " + id);
        }

        return ticket;
    }

    public List<TicketManagement> getAllTickets() {
        return ticketManagementDAO.getAllTickets();
    }

    public List<TicketManagement> searchTicketsByStatus(String status) {
        validateStatus(status);
        return ticketManagementDAO.searchTicketsByStatus(status);
    }

    public List<TicketManagement> searchTicketsByPriority(String priority) {
        validatePriority(priority);
        return ticketManagementDAO.searchTicketsByPriority(priority);
    }

    public List<TicketManagement> searchTicketsByAssignedTo(int userId) {
        if (userId <= 0) {
            throw new ValidationException("Invalid user ID.");
        }

        return ticketManagementDAO.searchTicketsByAssignedTo(userId);
    }

    public void updateTicket(TicketManagement ticket) {
        if (ticket == null || ticket.getId() <= 0) {
            throw new ValidationException("Invalid ticket.");
        }

        validateTicket(ticket);

        Project project = projectDAO.getProjectById(ticket.getProjectId());

        if (project == null) {
            throw new ResourceNotFoundException(
                    "Project does not exist. ID: " + ticket.getProjectId());
        }

        validateProjectMember(
                ticket.getProjectId(),
                ticket.getAssignedTo()
        );

        TicketManagement existingTicket =
                ticketManagementDAO.getTicketById(ticket.getId());

        if (existingTicket == null) {
            throw new ResourceNotFoundException(
                    "Ticket not found with ID: " + ticket.getId());
        }

        ticketManagementDAO.updateTicket(ticket);

        LOGGER.info("Ticket updated through service. ID: "
                + ticket.getId());
    }

    public void deleteTicket(int id) {
        if (id <= 0) {
            throw new ValidationException("Invalid ticket ID.");
        }

        TicketManagement existingTicket =
                ticketManagementDAO.getTicketById(id);

        if (existingTicket == null) {
            throw new ResourceNotFoundException(
                    "Ticket not found with ID: " + id);
        }

        ticketManagementDAO.deleteTicket(id);

        LOGGER.info("Ticket deleted through service. ID: " + id);
    }

    private void validateTicket(TicketManagement ticket) {
        if (ticket == null) {
            throw new ValidationException("Ticket cannot be null.");
        }

        if (ticket.getProjectId() <= 0) {
            throw new ValidationException("Invalid project ID.");
        }

        if (ticket.getTitle() == null ||
                ticket.getTitle().isBlank()) {
            throw new ValidationException("Ticket title is required.");
        }

        if (ticket.getDescription() == null ||
                ticket.getDescription().isBlank()) {
            throw new ValidationException(
                    "Ticket description is required.");
        }

        if (ticket.getDeadline() == null) {
            throw new ValidationException(
                    "Ticket deadline is required.");
        }

        if (ticket.getAssignedTo() <= 0) {
            throw new ValidationException(
                    "Invalid assigned user ID.");
        }

        validateStatus(ticket.getStatus());
        validatePriority(ticket.getPriority());
    }

    private void validateStatus(String status) {
        if (status == null ||
                !VALID_STATUSES.contains(status)) {
            throw new ValidationException(
                    "Invalid ticket status: " + status);
        }
    }

    private void validatePriority(String priority) {
        if (priority == null ||
                !VALID_PRIORITIES.contains(priority)) {
            throw new ValidationException(
                    "Invalid ticket priority: " + priority);
        }
    }

    private void validateProjectMember(int projectId, int userId) {
        List<ProjectMember> members =
                projectMemberDAO.getMembersByProjectId(projectId);

        boolean memberExists = members.stream()
                .anyMatch(member -> member.getUserId() == userId);

        if (!memberExists) {
            throw new ValidationException(
                    "Assigned user is not a member of the project.");
        }
    }
}