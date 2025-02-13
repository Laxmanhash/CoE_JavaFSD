package week3.FeeReportSoftware.dao;

import week3.FeeReportSoftware.models.Accountant;
import week3.FeeReportSoftware.utils.DbConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AdminDAO {
    // create table
    public static final String CREATE_ADMIN_TABLE = "CREATE TABLE IF NOT EXISTS admin ("
            + "id INT PRIMARY KEY AUTO_INCREMENT,"
            + "username VARCHAR(50) NOT NULL UNIQUE,"
            + "password VARCHAR(255) NOT NULL)";

    //add new accountants
    public boolean addAccountant(Accountant accountant) {
        String query = "INSERT INTO accountant (name, email, phone, password) VALUES (?,?,?,?);";

        try {
            Connection conn = DbConnection.getConnection();
            PreparedStatement ps = conn.prepareStatement(query);

            ps.setString(1, accountant.getName());
            ps.setString(2, accountant.getEmail());
            ps.setString(3, accountant.getPhone());
            ps.setString(4, accountant.getPassword());

            int insertedCount = ps.executeUpdate();

            return insertedCount > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    //view existing accountants
    public List<Accountant> viewAllAccountant() {
        List<Accountant> data = new ArrayList<>();
        String query = "SELECT * FROM student;";
        try {
            Connection conn = DbConnection.getConnection();
            PreparedStatement ps = conn.prepareStatement(query);

            ResultSet res = ps.executeQuery();
            if (res.next()) {
                //name,email,ph,password
                data.add(
                        new Accountant(
                        res.getInt("id"),
                        res.getString("name"),
                        res.getString("email"),
                        res.getString("phone"),
                        res.getString("password")
                ));

                return data;

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    //edit accountants by email
    public boolean editAccountant(String email, String newName, String newPhoneNum){
        String query = "UPDATE accountant SET name = ?, phone = ? WHERE email = ?";

        try {
            Connection conn = DbConnection.getConnection();
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setString(3,email);

            ps.setString(1,newName);
            ps.setString(2,newPhoneNum);

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    //delete accountants
    public boolean deleteAccountant(String email) {
        String query = "DELETE FROM accountant WHERE email = ?";

        try (Connection conn = DbConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {

            ps.setString(1, email);
            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    //logout
    public void logout() {
        System.out.println("Admin logged out successfully.");
    }

}
