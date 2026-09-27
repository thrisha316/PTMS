package com.ptms.app.service;

import com.ptms.app.dao.UserDAO;
import com.ptms.app.model.User;

import java.util.List;
import java.util.logging.Logger;

public class UserService {

    private static final Logger LOGGER =
            Logger.getLogger(UserService.class.getName());

    private final UserDAO userDAO;

    public UserService() {
        userDAO = new UserDAO();
    }

    public void addUser(User user) {
        userDAO.addUser(user);
        LOGGER.info("User added through service.");
    }

    public User getUserById(int id) {
        return userDAO.getUserById(id);
    }

    public User getUserByEmail(String email) {
        return userDAO.getUserByEmail(email);
    }

    public List<User> getAllUsers() {
        return userDAO.getAllUsers();
    }

    public List<User> searchUsers(String name) {
        return userDAO.searchUsers(name);
    }

    public void updateUser(User user) {
        userDAO.updateUser(user);
        LOGGER.info("User updated through service. ID: "
                + user.getId());
    }

    public void updateUserRole(int userId, int roleId) {
        userDAO.updateUserRole(userId, roleId);
        LOGGER.info("User role updated through service. User ID: "
                + userId);
    }

    public void deleteUser(int id) {
        userDAO.deleteUser(id);
        LOGGER.info("User deleted through service. ID: " + id);
    }
}