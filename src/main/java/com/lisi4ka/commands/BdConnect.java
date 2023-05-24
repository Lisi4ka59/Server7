package com.lisi4ka.commands;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class BdConnect {
    //static String url = "jdbc:postgresql://helios.cs.ifmo.ru/studs?user=s368570&password=Mishaqwe0059";
    static String url = "jdbc:postgresql://localhost/postgres?user=postgres&password=Mishaqwe0059";
    public static Connection conn;

    static {
        try {
            conn = DriverManager.getConnection(url);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
