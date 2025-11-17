package com.bankingsystem.dao;

import com.bankingsystem.database.DBConnections;
import java.sql.ResultSet;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class TransactionDAO {

    public static void logTransaction(int customerId, String type,
                                      double amount, String mode, String notes) {

        String query = """
                INSERT INTO transactions (customer_id, type, amount, mode, notes)
                VALUES (?, ?, ?, ?, ?);
                """;

        try {
            Connection conn = DBConnections.getConnection();
            PreparedStatement stmt = conn.prepareStatement(query);

            stmt.setInt(1, customerId);
            stmt.setString(2, type);
            stmt.setDouble(3, amount);
            stmt.setString(4, mode);
            stmt.setString(5, notes);

            stmt.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static void showTransactionHistory(int customerId) {

        String query = "SELECT transaction_id, type, amount, mode, notes, timestamp " +
                "FROM transactions WHERE customer_id = ? ORDER BY timestamp DESC";

        try {
            Connection conn = DBConnections.getConnection();
            PreparedStatement stmt = conn.prepareStatement(query);

            stmt.setInt(1, customerId);

            ResultSet rs = stmt.executeQuery();

            System.out.println("\n===== TRANSACTION HISTORY =====");

            boolean found = false;

            while (rs.next()) {
                found = true;
                System.out.println("\n---------------------------------");
                System.out.println("Transaction ID: " + rs.getLong("transaction_id"));
                System.out.println("Type: " + rs.getString("type"));
                System.out.println("Amount: ₹" + rs.getDouble("amount"));
                System.out.println("Mode: " + rs.getString("mode"));
                System.out.println("Notes: " + rs.getString("notes"));
                System.out.println("Date: " + rs.getTimestamp("timestamp")); // FIXED
            }

            if (!found) {
                System.out.println("No transactions found.");
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}