package com.ptms.app.controller;

import com.ptms.app.model.User;
import com.ptms.app.service.UserService;

import java.util.List;
import java.util.logging.Logger;

public class UserController {

    private static final Logger LOGGER =
            Logger.getLogger(UserController.class.getName());

    private final UserService userService;

    public UserController() {
        userService = new UserService();
    }

    public void addUser(User user) {
        userService.addUser(user);
        LOGGER.info("User add operation completed.");
    }

    public User getUserById(int id) {
        return userService.getUserById(id);
    }

    public User getUserByEmail(String email) {
        return userService.getUserByEmail(email);
    }

    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    public List<User> searchUsers(String name) {
        return userService.searchUsers(name);
    }

    public void updateUser(User user) {
        userService.updateUser(user);
        LOGGER.info("User update operation completed.");
    }

    public void updateUserRole(int userId, int roleId) {
        userService.updateUserRole(userId, roleId);
        LOGGER.info("User role update operation completed.");
    }

    public void deleteUser(int id) {
        userService.deleteUser(id);
        LOGGER.info("User delete operation completed.");
    }
}