package com.bankingsystem.models;

public class Customer {
    private int customerId;
    private String name;
    private String phone;
    private String aadhar;
    private String email;
    private String pinHash;
    private double balance;
    private double monthlySalary;
    private int creditScore;
    private String accountType;
    private String status;

    // Full constructor
    public Customer(int customerId, String name, String phone, String aadhar, String email,
                    String pinHash, double balance, double monthlySalary,
                    int creditScore, String accountType, String status) {

        this.customerId = customerId;
        this.name = name;
        this.phone = phone;
        this.aadhar = aadhar;
        this.email = email;
        this.pinHash = pinHash;
        this.balance = balance;
        this.monthlySalary = monthlySalary;
        this.creditScore = creditScore;
        this.accountType = accountType;
        this.status = status;
    }

    // Getters
    public int getCustomerId() { return customerId; }
    public String getName() { return name; }
    public String getPhone() { return phone; }
    public String getAadhar() { return aadhar; }
    public String getEmail() { return email; }
    public String getPinHash() { return pinHash; }
    public double getBalance() { return balance; }
    public double getMonthlySalary() { return monthlySalary; }
    public int getCreditScore() { return creditScore; }
    public String getAccountType() { return accountType; }
    public String getStatus() { return status; }

    // Setter for updating balance
    public void setBalance(double balance) { this.balance = balance; }

    @Override
    public String toString() {
        return "\nCustomer Info: \n" +
                "ID: " + customerId + "\n" +
                "Name: " + name + "\n" +
                "Phone: " + phone + "\n" +
                "Aadhar: " + aadhar + "\n" +
                "Email: " + email + "\n" +
                "Balance: " + balance + "\n" +
                "Salary: " + monthlySalary + "\n" +
                "Credit Score: " + creditScore + "\n" +
                "Account Type: " + accountType + "\n" +
                "Status: " + status + "\n";
    }
}
