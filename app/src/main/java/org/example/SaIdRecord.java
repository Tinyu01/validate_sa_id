package org.example;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * Entity class representing a stored South African ID record
 */
public class SaIdRecord {
    private Long id;
    private String idNumber;
    private LocalDate birthDate;
    private char gender;
    private char citizenship;
    private LocalDateTime dateAdded;

    // Default constructor
    public SaIdRecord() {
    }

    // Constructor for new records
    public SaIdRecord(String idNumber) {
        this.idNumber = idNumber;
        this.dateAdded = LocalDateTime.now();
        
        // Extract birthdate from ID number
        int year = Integer.parseInt(idNumber.substring(0, 2));
        int month = Integer.parseInt(idNumber.substring(2, 4));
        int day = Integer.parseInt(idNumber.substring(4, 6));
        
        // Determine century (if year > current year's last 2 digits, assume previous century)
        int currentYearLastTwoDigits = LocalDate.now().getYear() % 100;
        int century = (year > currentYearLastTwoDigits) ? 1900 : 2000;
        
        this.birthDate = LocalDate.of(century + year, month, day);
        
        // Extract gender (7-9 digits, odd = male, even = female)
        int genderDigit = Integer.parseInt(idNumber.substring(6, 10));
        this.gender = (genderDigit < 5000) ? 'F' : 'M';
        
        // Extract citizenship (0 = citizen, 1 = permanent resident)
        this.citizenship = idNumber.charAt(10);
    }

    // Constructor with all fields
    public SaIdRecord(Long id, String idNumber, LocalDate birthDate, char gender, char citizenship, LocalDateTime dateAdded) {
        this.id = id;
        this.idNumber = idNumber;
        this.birthDate = birthDate;
        this.gender = gender;
        this.citizenship = citizenship;
        this.dateAdded = dateAdded;
    }

    // Getters and setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getIdNumber() {
        return idNumber;
    }

    public void setIdNumber(String idNumber) {
        this.idNumber = idNumber;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public char getGender() {
        return gender;
    }

    public void setGender(char gender) {
        this.gender = gender;
    }

    public char getCitizenship() {
        return citizenship;
    }

    public void setCitizenship(char citizenship) {
        this.citizenship = citizenship;
    }

    public LocalDateTime getDateAdded() {
        return dateAdded;
    }

    public void setDateAdded(LocalDateTime dateAdded) {
        this.dateAdded = dateAdded;
    }

    public int getAge() {
        return LocalDate.now().getYear() - birthDate.getYear();
    }

    public String getCitizenshipStatus() {
        return citizenship == '0' ? "Citizen" : "Permanent Resident";
    }

    public String getGenderDescription() {
        return gender == 'M' ? "Male" : "Female";
    }

    @Override
    public String toString() {
        return String.format("ID: %s | Birth Date: %s | Gender: %s | %s | Added: %s",
                idNumber, birthDate, getGenderDescription(), getCitizenshipStatus(), 
                dateAdded.toLocalDate());
    }
}