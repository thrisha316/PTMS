package com.ptms.app.service;

import com.ptms.app.dao.UserDAO;
import com.ptms.app.model.User;
import org.junit.jupiter.api.Test;
import org.mockito.MockedConstruction;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserServiceTest {

    @Test
    void testGetUserById() {
        try (MockedConstruction<UserDAO> mocked = mockConstruction(UserDAO.class)) {
            UserService service = new UserService();
            User expected = mock(User.class);

            when(mocked.constructed().get(0).getUserById(1)).thenReturn(expected);

            User actual = service.getUserById(1);

            assertSame(expected, actual);
            verify(mocked.constructed().get(0)).getUserById(1);
        }
    }

    @Test
    void testGetUserByEmail() {
        try (MockedConstruction<UserDAO> mocked = mockConstruction(UserDAO.class)) {
            UserService service = new UserService();
            User expected = mock(User.class);

            when(mocked.constructed().get(0).getUserByEmail("test@gmail.com")).thenReturn(expected);

            User actual = service.getUserByEmail("test@gmail.com");

            assertSame(expected, actual);
            verify(mocked.constructed().get(0)).getUserByEmail("test@gmail.com");
        }
    }

    @Test
    void testGetAllUsers() {
        try (MockedConstruction<UserDAO> mocked = mockConstruction(UserDAO.class)) {
            UserService service = new UserService();
            List<User> expected = List.of(mock(User.class));

            when(mocked.constructed().get(0).getAllUsers()).thenReturn(expected);

            List<User> actual = service.getAllUsers();

            assertEquals(expected, actual);
            verify(mocked.constructed().get(0)).getAllUsers();
        }
    }

    @Test
    void testSearchUsers() {
        try (MockedConstruction<UserDAO> mocked = mockConstruction(UserDAO.class)) {
            UserService service = new UserService();
            List<User> expected = List.of(mock(User.class));

            when(mocked.constructed().get(0).searchUsers("Rahul")).thenReturn(expected);

            List<User> actual = service.searchUsers("Rahul");

            assertEquals(expected, actual);
            verify(mocked.constructed().get(0)).searchUsers("Rahul");
        }
    }

    @Test
    void testAddUser() {
        try (MockedConstruction<UserDAO> mocked = mockConstruction(UserDAO.class)) {
            UserService service = new UserService();
            User user = mock(User.class);

            service.addUser(user);

            verify(mocked.constructed().get(0)).addUser(user);
        }
    }

    @Test
    void testUpdateUser() {
        try (MockedConstruction<UserDAO> mocked = mockConstruction(UserDAO.class)) {
            UserService service = new UserService();
            User user = mock(User.class);

            when(user.getId()).thenReturn(1);

            service.updateUser(user);

            verify(mocked.constructed().get(0)).updateUser(user);
        }
    }

    @Test
    void testUpdateUserRole() {
        try (MockedConstruction<UserDAO> mocked = mockConstruction(UserDAO.class)) {
            UserService service = new UserService();

            service.updateUserRole(1, 3);

            verify(mocked.constructed().get(0)).updateUserRole(1, 3);
        }
    }

    @Test
    void testDeleteUser() {
        try (MockedConstruction<UserDAO> mocked = mockConstruction(UserDAO.class)) {
            UserService service = new UserService();

            service.deleteUser(1);

            verify(mocked.constructed().get(0)).deleteUser(1);
        }
    }
}