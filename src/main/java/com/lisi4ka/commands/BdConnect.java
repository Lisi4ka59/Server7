package com.lisi4ka.commands;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class BdConnect {
    //String url = "jdbc:postgresql://helios.cs.ifmo.ru/studs?user=s368570&password=wbhX&1731&ssl=true";
    static String url = "jdbc:postgresql://localhost/postgres?user=postgres&password=Misha";
    public static Connection conn;

    static {
        try {
            conn = DriverManager.getConnection(url);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
