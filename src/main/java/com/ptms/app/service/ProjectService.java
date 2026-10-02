package com.ptms.app.service;

import com.ptms.app.dao.ProjectDAO;
import com.ptms.app.exception.ResourceNotFoundException;
import com.ptms.app.exception.ValidationException;
import com.ptms.app.model.Project;

import java.time.LocalDate;
import java.util.List;
import java.util.logging.Logger;

public class ProjectService {

    private static final Logger LOGGER =
            Logger.getLogger(ProjectService.class.getName());

    private final ProjectDAO projectDAO;

    public ProjectService() {
        projectDAO = new ProjectDAO();
    }

    public void addProject(Project project) {

        validateProject(project);

        if (!project.getDeadline().isAfter(LocalDate.now())) {
            throw new ValidationException(
                    "Project deadline must be a future date.");
        }

        projectDAO.addProject(project);

        LOGGER.info("Project added through service. ID: "
                + project.getId());
    }

    public Project getProjectById(int id) {

        if (id <= 0) {
            throw new ValidationException("Invalid project ID.");
        }

        Project project = projectDAO.getProjectById(id);

        if (project == null) {
            throw new ResourceNotFoundException(
                    "Project not found with ID: " + id);
        }

        return project;
    }

    public List<Project> getAllProjects() {
        return projectDAO.getAllProjects();
    }

    public List<Project> searchProjects(String name) {

        if (name == null || name.isBlank()) {
            throw new ValidationException(
                    "Project name is required.");
        }

        return projectDAO.searchProjects(name);
    }

    public void updateProject(Project project) {

        if (project == null || project.getId() <= 0) {
            throw new ValidationException("Invalid project.");
        }

        validateProject(project);

        projectDAO.updateProject(project);

        LOGGER.info("Project updated through service. ID: "
                + project.getId());
    }

    public void deleteProject(int id) {

        if (id <= 0) {
            throw new ValidationException("Invalid project ID.");
        }

        projectDAO.deleteProject(id);

        LOGGER.info("Project deleted through service. ID: " + id);
    }

    private void validateProject(Project project) {

        if (project == null) {
            throw new ValidationException(
                    "Project cannot be null.");
        }

        if (project.getName() == null ||
                project.getName().isBlank()) {
            throw new ValidationException(
                    "Project name is required.");
        }

        if (project.getRequirements() == null ||
                project.getRequirements().isBlank()) {
            throw new ValidationException(
                    "Project requirements are required.");
        }

        if (project.getManagerId() <= 0) {
            throw new ValidationException(
                    "Invalid manager ID.");
        }

        if (project.getTeamLeadId() <= 0) {
            throw new ValidationException(
                    "Invalid team lead ID.");
        }

        if (project.getClientId() <= 0) {
            throw new ValidationException(
                    "Invalid client ID.");
        }

        if (project.getDomain() == null ||
                project.getDomain().isBlank()) {
            throw new ValidationException(
                    "Project domain is required.");
        }

        if (project.getCost() == null ||
                project.getCost().signum() < 0) {
            throw new ValidationException(
                    "Invalid project cost.");
        }

        if (project.getTeamSize() <= 0) {
            throw new ValidationException(
                    "Team size must be greater than zero.");
        }

        if (project.getStartDate() == null) {
            throw new ValidationException(
                    "Project start date is required.");
        }

        if (project.getDeadline() == null) {
            throw new ValidationException(
                    "Project deadline is required.");
        }

        if (project.getPriority() == null ||
                project.getPriority().isBlank()) {
            throw new ValidationException(
                    "Project priority is required.");
        }

        if (project.getStatus() == null ||
                project.getStatus().isBlank()) {
            throw new ValidationException(
                    "Project status is required.");
        }
    }
}