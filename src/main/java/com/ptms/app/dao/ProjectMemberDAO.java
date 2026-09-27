package com.ptms.app.dao;

import com.ptms.app.model.ProjectMember;
import com.ptms.app.util.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ProjectMemberDAO {

    private static final Logger LOGGER =
            Logger.getLogger(ProjectMemberDAO.class.getName());

    public void addProjectMember(ProjectMember projectMember) {

        String sql = "INSERT INTO project_members (project_id, user_id) " +
                "VALUES (?, ?)";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, projectMember.getProjectId());
            statement.setInt(2, projectMember.getUserId());

            int rows = statement.executeUpdate();

            if (rows > 0) {
                LOGGER.info("Project member added successfully.");
            }

        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE,
                    "Error while adding project member", e);
        }
    }

    public List<ProjectMember> getMembersByProjectId(int projectId) {

        String sql = "SELECT project_id, user_id " +
                "FROM project_members WHERE project_id = ? " +
                "ORDER BY user_id";

        List<ProjectMember> members = new ArrayList<>();

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, projectId);

            try (ResultSet resultSet = statement.executeQuery()) {

                while (resultSet.next()) {
                    ProjectMember member = new ProjectMember(
                            resultSet.getInt("project_id"),
                            resultSet.getInt("user_id")
                    );
                    members.add(member);
                }
            }

        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE,
                    "Error while fetching project members", e);
        }

        return members;
    }

    public List<ProjectMember> getAllProjectMembers() {

        String sql = "SELECT project_id, user_id " +
                "FROM project_members ORDER BY project_id, user_id";

        List<ProjectMember> members = new ArrayList<>();

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                ProjectMember member = new ProjectMember(
                        resultSet.getInt("project_id"),
                        resultSet.getInt("user_id")
                );
                members.add(member);
            }

        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE,
                    "Error while fetching project members", e);
        }

        return members;
    }

    public void deleteProjectMember(int projectId, int userId) {

        String sql = "DELETE FROM project_members " +
                "WHERE project_id = ? AND user_id = ?";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, projectId);
            statement.setInt(2, userId);

            int rows = statement.executeUpdate();

            if (rows > 0) {
                LOGGER.info("Project member removed successfully.");
            } else {
                LOGGER.warning("Project member not found.");
            }

        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE,
                    "Error while deleting project member", e);
        }
    }
}