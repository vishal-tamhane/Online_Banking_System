package com.bankingsystem.models;

import java.sql.Timestamp;

public class Transaction {
    private long transactionId;
    private int customerId;
    private String type;
    private double amount;
    private String mode;
    private String notes;
    private Timestamp timestamp;

    public Transaction(long transactionId, int customerId, String type, double amount,
                       String mode, String notes, Timestamp timestamp) {
        this.transactionId = transactionId;
        this.customerId = customerId;
        this.type = type;
        this.amount = amount;
        this.mode = mode;
        this.notes = notes;
        this.timestamp = timestamp;
    }
}
