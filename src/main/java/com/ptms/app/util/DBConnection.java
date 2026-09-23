package com.ptms.app.util;

import java.sql.Connection;
import java.sql.DriverManager;

public class DBConnection {

    private static final String URL =
            "jdbc:mysql://localhost:3306/ptms_db";

    private static final String USER =
            "ptms_user";

    private static final String PASSWORD =
            "ptms123";

    public static Connection getConnection() throws Exception {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}