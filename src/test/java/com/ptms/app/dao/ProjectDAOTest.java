package com.ptms.app.dao;

import com.ptms.app.model.Project;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;

class ProjectDAOTest {

    @Test
    void testAddProject() throws Exception {

        Project project = new Project(
                "Test Project",
                "Testing project for PTMS",
                2,
                3,
                1,
                "IT",
                new BigDecimal("50000.00"),
                5,
                LocalDate.of(2026, 10, 1),
                LocalDate.of(2026, 12, 31),
                "HIGH",
                "PLANNED"
        );

        ProjectDAO projectDAO = new ProjectDAO();

        projectDAO.addProject(project);
    }
}