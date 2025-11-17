package com.bankingsystem.utils;

import org.mindrot.jbcrypt.BCrypt;

public class HashUtil {

    // Hash the PIN
    public static String hashPin(String pin) {
        return BCrypt.hashpw(pin, BCrypt.gensalt());
    }

    // Validate entered PIN with stored hash
    public static boolean verifyPin(String pin, String hash) {
        return BCrypt.checkpw(pin, hash);
    }
}
