package org.example;

import java.util.ArrayList;
import java.util.List;

public class ValidateSaId {
    // Record to hold validation result and reasons
    public record ValidationResult(boolean isValid, List<String> reasons) {
        public static ValidationResult valid() {
            return new ValidationResult(true, List.of());
        }

        public static ValidationResult invalid(String reason) {
            List<String> reasons = new ArrayList<>();
            reasons.add(reason);
            return new ValidationResult(false, reasons);
        }

        public static ValidationResult invalid(List<String> reasons) {
            return new ValidationResult(false, reasons);
        }
    }

    private static int getDaysInMonth(int month, int yy) {
        return switch (month) {
            case 2 -> (yy % 4 == 0) ? 29 : 28; // Simplified leap year check
            case 4, 6, 9, 11 -> 30;
            default -> 31;
        };
    }

    private static boolean validateChecksum(String id) {
        int sum = 0;
        boolean doubleDigit = false;
        for (int i = id.length() - 1; i >= 0; i--) {
            int digit = Character.getNumericValue(id.charAt(i));
            if (doubleDigit) {
                digit *= 2;
                if (digit > 9) {
                    digit -= 9;
                }
            }
            sum += digit;
            doubleDigit = !doubleDigit;
        }
        return sum % 10 == 0;
    }

    public static ValidationResult validateIdNumber(String id) {
        List<String> reasons = new ArrayList<>();

        // Check null or incorrect length
        if (id == null) {
            reasons.add("ID is null");
            return ValidationResult.invalid(reasons);
        }
        if (id.length() != 13) {
            reasons.add("ID length must be 13 digits (got " + id.length() + ")");
            return ValidationResult.invalid(reasons);
        }

        // Check if all characters are digits
        if (!id.matches("\\d{13}")) {
            reasons.add("ID contains non-digit characters");
            return ValidationResult.invalid(reasons);
        }

        // Validate date (YYMMDD)
        int yy, mm, dd;
        try {
            yy = Integer.parseInt(id.substring(0, 2));
            mm = Integer.parseInt(id.substring(2, 4));
            dd = Integer.parseInt(id.substring(4, 6));
        } catch (NumberFormatException e) {
            reasons.add("Invalid number format in date portion");
            return ValidationResult.invalid(reasons);
        }

        if (mm < 1 || mm > 12) {
            reasons.add("Invalid month: " + mm);
        }

        int maxDays = getDaysInMonth(mm, yy);
        if (dd < 1 || dd > maxDays) {
            reasons.add("Invalid day: " + dd + " for month " + mm + " and year " + yy);
        }

        // Validate citizenship (digit 10, 0-based index)
        char citizenship = id.charAt(10);
        if (citizenship != '0' && citizenship != '1') {
            reasons.add("Invalid citizenship digit: " + citizenship + " (must be 0 or 1)");
        }

        // Validate checksum
        if (!validateChecksum(id)) {
            reasons.add("Invalid checksum");
        }

        // Return result
        if (reasons.isEmpty()) {
            return ValidationResult.valid();
        } else {
            return ValidationResult.invalid(reasons);
        }
    }

    // Maintain original method for backward compatibility
    public static boolean isIdNumberValid(String id) {
        return validateIdNumber(id).isValid();
    }
}
