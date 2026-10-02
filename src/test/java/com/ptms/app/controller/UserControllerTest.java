package com.ptms.app.controller;

import com.ptms.app.model.User;
import com.ptms.app.service.UserService;
import org.junit.jupiter.api.Test;
import org.mockito.MockedConstruction;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserControllerTest {

    @Test
    void testGetUserById() {
        try (MockedConstruction<UserService> mocked = mockConstruction(UserService.class)) {
            UserController controller = new UserController();
            User expected = mock(User.class);

            when(mocked.constructed().get(0).getUserById(1)).thenReturn(expected);

            User actual = controller.getUserById(1);

            assertSame(expected, actual);
            verify(mocked.constructed().get(0)).getUserById(1);
        }
    }

    @Test
    void testGetUserByEmail() {
        try (MockedConstruction<UserService> mocked = mockConstruction(UserService.class)) {
            UserController controller = new UserController();
            User expected = mock(User.class);

            when(mocked.constructed().get(0).getUserByEmail("test@gmail.com")).thenReturn(expected);

            User actual = controller.getUserByEmail("test@gmail.com");

            assertSame(expected, actual);
            verify(mocked.constructed().get(0)).getUserByEmail("test@gmail.com");
        }
    }

    @Test
    void testGetAllUsers() {
        try (MockedConstruction<UserService> mocked = mockConstruction(UserService.class)) {
            UserController controller = new UserController();
            List<User> expected = List.of(mock(User.class));

            when(mocked.constructed().get(0).getAllUsers()).thenReturn(expected);

            List<User> actual = controller.getAllUsers();

            assertEquals(expected, actual);
            verify(mocked.constructed().get(0)).getAllUsers();
        }
    }

    @Test
    void testSearchUsers() {
        try (MockedConstruction<UserService> mocked = mockConstruction(UserService.class)) {
            UserController controller = new UserController();
            List<User> expected = List.of(mock(User.class));

            when(mocked.constructed().get(0).searchUsers("Rahul")).thenReturn(expected);

            List<User> actual = controller.searchUsers("Rahul");

            assertEquals(expected, actual);
            verify(mocked.constructed().get(0)).searchUsers("Rahul");
        }
    }

    @Test
    void testAddUser() {
        try (MockedConstruction<UserService> mocked = mockConstruction(UserService.class)) {
            UserController controller = new UserController();
            User user = mock(User.class);

            controller.addUser(user);

            verify(mocked.constructed().get(0)).addUser(user);
        }
    }

    @Test
    void testUpdateUser() {
        try (MockedConstruction<UserService> mocked = mockConstruction(UserService.class)) {
            UserController controller = new UserController();
            User user = mock(User.class);

            controller.updateUser(user);

            verify(mocked.constructed().get(0)).updateUser(user);
        }
    }

    @Test
    void testUpdateUserRole() {
        try (MockedConstruction<UserService> mocked = mockConstruction(UserService.class)) {
            UserController controller = new UserController();

            controller.updateUserRole(1, 3);

            verify(mocked.constructed().get(0)).updateUserRole(1, 3);
        }
    }

    @Test
    void testDeleteUser() {
        try (MockedConstruction<UserService> mocked = mockConstruction(UserService.class)) {
            UserController controller = new UserController();

            controller.deleteUser(1);

            verify(mocked.constructed().get(0)).deleteUser(1);
        }
    }
}