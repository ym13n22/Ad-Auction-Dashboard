# Software Engineering Project Group 16
A financial data analysis and visualization dashboard for managing ad auctions, built using Java and JavaFX.

Developed collaboratively in a team setting using Agile methodology, with regular sprints, code reviews, and task tracking to ensure iterative delivery and continuous improvement.

🔧 Features

Interactive Dashboard: Visualizes ad auction performance, financial metrics, and bidding trends.

Secure User Authentication: Implements salted password hashing for enhanced security.

SQL Database Integration: Stores and queries auction and user data via SQL.

Data Visualization: Presents complex data using dynamic charts and tables via JavaFX.

🧱 Architecture & Design Patterns

Factory Pattern: Used to decouple object instantiation from usage, improving modularity and maintainability.

Singleton Pattern: Ensures a single instance for database connection and configuration management, preventing resource leaks and maintaining consistency.

🛡️ Security

Salted password storage to prevent hash collisions and enhance resistance to brute-force attacks.

Prepared statements for SQL queries to avoid injection vulnerabilities.

⚙️ Compilation

To compile and run the project:

javac -cp . Main.java

java Main

Ensure you have Java 8+ and JavaFX SDK installed and correctly configured.

📂 Tech Stack

Language: Java

GUI: JavaFX

Database: SQL (SQLite)

Patterns: Factory, Singleton
