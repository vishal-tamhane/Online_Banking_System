package com.bankingsystem.services;

import com.bankingsystem.dao.CustomerDAO;
import com.bankingsystem.models.Customer;
import com.bankingsystem.utils.HashUtil;

public class RegistrationService {

    public static Customer createCustomer(String name, String phone, String aadhar,
                                          String email, String pin, double deposit) {

        if (deposit < 500) {
            System.out.println("❌ Minimum initial deposit is ₹500!");
            return null;
        }

        // Simple hashing for now (upgrade later)
        String pinHash = HashUtil.hashPin(pin);

        return CustomerDAO.insertCustomer(
                name, phone, aadhar, email, pinHash, deposit
        );
    }
}
