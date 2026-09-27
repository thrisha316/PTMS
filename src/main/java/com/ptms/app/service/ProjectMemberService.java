package com.ptms.app.service;

import com.ptms.app.dao.ProjectMemberDAO;
import com.ptms.app.model.ProjectMember;

import java.util.List;
import java.util.logging.Logger;

public class ProjectMemberService {

    private static final Logger LOGGER =
            Logger.getLogger(ProjectMemberService.class.getName());

    private final ProjectMemberDAO projectMemberDAO;

    public ProjectMemberService() {
        projectMemberDAO = new ProjectMemberDAO();
    }

    public void addProjectMember(ProjectMember projectMember) {
        projectMemberDAO.addProjectMember(projectMember);
        LOGGER.info("Project member added through service.");
    }

    public List<ProjectMember> getMembersByProjectId(int projectId) {
        return projectMemberDAO.getMembersByProjectId(projectId);
    }

    public List<ProjectMember> getAllProjectMembers() {
        return projectMemberDAO.getAllProjectMembers();
    }

    public void deleteProjectMember(int projectId, int userId) {
        projectMemberDAO.deleteProjectMember(projectId, userId);
        LOGGER.info("Project member removed through service.");
    }
}