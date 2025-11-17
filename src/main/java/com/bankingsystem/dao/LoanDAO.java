package com.bankingsystem.dao;

import com.bankingsystem.database.DBConnections;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class LoanDAO {
    public static ResultSet getPendingLoansForAdmin(String role) {

        String query = "";

        switch (role) {
            case "A1":
                query = "SELECT * FROM loans WHERE amount > 100000 AND status = 'PENDING'";
                break;
            case "A2":
                query = "SELECT * FROM loans WHERE amount >= 20000 AND amount <= 100000 AND status = 'PENDING'";
                break;
            case "A3":
                query = "SELECT * FROM loans WHERE amount < 20000 AND status = 'PENDING'";
                break;
        }

        try {
            Connection conn = DBConnections.getConnection();
            return conn.createStatement().executeQuery(query);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    public static void approveLoan(int loanId, String adminId) {

        String query = """
            UPDATE loans 
            SET status = 'APPROVED', approved_by = ?
            WHERE loan_id = ?
        """;

        try {
            Connection conn = DBConnections.getConnection();
            PreparedStatement stmt = conn.prepareStatement(query);

            stmt.setString(1, adminId);
            stmt.setInt(2, loanId);

            stmt.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static void rejectLoan(int loanId, String adminId) {

        String query = """
            UPDATE loans 
            SET status = 'REJECTED', approved_by = ?
            WHERE loan_id = ?
        """;

        try {
            Connection conn = DBConnections.getConnection();
            PreparedStatement stmt = conn.prepareStatement(query);

            stmt.setString(1, adminId);
            stmt.setInt(2, loanId);

            stmt.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static ResultSet getLoanById(int loanId) {
        try {
            Connection conn = DBConnections.getConnection();
            PreparedStatement stmt = conn.prepareStatement("SELECT * FROM loans WHERE loan_id = ?");
            stmt.setInt(1, loanId);
            return stmt.executeQuery();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    public static void createLoan(int customerId, double amount, double monthlyEmi, int tenureMonths, String approverRole) {

        String query = """
            INSERT INTO loans (customer_id, amount, monthly_emi, tenure_months, remaining_amount, status)
            VALUES (?, ?, ?, ?, ?, 'PENDING')
        """;

        try {
            Connection conn = DBConnections.getConnection();
            PreparedStatement stmt = conn.prepareStatement(query);

            stmt.setInt(1, customerId);
            stmt.setDouble(2, amount);
            stmt.setDouble(3, monthlyEmi);
            stmt.setInt(4, tenureMonths);
            stmt.setDouble(5, amount); // remaining amount = total loan amount

            stmt.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static ResultSet getLoansByCustomer(int customerId) {

        String query = "SELECT * FROM loans WHERE customer_id = ? ORDER BY timestamp DESC";

        try {
            Connection conn = DBConnections.getConnection();
            PreparedStatement stmt = conn.prepareStatement(query);

            stmt.setInt(1, customerId);

            return stmt.executeQuery();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }


}
