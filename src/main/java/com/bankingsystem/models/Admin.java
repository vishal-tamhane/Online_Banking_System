package com.bankingsystem.models;

public class Admin {

    private String adminId;
    private String pinHash;
    private String role;

    public Admin(String adminId, String pinHash, String role) {
        this.adminId = adminId;
        this.pinHash = pinHash;
        this.role = role;
    }

    public String getAdminId() { return adminId; }
    public String getPinHash() { return pinHash; }
    public String getRole() { return role; }

    @Override
    public String toString() {
        return "\nAdmin ID: " + adminId +
                "\nRole: " + role + "\n";
    }
}
