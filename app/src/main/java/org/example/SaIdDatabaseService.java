package org.example;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Service class to handle database operations for South African ID records
 */
public abstract class SaIdDatabaseService {
    private static final String DB_URL = "jdbc:sqlite:sa_id_records.db";
    
    /**
     * Initializes the database by creating necessary tables if they don't exist
     */
    public void initDatabase() {
        try (Connection conn = DriverManager.getConnection(DB_URL);
             Statement stmt = conn.createStatement()) {
            
            // Create table if it doesn't exist
            String createTableSQL = "CREATE TABLE IF NOT EXISTS sa_id_records (" +
                                    "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                                    "id_number TEXT UNIQUE NOT NULL," +
                                    "birth_date TEXT NOT NULL," +
                                    "gender CHAR(1) NOT NULL," +
                                    "citizenship CHAR(1) NOT NULL," +
                                    "date_added TEXT NOT NULL" +
                                    ")";
            stmt.execute(createTableSQL);
            
            System.out.println("Database initialized successfully");
        } catch (SQLException e) {
            System.err.println("Database initialization error: " + e.getMessage());
        }
    }
    
    /**
     * Adds a valid South African ID to the database
     * 
     * @param idRecord The ID record to add
     * @return true if added successfully, false otherwise
     */
    public boolean addIdRecord(SaIdRecord idRecord) {
        String sql = "INSERT INTO sa_id_records (id_number, birth_date, gender, citizenship, date_added) " +
                     "VALUES (?, ?, ?, ?, ?)";
        
        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, idRecord.getIdNumber());
            pstmt.setString(2, idRecord.getBirthDate().toString());
            pstmt.setString(3, String.valueOf(idRecord.getGender()));
            pstmt.setString(4, String.valueOf(idRecord.getCitizenship()));
            pstmt.setString(5, idRecord.getDateAdded().toString());
            
            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            if (e.getMessage().contains("UNIQUE constraint failed")) {
                System.err.println("This ID number already exists in the database");
            } else {
                System.err.println("Error adding ID record: " + e.getMessage());
            }
            return false;
        }
    }
    
    /**
     * Retrieves all ID records from the database
     * 
     * @return List of ID records
     */
    public List<SaIdRecord> getAllIdRecords() {
        List<SaIdRecord> records = new ArrayList<>();
        String sql = "SELECT * FROM sa_id_records";
        
        try (Connection conn = DriverManager.getConnection(DB_URL);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                records.add(mapResultSetToIdRecord(rs));
            }
        } catch (SQLException e) {
            System.err.println("Error retrieving ID records: " + e.getMessage());
        }
        
        return records;
    }
    
    /**
     * Retrieves all ID records sorted by birthdate (oldest to youngest)
     * 
     * @return List of ID records sorted by age
     */
    public List<SaIdRecord> getIdRecordsSortedByAge() {
        List<SaIdRecord> records = new ArrayList<>();
        String sql = "SELECT * FROM sa_id_records ORDER BY birth_date ASC";
        
        try (Connection conn = DriverManager.getConnection(DB_URL);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                records.add(mapResultSetToIdRecord(rs));
            }
        } catch (SQLException e) {
            System.err.println("Error retrieving sorted ID records: " + e.getMessage());
        }
        
        return records;
    }
    
    /**
     * Deletes an ID record from the database
     * 
     * @param idNumber The ID number to delete
     * @return true if deleted successfully, false otherwise
     */
    public boolean deleteIdRecord(String idNumber) {
        String sql = "DELETE FROM sa_id_records WHERE id_number = ?";
        
        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, idNumber);
            int rowsAffected = pstmt.executeUpdate();
            
            return rowsAffected > 0;
        } catch (SQLException e) {
            System.err.println("Error deleting ID record: " + e.getMessage());
            return false;
        }
    }
    
    /**
     * Checks if an ID number exists in the database
     * 
     * @param idNumber The ID number to check
     * @return true if exists, false otherwise
     */
    public boolean idNumberExists(String idNumber) {
        String sql = "SELECT COUNT(*) FROM sa_id_records WHERE id_number = ?";
        
        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, idNumber);
            ResultSet rs = pstmt.executeQuery();
            
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        } catch (SQLException e) {
            System.err.println("Error checking if ID exists: " + e.getMessage());
        }
        
        return false;
    }
    
    /**
     * Maps a database result set to an ID record object
     */
    private SaIdRecord mapResultSetToIdRecord(ResultSet rs) throws SQLException {
        Long id = rs.getLong("id");
        String idNumber = rs.getString("id_number");
        LocalDate birthDate = LocalDate.parse(rs.getString("birth_date"));
        char gender = rs.getString("gender").charAt(0);
        char citizenship = rs.getString("citizenship").charAt(0);
        LocalDateTime dateAdded = LocalDateTime.parse(rs.getString("date_added"));
        
        return new SaIdRecord(id, idNumber, birthDate, gender, citizenship, dateAdded);
    }

    protected abstract String getDbUrl();
}