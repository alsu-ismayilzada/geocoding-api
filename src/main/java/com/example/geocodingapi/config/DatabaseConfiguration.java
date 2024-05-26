package com.example.geocodingapi.config;

import org.h2.tools.Server;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import static com.example.geocodingapi.contant.DatabaseConstants.*;


public class DatabaseConfiguration {

    static {
        try {
            Class.forName("org.h2.Driver");
            createDirectoryIfNotExists();
            startWebServer();
            createTableIfNotExists();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    private static void startWebServer()  {
        try {
            Server.createWebServer("-web", "-webAllowOthers", "-webPort", "8082").start();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void createDirectoryIfNotExists() {
        String filePath = JDBC_URL.replace("jdbc:h2:file:", "");
        File file = new File(filePath).getParentFile();
        if (file != null && !file.exists()) {
            file.mkdirs();
        }
    }

    private static void createTableIfNotExists() {
        String sql = "CREATE TABLE IF NOT EXISTS public.locations (" +
                "id IDENTITY PRIMARY KEY, " +
                "lon DOUBLE, " +
                "lat DOUBLE, " +
                "name VARCHAR(255), " +
                "display_name VARCHAR(255)" +
                ")";

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            System.out.println("Executing SQL: " + sql);
            stmt.executeUpdate();
            conn.commit();
            stmt.close();
            conn.close();
            System.out.println("Table created or already exists.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(JDBC_URL, JDBC_USERNAME, JDBC_PASSWORD);
    }

}
