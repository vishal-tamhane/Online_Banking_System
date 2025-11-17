package com.bankingsystem.services;

import com.bankingsystem.dao.AdminDAO;
import com.bankingsystem.models.Admin;
import com.bankingsystem.utils.HashUtil;

public class AdminService {

    // Fetch admin and verify PIN using BCrypt
    public static Admin login(String adminId, String pin) {

        // Fetch admin by ID first
        Admin admin = AdminDAO.getAdminById(adminId);
        
        if (admin == null) {
            System.out.println("❌ Admin not found");
            return null;
        }
        
        // Verify PIN using BCrypt
        if (!HashUtil.verifyPin(pin, admin.getPinHash())) {
            System.out.println("❌ Incorrect PIN");
            return null;
        }
        
        return admin;
    }
}
