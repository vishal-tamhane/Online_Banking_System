package com.bankingsystem.dao;

import com.bankingsystem.database.DBConnections;
import com.bankingsystem.models.Admin;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class AdminDAO {

    public static Admin loginAdmin(String adminId, String pinHash) {

        String query = "SELECT * FROM admins WHERE admin_id = ? AND pin_hash = ?";

        try {
            Connection conn = DBConnections.getConnection();
            PreparedStatement stmt = conn.prepareStatement(query);

            stmt.setString(1, adminId);
            stmt.setString(2, pinHash);

            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return new Admin(
                        rs.getString("admin_id"),
                        rs.getString("pin_hash"),
                        rs.getString("role")
                );
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null; // login failed
    }
    
    // Fetch admin by ID only (for BCrypt verification in service layer)
    public static Admin getAdminById(String adminId) {

        String query = "SELECT * FROM admins WHERE admin_id = ?";

        try {
            Connection conn = DBConnections.getConnection();
            PreparedStatement stmt = conn.prepareStatement(query);

            stmt.setString(1, adminId);

            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return new Admin(
                        rs.getString("admin_id"),
                        rs.getString("pin_hash"),
                        rs.getString("role")
                );
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }
}
