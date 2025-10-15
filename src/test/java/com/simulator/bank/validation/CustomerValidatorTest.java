package com.simulator.bank.validation;

import org.junit.jupiter.api.Test;
import java.time.LocalDate;
import static org.junit.jupiter.api.Assertions.*;

public class CustomerValidatorTest {

    @Test
    void testUsername() {
        assertTrue(CustomerValidator.isValidUsername("john_doe"));
        assertFalse(CustomerValidator.isValidUsername(""));
    }

    @Test
    void testPassword() {
        assertTrue(CustomerValidator.isValidPassword("secret123"));
        assertFalse(CustomerValidator.isValidPassword("123"));
    }

    @Test
    void testEmail() {
        assertTrue(CustomerValidator.isValidEmail("user@test.com"));
        assertFalse(CustomerValidator.isValidEmail("wrong-email"));
    }

    @Test
    void testPhone() {
        assertTrue(CustomerValidator.isValidPhone("9876543210"));
        assertFalse(CustomerValidator.isValidPhone("123abc"));
    }

    @Test
    void testAadhar() {
        assertTrue(CustomerValidator.isValidAadhar("123456789012"));
        assertFalse(CustomerValidator.isValidAadhar("abc123"));
    }

    @Test
    void testAgeDOBStatus() {
        assertTrue(CustomerValidator.isValidAge(30));
        assertFalse(CustomerValidator.isValidAge(-1));

        assertTrue(CustomerValidator.isValidDOB(LocalDate.now().minusYears(20)));
        assertFalse(CustomerValidator.isValidDOB(LocalDate.now().plusDays(1)));

        assertTrue(CustomerValidator.isValidStatus("ACTIVE"));
        assertFalse(CustomerValidator.isValidStatus("SOMETHING"));
    }
}
