package com.bankingsystem.main;

import com.bankingsystem.dao.CustomerDAO;
import com.bankingsystem.database.DBConnections;
import com.bankingsystem.menus.AdminMenu;
import com.bankingsystem.menus.CustomerMenu;
import com.bankingsystem.menus.RegistrationMenu;
import com.bankingsystem.models.Admin;
import com.bankingsystem.models.Customer;
import com.bankingsystem.utils.HashUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.*;

public class OnlineBankingMain {
    public static void main(String[] args) {
        System.out.println(HashUtil.hashPin("1234"));

        Scanner sc = new Scanner(System.in);


        while (true) {
            System.out.println("\n===== BANKING SYSTEM =====");
            System.out.println("1. Customer Login");
            System.out.println("2. Create New Customer Account");
            System.out.println("3. Admin Login");

            System.out.println("4. Exit");

            System.out.print("Choose: ");
            int choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {

                case 1:
                    Customer loggedIn = CustomerMenu.login();
                    if (loggedIn != null) {
                        CustomerMenu.showCustomerMenu(loggedIn);
                    }
                    break;

                case 2:
                    RegistrationMenu.createNewCustomer();
                    break;

                case 3:
                    Admin admin = AdminMenu.login();
                    if (admin != null) {
                        AdminMenu.showAdminMenu(admin);
                    }
                    break;

                case 4:
                    System.out.println("Exiting...");
                    return;

                default:
                    System.out.println("Invalid option!");
            }
        }
    }
}
