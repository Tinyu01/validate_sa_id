package org.example;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

public class ValidateSaIdTest {
    @Test
    public void testValidIds() {
        assertTrue(ValidateSaId.isIdNumberValid("2001014800086"));
        assertTrue(ValidateSaId.isIdNumberValid("2909035800085"));
    }

    @Test
    public void testTooShort() {
        assertFalse(ValidateSaId.isIdNumberValid("123456789012")); // 12 digits
    }

    @Test
    public void testTooLong() {
        assertFalse(ValidateSaId.isIdNumberValid("12345678901234")); // 14 digits
    }

    @Test
    public void testNonDigits() {
        assertFalse(ValidateSaId.isIdNumberValid("20010A4800086"));
    }

    @Test
    public void testInvalidMonth() {
        assertFalse(ValidateSaId.isIdNumberValid("2013014800086")); // MM=13
    }

    @Test
    public void testInvalidDay() {
        assertFalse(ValidateSaId.isIdNumberValid("2002304800086")); // Feb 30
    }

    @Test
    public void testLeapYear() {
        assertTrue(ValidateSaId.isIdNumberValid("2002294800086")); // Feb 29, YY=20
    }

    @Test
    public void testInvalidCitizenship() {
        assertFalse(ValidateSaId.isIdNumberValid("2001014800986")); // C=9
    }

    @Test
    public void testInvalidChecksum() {
        assertFalse(ValidateSaId.isIdNumberValid("2001014800087")); // Altered last digit
    }
}