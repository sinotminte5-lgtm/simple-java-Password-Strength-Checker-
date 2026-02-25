Password Strength Checker (Java)
Description
A Java console application that evaluates the strength of a password using two key methods:
1. Breach Check
Checks the password against the Have I Been Pwned (HIBP) database to see if it has appeared in known data breaches.
2. Entropy Calculation
Measures the theoretical strength of the password based on its length and character complexity (uppercase, lowercase, digits, symbols).
The program outputs:
* Entropy in bits
* Whether the password has been compromised
* Overall strength grading

Features
* Breach Check: Verifies passwords against the Have I Been Pwned (HIBP) database to see if they have appeared in known data breaches.
* Entropy Calculation: Measures the theoretical strength of passwords based on length and character complexity (uppercase, lowercase, digits, symbols).
* Cryptographically Safe: Uses secure methods for entropy estimation.
*  User-Friendly Console Interface: Simple and interactive CLI for testing password strength.
* Strength Grading: Combines breach status and entropy to provide a final strength grade.
Requirements
* Java 11 or higher (for HttpClient)
* Terminal or Java IDE (IntelliJ, Eclipse, VS Code)
* Internet connection (for real-time breach checks via HIBP API)
Usage
1. Launch the application in your terminal or IDE.
2. Enter the password you want to evaluate.
3. The program will output:
o Entropy in bits
o Breach status
o Overall strength grade


How it Works
1. Breach Check
* Uses HIBP API’s k-anonymity feature.
* Only sends a partial SHA-1 hash of the password for privacy.
* Checks if the password has appeared in known breaches.
2. Entropy Calculation
* Calculates theoretical entropy based on:
o Character types: lowercase, uppercase, digits, symbols
o Password length
* Formula: entropy = log2(possible_combinations)

License
This project is licensed under the MIT License.

Acknowledgements
* Have I Been Pwned API for breach data
* Inspired by best practices in password security and entropy estimation

