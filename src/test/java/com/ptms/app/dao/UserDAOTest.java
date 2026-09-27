package com.ptms.app.dao;

import com.ptms.app.model.User;
import org.junit.jupiter.api.Test;

class UserDAOTest {

    @Test
    void testAddUser() throws Exception {

        User user = new User(
                "Test User",
                "testuser@gmail.com",
                "test123",
                4
        );

        UserDAO userDAO = new UserDAO();

        userDAO.addUser(user);
    }
}