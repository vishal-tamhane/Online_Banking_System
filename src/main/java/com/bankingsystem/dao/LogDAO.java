package com.bankingsystem.dao;

import com.bankingsystem.database.DBConnections;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class LogDAO {

    public static void addLog(String message, String level, String userId, String action) {

        String query = """
            INSERT INTO logs (message, log_level, user_id, action)
            VALUES (?, ?, ?, ?)
            """;

        try {
            Connection conn = DBConnections.getConnection();
            PreparedStatement stmt = conn.prepareStatement(query);

            stmt.setString(1, message);
            stmt.setString(2, level);
            stmt.setString(3, userId);
            stmt.setString(4, action);

            stmt.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static ResultSet getAllLogs() {
        String query = "SELECT * FROM logs ORDER BY timestamp DESC";

        try {
            Connection conn = DBConnections.getConnection();
            return conn.createStatement().executeQuery(query);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }
}

