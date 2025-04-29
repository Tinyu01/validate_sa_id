# South African ID Validator (2025 Edition)

A modern Java project built with Gradle and JUnit 5, using Test-Driven Development (TDD) to validate South African ID numbers.

---

## 🧠 Overview

This project implements a `ValidateSaId` class with a static method `isIdNumberValid(String id)` to validate South African ID numbers based on:

- ✅ **Length**: Must be exactly 13 digits.
- 🔢 **Digits Only**: All characters must be numeric.
- 📅 **Birth Date (YYMMDD)**: First 6 digits represent a valid date, including leap year logic.
- 🪪 **Citizenship**: 11th digit must be `0` (South African) or `1` (Permanent resident).
- ✔️ **Checksum (Luhn Algorithm)**: The 13th digit must match the checksum calculated using the Luhn algorithm.

---

## ⚙️ Setup Instructions

### Prerequisites

- Java 17 or later
- Gradle 8.x or later

### Clone the Repository

```bash
git clone https://github.com/yourusername/validate_sa_id.git
cd validate_sa_id
Build and Test
bash
Copy
Edit
gradle build
gradle test
🚀 Usage
java
Copy
Edit
boolean isValid = ValidateSaId.isIdNumberValid("2001014800086");
System.out.println(isValid); // true
🗂 Project Structure
swift
Copy
Edit
validate_sa_id/
├── src/
│   ├── main/java/com/example/
│   │   └── ValidateSaId.java
│   └── test/java/com/example/
│       └── ValidateSaIdTest.java
├── build.gradle
└── README.md
✅ Testing
Tests are located in ValidateSaIdTest.java and include checks for:

✔️ Valid ID numbers

❌ Incorrect lengths (too short/too long)

❌ Non-digit characters

❌ Invalid dates (e.g., Feb 30, leap year handling)

❌ Invalid citizenship digit

❌ Incorrect checksum digit

Run All Tests
bash
Copy
Edit
gradle test
🔁 Development Process
Built using Test-Driven Development (TDD):

🟥 Write a failing test

🟩 Write just enough code to pass

🔁 Refactor and clean up

💾 Commit with meaningful messages

Example commits:

bash
Copy
Edit
git commit -m "Initial project setup with Gradle"
git commit -m "Added valid ID test and basic implementation"
git commit -m "Implemented length validation and tests"
🧮 Checksum Algorithm (Luhn)
To validate the checksum (13th digit), the Luhn algorithm is used:

Sum the digits in the odd positions (excluding the last digit).

Concatenate the digits in even positions, multiply by 2.

Add the digits of the result from step 2.

Add the result of step 1 and 3.

Subtract the final digit of the sum from 10. If result equals the last digit of the ID number, it's valid.

📚 Example:
ID Number: 8001015009087 → ✅ Valid (checksum passes)

🧪 More Validation Examples

ID Number	Valid?	Reason
2001014800086	✅	Correct format and checksum
2001014800089	❌	Invalid checksum
0002295800082	✅	Valid leap year (29 Feb 2000)
9913315800086	❌	Invalid date (31 Nov doesn't exist)
200101A800086	❌	Non-digit character present
20010148000867	❌	Too many digits
200101480008	❌	Too few digits
2001014800186	❌	Invalid citizenship digit (should be 0 or 1)
📜 License
MIT License © 2025 [Your Name]

📝 Notes
Main Class: The default App.java file created by gradle init is unused and can be deleted.

GitHub Setup:

bash
Copy
Edit
git init
git add .
git commit -m "Initial commit"
git remote add origin https://github.com/yourusername/validate_sa_id.git
git push -u origin main
Replace yourusername and [Your Name] with your actual GitHub username and full name.

Happy coding! 🇿🇦

kotlin
Copy
Edit

Would you like this turned into an actual GitHub repository structure or zipped file too?







