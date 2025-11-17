package com.bankingsystem.services;

import com.bankingsystem.dao.CustomerDAO;
import com.bankingsystem.models.Customer;
import com.bankingsystem.utils.HashUtil;

public class AuthenticationService {

    public static Customer loginCustomer(String phone, String aadhar, String pin) {

        // Step 1: Fetch customer (but no password check yet)
        Customer customer = CustomerDAO.loginCustomer(phone, aadhar);

        if (customer == null) {
            System.out.println("❌ No customer found with given credentials");
            return null;
        }

        // Step 2: Verify PIN using BCrypt
        if (!HashUtil.verifyPin(pin, customer.getPinHash())) {
            System.out.println("❌ Incorrect PIN");
            return null;
        }

        // Step 3: Log successful login
        LoggingService.info(
                String.valueOf(customer.getCustomerId()),
                "LOGIN",
                "Customer logged in successfully"
        );

        return customer;
    }
}
