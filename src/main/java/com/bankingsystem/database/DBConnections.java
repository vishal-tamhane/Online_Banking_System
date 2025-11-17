package com.bankingsystem.database;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
public class DBConnections {

        private static final String URL = "jdbc:postgresql://localhost:5432/Online_Banking_System";
        private static final String USER = "postgres";
        private static final String PASSWORD = "vishal9699";

        private static Connection connection = null;

        // Method to get a SINGLE shared connection
        public static Connection getConnection() {
            if (connection == null) {
                try {
                    connection = DriverManager.getConnection(URL, USER, PASSWORD);
                    System.out.println("✅ PostgreSQL Connected Successfully!");
                } catch (SQLException e) {
                    System.out.println("❌ PostgreSQL Connection Failed!");
                    e.printStackTrace();
                }
            }
            return connection;
        }

        // Method to close connection when program ends
        public static void closeConnection() {
            try {
                if (connection != null) {
                    connection.close();
                    System.out.println("🔌 Connection Closed.");
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

}
