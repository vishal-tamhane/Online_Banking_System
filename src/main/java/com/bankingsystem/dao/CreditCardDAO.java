package com.bankingsystem.dao;

import com.bankingsystem.database.DBConnections;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class CreditCardDAO {

    public static void createCreditCardApplication(int customerId, double creditLimit) {

        String query = """
                INSERT INTO credit_cards (customer_id, credit_limit, status)
                VALUES (?, ?, 'PENDING')
                """;

        try {
            Connection conn = DBConnections.getConnection();
            PreparedStatement stmt = conn.prepareStatement(query);

            stmt.setInt(1, customerId);
            stmt.setDouble(2, creditLimit);

            stmt.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static ResultSet getPendingCards() {
        String query = "SELECT * FROM credit_cards WHERE status = 'PENDING'";

        try {
            Connection conn = DBConnections.getConnection();
            return conn.createStatement().executeQuery(query);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    public static void approveCard(int cardId, String cardNumber, String cvv, java.sql.Date expiryDate, String adminId) {

        String query = """
            UPDATE credit_cards 
            SET card_number = ?, cvv = ?, expiry_date = ?, 
                status = 'ACTIVE', approved_by = ?
            WHERE card_id = ?
            """;

        try {
            Connection conn = DBConnections.getConnection();
            PreparedStatement stmt = conn.prepareStatement(query);

            stmt.setString(1, cardNumber);
            stmt.setString(2, cvv);
            stmt.setDate(3, expiryDate);
            stmt.setString(4, adminId);
            stmt.setInt(5, cardId);

            stmt.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void rejectCard(int cardId, String adminId) {

        String query = """
            UPDATE credit_cards
            SET status = 'REJECTED', approved_by = ?
            WHERE card_id = ?
            """;

        try {
            Connection conn = DBConnections.getConnection();
            PreparedStatement stmt = conn.prepareStatement(query);

            stmt.setString(1, adminId);
            stmt.setInt(2, cardId);

            stmt.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }



}
