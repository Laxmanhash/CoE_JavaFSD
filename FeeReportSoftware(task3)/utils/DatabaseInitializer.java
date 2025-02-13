package week3.FeeReportSoftware.utils;


import java.sql.Connection;
import java.sql.Statement;

public class DatabaseInitializer {
    public static void initialize() {
        try (Connection conn = DbConnection.getConnection();
             Statement stmt = conn.createStatement()) {

            // Create Admin Table
            String createAdminTable = "CREATE TABLE IF NOT EXISTS admin (" +
                    "id INT PRIMARY KEY AUTO_INCREMENT, " +
                    "username VARCHAR(50) NOT NULL UNIQUE, " +
                    "password VARCHAR(255) NOT NULL)";
            stmt.executeUpdate(createAdminTable);

            // Create Accountant Table
            String createAccountantTable = "CREATE TABLE IF NOT EXISTS accountant (" +
                    "id INT PRIMARY KEY AUTO_INCREMENT, " +
                    "name VARCHAR(100) NOT NULL, " +
                    "email VARCHAR(100) NOT NULL UNIQUE, " +
                    "phone VARCHAR(15) NOT NULL, " +
                    "password VARCHAR(255) NOT NULL)";
            stmt.executeUpdate(createAccountantTable);

            // Create Student Table
            String createStudentTable = "CREATE TABLE IF NOT EXISTS student (" +
                    "id INT PRIMARY KEY AUTO_INCREMENT, " +
                    "name VARCHAR(100) NOT NULL, " +
                    "email VARCHAR(100) NOT NULL UNIQUE, " +
                    "course VARCHAR(100) NOT NULL, " +
                    "fee DOUBLE NOT NULL, " +
                    "paid DOUBLE NOT NULL, " +
                    "due DOUBLE NOT NULL, " +
                    "address TEXT NOT NULL, " +
                    "phone VARCHAR(15) NOT NULL)";
            stmt.executeUpdate(createStudentTable);

            System.out.println("Database initialized successfully.");

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error initializing database.");
        }
    }
}
