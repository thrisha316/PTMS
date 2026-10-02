package com.ptms.app.dao;

import com.ptms.app.model.Role;
import org.junit.jupiter.api.Test;

class RoleDAOTest {

    @Test
    void testAddRole() throws Exception {

        Role role = new Role();
        role.setRoleName("TEST_ROLE_" + System.currentTimeMillis());

        RoleDAO roleDAO = new RoleDAO();

        roleDAO.addRole(role);
    }
}