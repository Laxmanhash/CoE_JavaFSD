package week3.FeeReportSoftware.utils;

import week3.FeeReportSoftware.customExceptions.DbExpection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DbConnection {

    public static String url = "jdbc:mysql://localhost:3306/db";

    public static Connection getConnection() throws SQLException {
        try {
            return DriverManager.getConnection(url, "root", "password");
        } catch (SQLException e) {
            throw new DbExpection("Database connection failed.", e);
        }
    }


}
