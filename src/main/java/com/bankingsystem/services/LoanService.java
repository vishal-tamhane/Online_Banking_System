package com.bankingsystem.services;
import com.bankingsystem.models.Customer;

import com.bankingsystem.dao.CustomerDAO;
import com.bankingsystem.dao.LoanDAO;
import com.bankingsystem.dao.TransactionDAO;

import java.sql.ResultSet;

public class LoanService {

    public static void showPendingLoans(String role) {

        try {
            ResultSet rs = LoanDAO.getPendingLoansForAdmin(role);

            System.out.println("\n===== PENDING LOANS =====");

            boolean found = false;

            while (rs.next()) {
                found = true;
                System.out.println("\n----------------------------------");
                System.out.println("Loan ID: " + rs.getInt("loan_id"));
                System.out.println("Customer ID: " + rs.getInt("customer_id"));
                System.out.println("Amount: ₹" + rs.getDouble("amount"));
                System.out.println("Status: " + rs.getString("status"));
                System.out.println("Requested On: " + rs.getTimestamp("timestamp"));
            }

            if (!found) {
                System.out.println("No pending loans for your role.");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

//    public static void approveLoan(int loanId, int customerId, double amount, String adminId) {
//        LoanDAO.approveLoan(loanId, adminId);
//
//        // credit money to customer
//        double currentBalance = CustomerDAO.getBalance(customerId);
//        double newBalance = currentBalance + amount;
//
//        CustomerDAO.updateBalance(customerId, newBalance);
//
//        // log transaction
//        TransactionDAO.logTransaction(customerId,
//                "LOAN_CREDIT",
//                amount,
//                "BANK",
//                "Loan credited after approval");
//
//        System.out.println("\n✔ Loan Approved Successfully!");
//    }

    public static void rejectLoan(int loanId, String adminId) {
        LoanDAO.rejectLoan(loanId, adminId);
        System.out.println("\n❌ Loan Rejected.");
    }
    public static void applyForLoan(Customer customer, double amount, int tenureMonths) {

        if (amount <= 0) {
            System.out.println("❌ Invalid loan amount.");
            return;
        }

        // Credit score check
        if (customer.getCreditScore() < 100) {
            System.out.println("❌ Loan Auto-Rejected: Credit score too low.");
            return;
        }

        // Determine approver role
        String approverRole;

        if (amount < 20000) {
            approverRole = "A3";
        } else if (amount <= 100000) {
            approverRole = "A2";
        } else {
            approverRole = "A1";
        }

        // EMI calculation (simple version)
        double monthlyEmi = amount / tenureMonths;

        // Insert loan in DB
        LoanDAO.createLoan(
                customer.getCustomerId(),
                amount,
                monthlyEmi,
                tenureMonths,
                approverRole
        );

        System.out.println("\n✔ Loan request submitted successfully!");
        System.out.println("Required Approver: " + approverRole);
        System.out.println("Monthly EMI: ₹" + monthlyEmi);
    }
    public static void showCustomerLoanStatus(int customerId) {

        try {
            ResultSet rs = LoanDAO.getLoansByCustomer(customerId);

            System.out.println("\n===== YOUR LOAN STATUS =====");

            boolean found = false;

            while (rs.next()) {
                found = true;

                System.out.println("\n----------------------------------");
                System.out.println("Loan ID       : " + rs.getInt("loan_id"));
                System.out.println("Amount        : ₹" + rs.getDouble("amount"));
                System.out.println("EMI           : ₹" + rs.getDouble("monthly_emi"));
                System.out.println("Tenure        : " + rs.getInt("tenure_months") + " months");
                System.out.println("Status        : " + rs.getString("status"));
                System.out.println("Remaining Amt : ₹" + rs.getDouble("remaining_amount"));
                System.out.println("Requested On  : " + rs.getTimestamp("timestamp"));
            }

            if (!found) {
                System.out.println("You have no loan applications.");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static void approveLoan(int loanId, int customerId, double amount, String adminId) {

        LoanDAO.approveLoan(loanId, adminId);
        double newBalance = CustomerDAO.getBalance(customerId) + amount;
        CustomerDAO.updateBalance(customerId, newBalance);

        // ⭐ Add log here
        LoggingService.info(
                adminId,
                "LOAN_APPROVAL",
                "Loan approved. Loan ID: " + loanId
        );

        System.out.println("✔ Loan Approved Successfully!");
    }


}
