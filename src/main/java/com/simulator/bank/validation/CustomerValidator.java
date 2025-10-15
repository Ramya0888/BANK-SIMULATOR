package com.simulator.bank.validation;

import com.simulator.bank.model.Customer;
import java.time.LocalDate;

public class CustomerValidator {

    public static boolean isValidUsername(String username) {
        return username != null && !username.trim().isEmpty() && username.length() <= 50;
    }

    public static boolean isValidPassword(String password) {
        return password != null && password.length() >= 6;
    }

    public static boolean isValidEmail(String email) {
        return email != null && email.matches("^[\\w._%+-]+@[\\w.-]+\\.[A-Za-z]{2,6}$");
    }

    public static boolean isValidPhone(String phone) {
        return phone != null && phone.matches("\\d{10}");
    }

    public static boolean isValidAadhar(String aadhar) {
        return aadhar == null || aadhar.matches("\\d{12}");
    }

    public static boolean isValidAge(int age) {
        return age > 0 && age <= 120;
    }

    public static boolean isValidDOB(LocalDate dob) {
        return dob != null && !dob.isAfter(LocalDate.now());
    }

    public static boolean isValidStatus(String status) {
        return status == null || status.matches("ACTIVE|INACTIVE|BLOCKED");
    }

  
    public static void validate(Customer c) {
        if (c == null) {
            throw new IllegalArgumentException("Customer cannot be null");
        }
        if (!isValidUsername(c.getUsername())) {
            throw new IllegalArgumentException("Invalid username");
        }
        if (!isValidPassword(c.getPassword())) {
            throw new IllegalArgumentException("Password must be at least 6 characters");
        }
        if (c.getEmail() != null && !isValidEmail(c.getEmail())) {
            throw new IllegalArgumentException("Invalid email format");
        }
        if (!isValidPhone(c.getPhoneNumber())) {
            throw new IllegalArgumentException("Phone number must be 10 digits");
        }
      
        if (!isValidDOB(c.getDob())) {
            throw new IllegalArgumentException("Date of birth cannot be in the future");
        }
        if (!isValidStatus(c.getStatus())) {
            throw new IllegalArgumentException("Invalid customer status");
        }
    }
}
