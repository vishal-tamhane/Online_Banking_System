package com.bankingsystem.menus;

import com.bankingsystem.dao.TransactionDAO;
import com.bankingsystem.models.Customer;
import com.bankingsystem.services.AuthenticationService;
import com.bankingsystem.services.CreditCardService;
import com.bankingsystem.services.CustomerService;
import com.bankingsystem.services.LoanService;

import java.util.Scanner;

public class CustomerMenu {

    private static Scanner sc = new Scanner(System.in);

    // ========== LOGIN FUNCTION ==========
    public static Customer login() {

        System.out.println("\n=== CUSTOMER LOGIN ===");
        System.out.print("Enter Phone: ");
        String phone = sc.nextLine();

        System.out.print("Enter Aadhar: ");
        String aadhar = sc.nextLine();

        System.out.print("Enter PIN: ");
        String pin = sc.nextLine(); // later we convert to hash

        Customer c = AuthenticationService.loginCustomer(phone, aadhar, pin);

        if (c != null) {
            System.out.println("\nLogin Successful!");
            System.out.println("Welcome, " + c.getName());
            return c;
        } else {
            System.out.println("\n❌ Invalid credentials. Try again.");
            return null;
        }
    }

    // ========== CUSTOMER MENU AFTER LOGIN ==========
    public static void showCustomerMenu(Customer customer) {

        while (true) {
            System.out.println("\n===== CUSTOMER MENU =====");
            System.out.println("1. Check Balance");
            System.out.println("2. Deposit Money");
            System.out.println("3. Withdraw Money");
            System.out.println("4. Transaction History");
            System.out.println("5. Send Money");
            System.out.println("6. Apply for Loan");
            System.out.println("7. Apply for Credit Card");

            System.out.println("8. View Loan Status");
            System.out.println("9. Logout");
            System.out.print("Choose an option: ");
            int choice = sc.nextInt();
            sc.nextLine(); // fix input

            switch (choice) {
                case 1:
                    CustomerService.checkBalance(customer);
                    break;

                case 2:
                    System.out.print("Enter amount to deposit: ");
                    double depositAmount = sc.nextDouble();
                    sc.nextLine();
                    CustomerService.deposit(customer, depositAmount);
                    break;

                case 3:
                    System.out.print("Enter amount to withdraw: ");
                    double withdrawAmount = sc.nextDouble();
                    sc.nextLine();
                    CustomerService.withdraw(customer, withdrawAmount);
                    break;


                case 4:
                    TransactionDAO.showTransactionHistory(customer.getCustomerId());
                    break;
                case 5:
                    System.out.print("Enter receiver phone: ");
                    String recPhone = sc.nextLine();

                    System.out.print("Enter amount: ");
                    double amt = sc.nextDouble();
                    sc.nextLine();

                    System.out.print("Same bank? (yes/no): ");
                    String bankType = sc.nextLine();

                    boolean sameBank = bankType.equalsIgnoreCase("yes");

                    CustomerService.sendMoney(customer, recPhone, amt, sameBank);
                    break;

                case 6:
                    System.out.print("Enter loan amount: ");
                    double loanAmount = sc.nextDouble();

                    System.out.print("Enter loan tenure (months): ");
                    int tenure = sc.nextInt();
                    sc.nextLine();

                    LoanService.applyForLoan(customer, loanAmount, tenure);
                    break;
                case 7:
                    CreditCardService.applyForCreditCard(customer);
                    break;
                case 8:
                    LoanService.showCustomerLoanStatus(customer.getCustomerId());
                    break;

                case 9:
                    System.out.println("Logging out...");
                    return;

                default:
                    System.out.println("Invalid choice. Try again.");
            }
        }
    }
}
