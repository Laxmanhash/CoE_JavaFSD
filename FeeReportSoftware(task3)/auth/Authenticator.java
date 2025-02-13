package week3.FeeReportSoftware.auth;


import week3.FeeReportSoftware.utils.DbConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Authenticator {
    public boolean authenticateAdmin(String username, String password) {
        String query = "SELECT * FROM admin WHERE username = ? AND password = ?";

        try (Connection conn = DbConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {

            ps.setString(1, username);
            ps.setString(2, password);

            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();  // Returns true if credentials match
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
