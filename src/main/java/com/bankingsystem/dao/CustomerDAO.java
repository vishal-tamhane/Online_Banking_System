package com.bankingsystem.dao;

import com.bankingsystem.database.DBConnections;
import com.bankingsystem.models.Customer;
import com.bankingsystem.utils.HashUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class CustomerDAO {
    public static Customer insertCustomer(String name, String phone, String aadhar,
                                          String email, String pinHash, double balance) {

        String query = """
            INSERT INTO customers (name, phone, aadhar, email, pin_hash, balance)
            VALUES (?, ?, ?, ?, ?, ?)
            RETURNING *;
            """;

        try {
            Connection conn = DBConnections.getConnection();
            PreparedStatement stmt = conn.prepareStatement(query);

            stmt.setString(1, name);
            stmt.setString(2, phone);
            stmt.setString(3, aadhar);
            stmt.setString(4, email);
            stmt.setString(5, pinHash);

            stmt.setDouble(6, balance);

            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return extractCustomer(rs); // already written in your DAO
            }

        } catch (Exception e) {
            System.out.println("❌ Error creating customer (duplicates?)");
            e.printStackTrace();
        }

        return null;
    }


    // Fetch customer by ID
    public static Customer getCustomerById(int id) {
        String query = "SELECT * FROM customers WHERE customer_id = ?";

        try {
            Connection conn = DBConnections.getConnection();
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setInt(1, id);

            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return extractCustomer(rs);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    // Fetch customer by phone + aadhar (PIN verification done in service layer)
    public static Customer loginCustomer(String phone, String aadhar) {
        String query = "SELECT * FROM customers WHERE phone = ? AND aadhar = ?";

        try {
            Connection conn = DBConnections.getConnection();
            PreparedStatement stmt = conn.prepareStatement(query);

            stmt.setString(1, phone);
            stmt.setString(2, aadhar);

            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return extractCustomer(rs);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null; // login failed
    }

    // Convert DB row → Customer object
    private static Customer extractCustomer(ResultSet rs) throws Exception {
        return new Customer(
                rs.getInt("customer_id"),
                rs.getString("name"),
                rs.getString("phone"),
                rs.getString("aadhar"),
                rs.getString("email"),
                rs.getString("pin_hash"),
                rs.getDouble("balance"),
                rs.getDouble("monthly_salary"),
                rs.getInt("credit_score"),
                rs.getString("account_type"),
                rs.getString("status")
        );
    }
    public static void updateBalance(int customerId, double newBalance) {
        String query = "UPDATE customers SET balance = ? WHERE customer_id = ?";

        try {
            Connection conn = DBConnections.getConnection();
            PreparedStatement stmt = conn.prepareStatement(query);

            stmt.setDouble(1, newBalance);
            stmt.setInt(2, customerId);

            stmt.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static Customer getCustomerByPhone(String phone) {
        String query = "SELECT * FROM customers WHERE phone = ?";

        try {
            Connection conn = DBConnections.getConnection();
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, phone);

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return extractCustomer(rs);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    public static double getBalance(int customerId) {
        String query = "SELECT balance FROM customers WHERE customer_id = ?";

        try {
            Connection conn = DBConnections.getConnection();
            PreparedStatement stmt = conn.prepareStatement(query);

            stmt.setInt(1, customerId);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return rs.getDouble("balance");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return 0.0;
    }



}
