package com.bankingsystem.services;

import com.bankingsystem.dao.LogDAO;

public class LoggingService {

    public static void info(String userId, String action, String message) {
        LogDAO.addLog(message, "INFO", userId, action);
    }

    public static void warning(String userId, String action, String message) {
        LogDAO.addLog(message, "WARNING", userId, action);
    }

    public static void error(String userId, String action, String message) {
        LogDAO.addLog(message, "ERROR", userId, action);
    }
}
