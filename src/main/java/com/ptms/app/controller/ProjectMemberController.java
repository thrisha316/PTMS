package com.ptms.app.controller;

import com.ptms.app.model.ProjectMember;
import com.ptms.app.service.ProjectMemberService;

import java.util.List;
import java.util.logging.Logger;

public class ProjectMemberController {

    private static final Logger LOGGER =
            Logger.getLogger(ProjectMemberController.class.getName());

    private final ProjectMemberService projectMemberService;

    public ProjectMemberController() {
        projectMemberService = new ProjectMemberService();
    }

    public void addProjectMember(ProjectMember projectMember) {
        projectMemberService.addProjectMember(projectMember);
        LOGGER.info("Project member add operation completed.");
    }

    public List<ProjectMember> getMembersByProjectId(int projectId) {
        return projectMemberService.getMembersByProjectId(projectId);
    }

    public List<ProjectMember> getAllProjectMembers() {
        return projectMemberService.getAllProjectMembers();
    }

    public void deleteProjectMember(int projectId, int userId) {
        projectMemberService.deleteProjectMember(projectId, userId);
        LOGGER.info("Project member delete operation completed.");
    }
}