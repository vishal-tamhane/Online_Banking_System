package com.bankingsystem.services;

import com.bankingsystem.dao.CustomerDAO;
import com.bankingsystem.dao.TransactionDAO;
import com.bankingsystem.models.Customer;

public class CustomerService {

    // Check Balance
    public static void checkBalance(Customer customer) {
        System.out.println("\nYour Current Balance: ₹" + customer.getBalance());
    }

    // Deposit Money
    public static void deposit(Customer customer, double amount) {
        if (amount <= 0) {
            System.out.println("❌ Amount must be greater than 0.");
            return;
        }

        double newBalance = customer.getBalance() + amount;
        customer.setBalance(newBalance);

//        CustomerDAO.updateBalance(customer.getCustomerId(), newBalance);

        CustomerDAO.updateBalance(customer.getCustomerId(), newBalance);
        TransactionDAO.logTransaction(customer.getCustomerId(), "DEPOSIT", amount, "CASH", "User deposit");


        System.out.println("₹" + amount + " deposited successfully!");
        System.out.println("Updated Balance: ₹" + newBalance);
    }

    // Withdraw Money
    public static void withdraw(Customer customer, double amount) {
        if (amount <= 0) {
            System.out.println("❌ Amount must be greater than 0.");
            return;
        }

        // Minimum balance rule
        if (customer.getBalance() - amount < 500) {
            System.out.println("❌ You must maintain a minimum balance of ₹500!");
            return;
        }

        double newBalance = customer.getBalance() - amount;
        customer.setBalance(newBalance);

        CustomerDAO.updateBalance(customer.getCustomerId(), newBalance);
        TransactionDAO.logTransaction(customer.getCustomerId(), "WITHDRAW", amount, "CASH", "User withdrawal");


        System.out.println("₹" + amount + " withdrawn successfully!");
        System.out.println("Updated Balance: ₹" + newBalance);
    }
    public static void sendMoney(Customer sender, String receiverPhone, double amount, boolean sameBank) {

        if (amount <= 0) {
            System.out.println("❌ Invalid amount!");
            return;
        }

        Customer receiver = CustomerDAO.getCustomerByPhone(receiverPhone);

        if (receiver == null) {
            System.out.println("❌ Receiver not found!");
            return;
        }

        double charge = sameBank ? 0 : amount * 0.02;
        double totalDebit = amount + charge;

        if (sender.getBalance() - totalDebit < 500) {
            System.out.println("❌ Insufficient balance! Keep minimum ₹500.");
            return;
        }

        // Deduct from sender
        double senderNewBal = sender.getBalance() - totalDebit;
        sender.setBalance(senderNewBal);
        CustomerDAO.updateBalance(sender.getCustomerId(), senderNewBal);

        // Credit receiver
        double receiverNewBal = receiver.getBalance() + amount;
        receiver.setBalance(receiverNewBal);
        CustomerDAO.updateBalance(receiver.getCustomerId(), receiverNewBal);

        // Log sender
        TransactionDAO.logTransaction(
                sender.getCustomerId(),
                "TRANSFER_SENT",
                amount,
                sameBank ? "SAME_BANK" : "OTHER_BANK",
                "Sent to phone: " + receiverPhone
        );

        // Log receiver
        TransactionDAO.logTransaction(
                receiver.getCustomerId(),
                "TRANSFER_RECEIVED",
                amount,
                sameBank ? "SAME_BANK" : "OTHER_BANK",
                "Received from phone: " + sender.getPhone()
        );

        System.out.println("\n₹" + amount + " sent successfully to " + receiver.getName());
        if (!sameBank) System.out.println("Bank Charge: ₹" + charge);
        System.out.println("New Balance: ₹" + senderNewBal);
    }

}
