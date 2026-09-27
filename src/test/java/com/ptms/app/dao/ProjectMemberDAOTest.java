package com.ptms.app.dao;

import com.ptms.app.model.ProjectMember;
import org.junit.jupiter.api.Test;

class ProjectMemberDAOTest {

    @Test
    void testAddProjectMember() throws Exception {

        ProjectMember projectMember = new ProjectMember(
                6,
                6
        );

        ProjectMemberDAO projectMemberDAO = new ProjectMemberDAO();

        projectMemberDAO.addProjectMember(projectMember);
    }
}