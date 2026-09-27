package com.ptms.app.controller;

import com.ptms.app.model.Project;
import com.ptms.app.service.ProjectService;

import java.util.List;
import java.util.logging.Logger;

public class ProjectController {

    private static final Logger LOGGER =
            Logger.getLogger(ProjectController.class.getName());

    private final ProjectService projectService;

    public ProjectController() {
        projectService = new ProjectService();
    }

    public void addProject(Project project) {
        projectService.addProject(project);
        LOGGER.info("Project add operation completed.");
    }

    public Project getProjectById(int id) {
        return projectService.getProjectById(id);
    }

    public List<Project> getAllProjects() {
        return projectService.getAllProjects();
    }

    public List<Project> searchProjects(String name) {
        return projectService.searchProjects(name);
    }

    public void updateProject(Project project) {
        projectService.updateProject(project);
        LOGGER.info("Project update operation completed.");
    }

    public void deleteProject(int id) {
        projectService.deleteProject(id);
        LOGGER.info("Project delete operation completed.");
    }
}