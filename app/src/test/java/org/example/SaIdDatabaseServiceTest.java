package org.example;


import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class SaIdDatabaseServiceTest {
    
    private static final String TEST_DB_URL = "jdbc:sqlite:test_sa_id_records.db";
    private SaIdDatabaseService dbService;
    
    @BeforeEach
    public void setUp() {
        // Override the DB_URL for testing
        dbService = new SaIdDatabaseService() {
            @Override
            protected String getDbUrl() {
                return TEST_DB_URL;
            }
        };
        
        // Initialize test database
        dbService.initDatabase();
    }
    
    @AfterEach
    public void tearDown() {
        // Clean up test database
        try (Connection conn = DriverManager.getConnection(TEST_DB_URL);
             Statement stmt = conn.createStatement()) {
            
            stmt.execute("DROP TABLE IF EXISTS sa_id_records");
        } catch (Exception e) {
            System.err.println("Error cleaning up test database: " + e.getMessage());
        }
    }
    
    @Test
    public void testAddAndRetrieveIdRecord() {
        // Given
        String validId = "2001014800086";
        SaIdRecord record = new SaIdRecord(validId);
        
        // When
        boolean added = dbService.addIdRecord(record);
        List<SaIdRecord> records = dbService.getAllIdRecords();
        
        // Then
        assertTrue(added);
        assertEquals(1, records.size());
        assertEquals(validId, records.get(0).getIdNumber());
    }
    
    @Test
    public void testAddDuplicateIdRecord() {
        // Given
        String validId = "2001014800086";
        SaIdRecord record = new SaIdRecord(validId);
        
        // When
        boolean firstAdd = dbService.addIdRecord(record);
        boolean secondAdd = dbService.addIdRecord(record);
        
        // Then
        assertTrue(firstAdd);
        assertFalse(secondAdd);
        assertEquals(1, dbService.getAllIdRecords().size());
    }
    
    @Test
    public void testDeleteIdRecord() {
        // Given
        String validId = "2001014800086";
        SaIdRecord record = new SaIdRecord(validId);
        dbService.addIdRecord(record);
        
        // When
        boolean deleted = dbService.deleteIdRecord(validId);
        
        // Then
        assertTrue(deleted);
        assertTrue(dbService.getAllIdRecords().isEmpty());
    }
    
    @Test
    public void testIdNumberExists() {
        // Given
        String validId = "2001014800086";
        SaIdRecord record = new SaIdRecord(validId);
        dbService.addIdRecord(record);
        
        // When/Then
        assertTrue(dbService.idNumberExists(validId));
        assertFalse(dbService.idNumberExists("9901010000087"));
    }
    
    @Test
    public void testGetIdRecordsSortedByAge() {
        // Given - Add IDs in non-chronological order
        SaIdRecord record1 = new SaIdRecord("9001014800082"); // 1990
        SaIdRecord record2 = new SaIdRecord("0501014800081"); // 2005
        SaIdRecord record3 = new SaIdRecord("8001014800084"); // 1980
        
        dbService.addIdRecord(record1);
        dbService.addIdRecord(record2);
        dbService.addIdRecord(record3);
        
        // When
        List<SaIdRecord> sortedRecords = dbService.getIdRecordsSortedByAge();
        
        // Then
        assertEquals(3, sortedRecords.size());
        assertEquals("8001014800084", sortedRecords.get(0).getIdNumber()); // Oldest first
        assertEquals("9001014800082", sortedRecords.get(1).getIdNumber());
        assertEquals("0501014800081", sortedRecords.get(2).getIdNumber()); // Youngest last
    }
}