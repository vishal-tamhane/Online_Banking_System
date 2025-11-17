package com.bankingsystem.services;

import com.bankingsystem.dao.CreditCardDAO;
import com.bankingsystem.models.Customer;

import java.sql.ResultSet;

public class CreditCardService {

    public static void applyForCreditCard(Customer customer) {

        int creditScore = customer.getCreditScore();

        // Step 1 — Eligibility Check
        if (creditScore < 400) {
            System.out.println("❌ Credit Card Auto-Rejected: Low Credit Score (" + creditScore + ")");
            return;
        }

        // Step 2 — Calculate Credit Limit
        double salary = customer.getMonthlySalary();
        if (salary <= 0) {
            System.out.println("❌ Monthly salary not set. Update salary before applying.");
            return;
        }

        double limit = salary * 3;

        // Step 3 — Save Application
        CreditCardDAO.createCreditCardApplication(customer.getCustomerId(), limit);

        System.out.println("\n✔ Credit Card Application Submitted Successfully!");
        System.out.println("Eligible Credit Limit: ₹" + limit);
        System.out.println("Sent to Admin A4 for approval.");
    }

    public static void showPendingCards() {
        try {
            ResultSet rs = CreditCardDAO.getPendingCards();

            System.out.println("\n===== PENDING CREDIT CARD APPLICATIONS =====");

            boolean found = false;

            while (rs.next()) {
                found = true;

                System.out.println("\n----------------------------------");
                System.out.println("Card ID: " + rs.getInt("card_id"));
                System.out.println("Customer ID: " + rs.getInt("customer_id"));
                System.out.println("Credit Limit: ₹" + rs.getDouble("credit_limit"));
                System.out.println("Status: " + rs.getString("status"));
                System.out.println("Requested On: " + rs.getTimestamp("issued_date"));
            }

            if (!found) {
                System.out.println("No pending credit card applications.");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private static String generateCardNumber() {
        StringBuilder card = new StringBuilder();
        for (int i = 0; i < 16; i++) {
            card.append((int)(Math.random() * 10));
        }
        return card.toString();
    }

    private static String generateCVV() {
        StringBuilder cvv = new StringBuilder();
        for (int i = 0; i < 3; i++) {
            cvv.append((int)(Math.random() * 10));
        }
        return cvv.toString();
    }

    public static void approveCard(int cardId, String adminId) {

        String cardNumber = generateCardNumber();
        String cvv = generateCVV();

        java.sql.Date expiryDate =
                java.sql.Date.valueOf(java.time.LocalDate.now().plusYears(5));

        CreditCardDAO.approveCard(cardId, cardNumber, cvv, expiryDate, adminId);

        System.out.println("\n✔ Credit Card Approved!");
        System.out.println("Card Number: " + cardNumber);
        System.out.println("CVV: " + cvv);
        System.out.println("Expiry: " + expiryDate);

        LoggingService.info(
                adminId,
                "CARD_APPROVAL",
                "Credit card approved. Card ID: " + cardId
        );

        System.out.println("✔ Credit Card Approved!");
    }
    public static void rejectCard(int cardId, String adminId) {
        CreditCardDAO.rejectCard(cardId, adminId);
        System.out.println("\n❌ Credit Card Application Rejected.");
    }



}
