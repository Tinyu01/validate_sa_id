# South African ID Validator & Database

A modern Java application built with Gradle that validates South African ID numbers, providing detailed reasons for invalidity, and stores valid IDs in a SQLite database with sorting and management capabilities.

## Features

- **ID Validation**: Validates South African ID numbers with detailed reasons for invalidity
- **Database Storage**: Stores valid IDs in a SQLite database
- **Data Extraction**: Extracts birth date, gender, and citizenship status from valid IDs
- **ID Management**: View, sort, and delete stored IDs
- **Sorting**: Sort IDs from oldest to youngest based on birth date

## Technology Stack

- Java 17
- Gradle 8.x
- SQLite (via sqlite-jdbc driver)
- JUnit 5

## Setup Instructions

### Prerequisites
- Java Development Kit (JDK) 17 or later
- Gradle 8.x

### Installation
1. Clone the repository:
   ```bash
   git clone https://github.com/Tinyu01/validate_sa_id.git
   cd validate_sa_id
   ```

2. Build the project:
   ```bash
   gradle build
   ```

### Running the Application
Run the application using Gradle:
```bash
gradle run
```

Or build and run the JAR file:
```bash
gradle build
java -jar build/libs/validate_sa_id-1.0-SNAPSHOT.jar
```

## Usage

The application provides a menu-driven interface with the following options:

1. **Validate an ID number**: Checks if an ID is valid and shows detailed validation results
2. **Add valid ID to database**: Validates an ID and stores it if valid
3. **Display all stored IDs**: Shows all IDs in the database
4. **Display IDs sorted by age**: Lists IDs from oldest to youngest
5. **Delete an ID from database**: Removes an ID from the database
0. **Exit**: Closes the application

### Example Interaction

```
South African ID Validator and Database
--------------------------------------

MAIN MENU
1. Validate an ID number
2. Add valid ID to database
3. Display all stored IDs
4. Display IDs sorted by age (oldest to youngest)
5. Delete an ID from database
0. Exit

Enter your choice: 1

--- ID VALIDATION ---
Enter a 13-digit ID number to validate: 9001014800082
ID: 9001014800082 -> Valid

ID Information:
- Birth Date: 1990-01-01
- Age: 35
- Gender: Male
- Status: Citizen

Press Enter to continue...

MAIN MENU
1. Validate an ID number
2. Add valid ID to database
3. Display all stored IDs
4. Display IDs sorted by age (oldest to youngest)
5. Delete an ID from database
0. Exit

Enter your choice: 2

--- STORE ID IN DATABASE ---
Enter a 13-digit ID number to validate and store: 9001014800082
ID successfully added to database.
ID: 9001014800082 | Birth Date: 1990-01-01 | Gender: Male | Citizen | Added: 2025-04-30

Press Enter to continue...

MAIN MENU
1. Validate an ID number
2. Add valid ID to database
3. Display all stored IDs
4. Display IDs sorted by age (oldest to youngest)
5. Delete an ID from database
0. Exit

Enter your choice: 4

--- IDS SORTED BY AGE (OLDEST TO YOUNGEST) ---
Found 1 records:
---------------------------------------------------
1. ID: 9001014800082 | Birth Date: 1990-01-01 | Gender: Male | Citizen | Added: 2025-04-30
---------------------------------------------------

Press Enter to continue...
```

## Validation Rules

South African ID numbers follow a specific format: `YYMMDD SSSS C Z Z`:

- **YYMMDD**: Date of birth (YY=year, MM=month, DD=day)
- **SSSS**: Serial number (includes gender information: females 0000-4999, males 5000-9999)
- **C**: Citizenship status (0 for SA citizen, 1 for permanent resident)
- **Z**: Checksum digits (calculated using the Luhn algorithm)

The validator checks:
- **Length**: Must be 13 digits
- **Content**: All characters must be digits
- **Date**: Valid date in YYMMDD format (with leap year handling)
- **Citizenship**: Value must be 0 or 1
- **Checksum**: Last digit verified using the Luhn algorithm

## Project Structure

```
validate_sa_id/
├── .gitignore
├── build.gradle
├── settings.gradle
├── src/
│   ├── main/
│   │   └── java/com/example/
│   │       ├── App.java                 # Main application with CLI interface
│   │       ├── ValidateSaId.java        # ID validation logic
│   │       ├── SaIdRecord.java          # Entity class for ID records
│   │       └── SaIdDatabaseService.java # Database operations manager
│   └── test/
│       └── java/com/example/
│           ├── ValidateSaIdTest.java       # Tests for validation logic
│           └── SaIdDatabaseServiceTest.java # Tests for database operations
└── README.md
```

## Database Schema

The application uses a SQLite database with a single table:

### Table: sa_id_records

| Column      | Type     | Description                            |
|-------------|----------|----------------------------------------|
| id          | INTEGER  | Primary key, auto-incremented          |
| id_number   | TEXT     | SA ID number (unique)                  |
| birth_date  | TEXT     | Birth date in ISO format (YYYY-MM-DD)  |
| gender      | CHAR(1)  | Gender (M/F)                           |
| citizenship | CHAR(1)  | Citizenship status (0/1)               |
| date_added  | TEXT     | Date record was added to DB            |

## Testing

Run tests with Gradle:
```bash
gradle test
```

Tests include:
- Validation of various ID formats and edge cases
- Database operations (add, retrieve, delete)
- Sorting functionality
- Duplicate detection

## Code Overview

### Key Classes

1. **ValidateSaId**: Handles validation logic with detailed error messages
   - `validateIdNumber(String id)`: Returns a ValidationResult with status and reasons
   - `isIdNumberValid(String id)`: Legacy method for backwards compatibility

2. **SaIdRecord**: Entity class representing a stored ID
   - Parses ID number to extract demographic information
   - Calculates age based on birth date

3. **SaIdDatabaseService**: Manages database operations
   - `initDatabase()`: Creates necessary tables
   - `addIdRecord(SaIdRecord)`: Adds new ID to database
   - `getAllIdRecords()`: Retrieves all stored IDs
   - `getIdRecordsSortedByAge()`: Returns IDs sorted by birth date
   - `deleteIdRecord(String)`: Removes ID from database

4. **App**: Main application with UI logic
   - Menu-driven interface to access all features
   - Input validation and formatting

## Contributing

Contributions are welcome! Please feel free to submit a Pull Request.

## License

This project is licensed under the MIT License - see the LICENSE file for details.