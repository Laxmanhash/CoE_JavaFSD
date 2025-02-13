package week3.FeeReportSoftware.dao;

import week3.FeeReportSoftware.models.Student;
import week3.FeeReportSoftware.utils.DbConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AccountantDAO {

    // Add new student
    public boolean addStudent(Student student) {
        String query = "INSERT INTO student (name, email, course, fee, paid, due, address, phone) VALUES (?,?,?,?,?,?,?,?)";

        try (Connection conn = DbConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {

            ps.setString(1, student.getName());
            ps.setString(2, student.getEmail());
            ps.setString(3, student.getCourse());
            ps.setDouble(4, student.getFee());
            ps.setDouble(5, student.getPaid());
            ps.setDouble(6, student.getDue());
            ps.setString(7, student.getAddress());
            ps.setString(8, student.getPhone());

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // View all students
    public List<Student> viewAllStudents() {
        List<Student> students = new ArrayList<>();
        String query = "SELECT * FROM student";

        try (Connection conn = DbConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(query);
             ResultSet res = ps.executeQuery()) {

            while (res.next()) {
                students.add(new Student(
                        res.getInt("id"),
                        res.getString("name"),
                        res.getString("email"),
                        res.getString("course"),
                        res.getDouble("fee"),
                        res.getDouble("paid"),
                        res.getDouble("due"),
                        res.getString("address"),
                        res.getString("phone")
                ));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return students;
    }

    public boolean editStudent(int id, String name, String course, double fee, double paid, double due, String address, String phone) {
        String query = "UPDATE student SET name = ?, course = ?, fee = ?, paid = ?, due = ?, address = ?, phone = ? WHERE id = ?";

        try (Connection conn = DbConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {

            ps.setString(1, name);
            ps.setString(2, course);
            ps.setDouble(3, fee);
            ps.setDouble(4, paid);
            ps.setDouble(5, due);
            ps.setString(6, address);
            ps.setString(7, phone);
            ps.setInt(8, id);

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Delete student record
    public boolean deleteStudent(int id) {
        String query = "DELETE FROM student WHERE id = ?";

        try (Connection conn = DbConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {

            ps.setInt(1, id);
            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // View students with pending fee payments
    public List<Student> viewPendingFees() {
        List<Student> students = new ArrayList<>();
        String query = "SELECT * FROM student WHERE due > 0";

        try (Connection conn = DbConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(query);
             ResultSet res = ps.executeQuery()) {

            while (res.next()) {
                students.add(new Student(
                        res.getInt("id"),
                        res.getString("name"),
                        res.getString("email"),
                        res.getString("course"),
                        res.getDouble("fee"),
                        res.getDouble("paid"),
                        res.getDouble("due"),
                        res.getString("address"),
                        res.getString("phone")
                ));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return students;
    }

    // Logout method (dummy implementation)
    public void logout() {
        System.out.println("Accountant logged out successfully.");
    }
}
