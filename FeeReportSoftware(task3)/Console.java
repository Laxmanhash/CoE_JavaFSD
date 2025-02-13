package week3.FeeReportSoftware;

import week3.FeeReportSoftware.auth.Authenticator;
import week3.FeeReportSoftware.customExceptions.ValidationException;
import week3.FeeReportSoftware.models.Accountant;
import week3.FeeReportSoftware.service.AdminService;
import week3.FeeReportSoftware.utils.DatabaseInitializer;
import week3.FeeReportSoftware.utils.DbConnection;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class Console {

    private static void insertSampleData() {
        try (Connection conn = DbConnection.getConnection()) {
            Statement stmt = conn.createStatement();

            String adminCheck = "SELECT COUNT(*) FROM admin";
            ResultSet rs = stmt.executeQuery(adminCheck);
            rs.next();
            if (rs.getInt(1) == 0) {
                String insertAdmin = "INSERT INTO admin (username, password) VALUES ('admin', 'admin123')";
                stmt.executeUpdate(insertAdmin);
            }

            String accountantCheck = "SELECT COUNT(*) FROM accountant";
            rs = stmt.executeQuery(accountantCheck);
            rs.next();
            if (rs.getInt(1) == 0) {
                String insertAccountants = """
                INSERT INTO accountant (name, email, phone, password) VALUES 
                ('John Doe', 'john@example.com', '9876543210', 'pass123'),
                ('Jane Smith', 'jane@example.com', '9876543220', 'secure456');
            """;
                stmt.executeUpdate(insertAccountants);
            }

            String studentCheck = "SELECT COUNT(*) FROM student";
            rs = stmt.executeQuery(studentCheck);
            rs.next();
            if (rs.getInt(1) == 0) {
                String insertStudents = """
                INSERT INTO student (name, email, course, fee, paid, due, address, phone) VALUES 
                ('Alice Smith', 'alice@example.com', 'Computer Science', 50000, 20000, 30000, '123 Main St', '9998887776'),
                ('Bob Johnson', 'bob@example.com', 'Data Science', 60000, 30000, 30000, '456 Park Ave', '8887776665');
            """;
                stmt.executeUpdate(insertStudents);
            }

            System.out.println("Sample data inserted successfully!");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        DatabaseInitializer.initialize();
        insertSampleData();


        Scanner scanner = new Scanner(System.in);
        Authenticator authenticator = new Authenticator();
        AdminService adminService = new AdminService();

        System.out.println("=== Fee Report Software ===");
        System.out.print("Enter Admin Username: ");
        String username = scanner.next();
        System.out.print("Enter Password: ");
        String password = scanner.next();

        if (!authenticator.authenticateAdmin(username, password)) {
            System.out.println("Invalid credentials! Exiting...");
            return;
        }

        while (true) {
            System.out.println("\n--- Admin Dashboard ---");
            System.out.println("1. Add Accountant");
            System.out.println("2. View All Accountants");
            System.out.println("3. Edit Accountant");
            System.out.println("4. Delete Accountant");
            System.out.println("5. Logout");
            System.out.print("Choose an option: ");

            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    System.out.print("Enter Accountant Name: ");
                    String name = scanner.nextLine();
                    System.out.print("Enter Email: ");
                    String email = scanner.next();
                    System.out.print("Enter Phone Number: ");
                    String phone = scanner.next();
                    System.out.print("Enter Password: ");
                    String accPassword = scanner.next();

                    Accountant accountant = new Accountant(0, name, email, phone, accPassword);
                    try {
                        if (adminService.addAccountant(accountant)) {
                            System.out.println("Accountant added successfully!");
                        } else {
                            System.out.println("Failed to add accountant.");
                        }
                    } catch (ValidationException e) {
                        System.out.println("Error: " + e.getMessage());
                    }
                    break;

                case 2:
                    System.out.println("\n--- List of Accountants ---");

                    System.out.println("Accountants Details: ");
                    for(Accountant acc:adminService.viewAllAccountants() ){
                        System.out.println(acc);
                    }
                    break;

                case 3:
                    System.out.print("Enter Accountant Email to Edit: ");
                    String editEmail = scanner.next();
                    System.out.print("Enter New Name: ");
                    String newName = scanner.next();
                    System.out.print("Enter New Phone Number: ");
                    String newPhone = scanner.next();

                    try {
                        if (adminService.editAccountant(editEmail, newName, newPhone)) {
                            System.out.println("Accountant details updated successfully.");
                        } else {
                            System.out.println("Failed to update accountant details.");
                        }
                    } catch (ValidationException e) {
                        System.out.println("Error: " + e.getMessage());
                    }
                    break;

                case 4:
                    System.out.print("Enter Accountant Email to Delete: ");
                    String deleteEmail = scanner.next();
                    if (adminService.deleteAccountant(deleteEmail)) {
                        System.out.println("Accountant deleted successfully.");
                    } else {
                        System.out.println("Failed to delete accountant.");
                    }
                    break;

                case 5:
                    System.out.println("Logging out...");
                    adminService.logout();
                    return;

                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }
}
