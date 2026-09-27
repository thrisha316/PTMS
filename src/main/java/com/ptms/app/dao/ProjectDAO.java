package com.ptms.app.dao;

import com.ptms.app.model.Project;
import com.ptms.app.util.DBConnection;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ProjectDAO {

    private static final Logger LOGGER =
            Logger.getLogger(ProjectDAO.class.getName());

    public void addProject(Project project) {

        String sql = "INSERT INTO projects " +
                "(name, requirements, manager_id, team_lead_id, client_id, " +
                "domain, cost, team_size, start_date, deadline, priority, status) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            statement.setString(1, project.getName());
            statement.setString(2, project.getRequirements());
            statement.setInt(3, project.getManagerId());
            statement.setInt(4, project.getTeamLeadId());
            statement.setInt(5, project.getClientId());
            statement.setString(6, project.getDomain());
            statement.setBigDecimal(7, project.getCost());
            statement.setInt(8, project.getTeamSize());
            statement.setDate(9, Date.valueOf(project.getStartDate()));
            statement.setDate(10, Date.valueOf(project.getDeadline()));
            statement.setString(11, project.getPriority());
            statement.setString(12, project.getStatus());

            int rows = statement.executeUpdate();

            if (rows > 0) {
                try (ResultSet resultSet = statement.getGeneratedKeys()) {
                    if (resultSet.next()) {
                        project.setId(resultSet.getInt(1));
                    }
                }
                LOGGER.info("Project added successfully. ID: " + project.getId());
            } else {
                LOGGER.warning("Project was not added.");
            }

        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error while adding project", e);
        }
    }

    public Project getProjectById(int id) {

        String sql = "SELECT id, name, requirements, manager_id, team_lead_id, " +
                "client_id, domain, cost, team_size, start_date, deadline, " +
                "priority, status FROM projects WHERE id = ?";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, id);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return mapProject(resultSet);
                }
            }

        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE,
                    "Error while fetching project ID: " + id, e);
        }

        return null;
    }

    public List<Project> getAllProjects() {

        String sql = "SELECT id, name, requirements, manager_id, team_lead_id, " +
                "client_id, domain, cost, team_size, start_date, deadline, " +
                "priority, status FROM projects ORDER BY id";

        List<Project> projects = new ArrayList<>();

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                projects.add(mapProject(resultSet));
            }

        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE,
                    "Error while fetching projects", e);
        }

        return projects;
    }

    public List<Project> searchProjects(String name) {

        String sql = "SELECT id, name, requirements, manager_id, team_lead_id, " +
                "client_id, domain, cost, team_size, start_date, deadline, " +
                "priority, status FROM projects " +
                "WHERE name LIKE ? ORDER BY id";

        List<Project> projects = new ArrayList<>();

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, "%" + name + "%");

            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    projects.add(mapProject(resultSet));
                }
            }

        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE,
                    "Error while searching projects", e);
        }

        return projects;
    }

    public void updateProject(Project project) {

        String sql = "UPDATE projects SET " +
                "name = ?, requirements = ?, manager_id = ?, " +
                "team_lead_id = ?, client_id = ?, domain = ?, cost = ?, " +
                "team_size = ?, start_date = ?, deadline = ?, " +
                "priority = ?, status = ? WHERE id = ?";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, project.getName());
            statement.setString(2, project.getRequirements());
            statement.setInt(3, project.getManagerId());
            statement.setInt(4, project.getTeamLeadId());
            statement.setInt(5, project.getClientId());
            statement.setString(6, project.getDomain());
            statement.setBigDecimal(7, project.getCost());
            statement.setInt(8, project.getTeamSize());
            statement.setDate(9, Date.valueOf(project.getStartDate()));
            statement.setDate(10, Date.valueOf(project.getDeadline()));
            statement.setString(11, project.getPriority());
            statement.setString(12, project.getStatus());
            statement.setInt(13, project.getId());

            int rows = statement.executeUpdate();

            if (rows > 0) {
                LOGGER.info("Project updated successfully. ID: "
                        + project.getId());
            } else {
                LOGGER.warning("Project not found. ID: "
                        + project.getId());
            }

        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE,
                    "Error while updating project ID: "
                            + project.getId(), e);
        }
    }

    public void deleteProject(int id) {

        String sql = "DELETE FROM projects WHERE id = ?";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, id);

            int rows = statement.executeUpdate();

            if (rows > 0) {
                LOGGER.info("Project deleted successfully. ID: " + id);
            } else {
                LOGGER.warning("Project not found. ID: " + id);
            }

        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE,
                    "Error while deleting project ID: " + id, e);
        }
    }

    private Project mapProject(ResultSet resultSet)
            throws SQLException {

        Project project = new Project();

        project.setId(resultSet.getInt("id"));
        project.setName(resultSet.getString("name"));
        project.setRequirements(resultSet.getString("requirements"));
        project.setManagerId(resultSet.getInt("manager_id"));
        project.setTeamLeadId(resultSet.getInt("team_lead_id"));
        project.setClientId(resultSet.getInt("client_id"));
        project.setDomain(resultSet.getString("domain"));
        project.setCost(resultSet.getBigDecimal("cost"));
        project.setTeamSize(resultSet.getInt("team_size"));

        Date startDate = resultSet.getDate("start_date");
        if (startDate != null) {
            project.setStartDate(startDate.toLocalDate());
        }

        Date deadline = resultSet.getDate("deadline");
        if (deadline != null) {
            project.setDeadline(deadline.toLocalDate());
        }

        project.setPriority(resultSet.getString("priority"));
        project.setStatus(resultSet.getString("status"));

        return project;
    }
}