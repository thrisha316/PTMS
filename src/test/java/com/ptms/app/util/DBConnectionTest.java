package com.ptms.app.util;

import org.junit.jupiter.api.Test;

import java.sql.Connection;

import static org.junit.jupiter.api.Assertions.assertNotNull;

class DBConnectionTest {

    @Test
    void testDatabaseConnection() throws Exception {
        Connection connection = DBConnection.getConnection();

        assertNotNull(connection);

        connection.close();
    }
}