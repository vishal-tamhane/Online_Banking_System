package com.bankingsystem.menus;

import com.bankingsystem.models.Admin;
import com.bankingsystem.services.AdminService;
import com.bankingsystem.services.CreditCardService;
import com.bankingsystem.services.LoanService;
import com.bankingsystem.dao.LoanDAO;

import java.sql.ResultSet;
import java.util.Scanner;

public class AdminMenu {

    private static final Scanner sc = new Scanner(System.in);

    public static Admin login() {

        System.out.println("\n===== ADMIN LOGIN =====");
        System.out.print("Enter Admin ID: ");
        String adminId = sc.nextLine();

        System.out.print("Enter PIN: ");
        String pin = sc.nextLine();

        Admin admin = AdminService.login(adminId, pin);

        if (admin == null) {
            System.out.println("❌ Invalid admin credentials!");
            return null;
        }

        System.out.println("\n✔ Login Successful!");
        System.out.println("Welcome, Admin " + admin.getAdminId());
        System.out.println("Role: " + admin.getRole());

        return admin;
    }

    // After login → show role-based options
    public static void showAdminMenu(Admin admin) {
        switch (admin.getRole()) {

            case "A1":
                A1Menu(admin);
                break;

            case "A2":
                A2Menu(admin);
                break;

            case "A3":
                A3Menu(admin);
                break;

            case "A4":
                A4Menu(admin);
                break;
        }
    }

    private static void A1Menu(Admin admin) {

        Scanner sc = new Scanner(System.in);

        while (true) {
            System.out.println("\n=== A1 ADMIN MENU ===");
            System.out.println("1. View Pending High Loans (> ₹1,00,000)");
            System.out.println("2. Approve/Reject Loan");
            System.out.println("3. Logout");

            int choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {
                case 1:
                    LoanService.showPendingLoans("A1");
                    break;

                case 2:
                    System.out.print("Enter Loan ID: ");
                    int loanId = sc.nextInt();
                    sc.nextLine();

                    System.out.print("Approve or Reject? (a/r): ");
                    String op = sc.nextLine();

                    // fetch loan details
                    ResultSet rs = LoanDAO.getLoanById(loanId);
                    try {
                        if (rs != null && rs.next()) {
                            int custId = rs.getInt("customer_id");
                            double amt = rs.getDouble("amount");

                            if (op.equalsIgnoreCase("a")) {
                                LoanService.approveLoan(loanId, custId, amt, admin.getAdminId());
                            } else {
                                LoanService.rejectLoan(loanId, admin.getAdminId());
                            }
                        }
                    } catch (Exception e) { e.printStackTrace(); }
                    break;

                case 3:
                    return;
            }
        }
    }


    private static void A2Menu(Admin admin) {

        Scanner sc = new Scanner(System.in);

        while (true) {
            System.out.println("\n=== A2 ADMIN MENU ===");
            System.out.println("1. View Pending Medium Loans (₹20,000 – ₹1,00,000)");
            System.out.println("2. Approve/Reject Loan");
            System.out.println("3. Logout");

            int choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {
                case 1:
                    LoanService.showPendingLoans("A2");
                    break;

                case 2:
                    System.out.print("Enter Loan ID: ");
                    int loanId = sc.nextInt();
                    sc.nextLine();

                    System.out.print("Approve or Reject? (a/r): ");
                    String op = sc.nextLine();

                    ResultSet rs = LoanDAO.getLoanById(loanId);

                    try {
                        if (rs != null && rs.next()) {

                            double amount = rs.getDouble("amount");

                            // Role Validation
                            if (amount < 20000 || amount > 100000) {
                                System.out.println("❌ You are NOT authorized to approve this loan.");
                                break;
                            }

                            int custId = rs.getInt("customer_id");

                            if (op.equalsIgnoreCase("a")) {
                                LoanService.approveLoan(loanId, custId, amount, admin.getAdminId());
                            } else {
                                LoanService.rejectLoan(loanId, admin.getAdminId());
                            }
                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;

                case 3:
                    return;
            }
        }
    }


    private static void A3Menu(Admin admin) {

        Scanner sc = new Scanner(System.in);

        while (true) {
            System.out.println("\n=== A3 ADMIN MENU ===");
            System.out.println("1. View Pending Small Loans (< ₹20,000)");
            System.out.println("2. Approve/Reject Loan");
            System.out.println("3. Logout");

            int choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {
                case 1:
                    LoanService.showPendingLoans("A3");
                    break;

                case 2:
                    System.out.print("Enter Loan ID: ");
                    int loanId = sc.nextInt();
                    sc.nextLine();

                    System.out.print("Approve or Reject? (a/r): ");
                    String op = sc.nextLine();

                    ResultSet rs = LoanDAO.getLoanById(loanId);

                    try {
                        if (rs != null && rs.next()) {

                            double amount = rs.getDouble("amount");

                            // Role Validation
                            if (amount >= 20000) {
                                System.out.println("❌ You are NOT authorized to approve this loan.");
                                break;
                            }

                            int custId = rs.getInt("customer_id");

                            if (op.equalsIgnoreCase("a")) {
                                LoanService.approveLoan(loanId, custId, amount, admin.getAdminId());
                            } else {
                                LoanService.rejectLoan(loanId, admin.getAdminId());
                            }
                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;

                case 3:
                    return;
            }
        }
    }


    private static void A4Menu(Admin admin) {

        Scanner sc = new Scanner(System.in);

        while (true) {
            System.out.println("\n=== A4 ADMIN MENU ===");
            System.out.println("1. View Pending Credit Card Applications");
            System.out.println("2. Approve Credit Card");
            System.out.println("3. Reject Credit Card");
            System.out.println("4. Logout");

            int choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {
                case 1:
                    CreditCardService.showPendingCards();
                    break;

                case 2:
                    System.out.print("Enter Card ID to Approve: ");
                    int approveId = sc.nextInt();
                    sc.nextLine();
                    CreditCardService.approveCard(approveId, admin.getAdminId());
                    break;

                case 3:
                    System.out.print("Enter Card ID to Reject: ");
                    int rejectId = sc.nextInt();
                    sc.nextLine();
                    CreditCardService.rejectCard(rejectId, admin.getAdminId());
                    break;

                case 4:
                    return;

                default:
                    System.out.println("Invalid choice.");
            }
        }
    }

}
