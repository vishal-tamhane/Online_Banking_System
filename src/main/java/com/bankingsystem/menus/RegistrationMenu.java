package com.bankingsystem.menus;

import com.bankingsystem.models.Customer;
import com.bankingsystem.services.RegistrationService;

import java.util.Scanner;

public class RegistrationMenu {

    private static final Scanner sc = new Scanner(System.in);

    public static void createNewCustomer() {

        System.out.println("\n===== CREATE NEW CUSTOMER ACCOUNT =====");

        System.out.print("Full Name: ");
        String name = sc.nextLine();

        System.out.print("Phone (10 digits): ");
        String phone = sc.nextLine();

        System.out.print("Aadhar (12 digits): ");
        String aadhar = sc.nextLine();

        System.out.print("Email: ");
        String email = sc.nextLine();

        System.out.print("Set 4-digit PIN: ");
        String pin = sc.nextLine();

        System.out.print("Initial Deposit (>= 500): ");
        double deposit = sc.nextDouble();
        sc.nextLine();

        Customer customer = RegistrationService.createCustomer(
                name, phone, aadhar, email, pin, deposit
        );

        if (customer != null) {
            System.out.println("\n🎉 Customer Account Created Successfully!");
            System.out.println(customer);
        } else {
            System.out.println("\n❌ Registration Failed!");
        }
    }
}
