package week3.FeeReportSoftware.service;


import week3.FeeReportSoftware.customExceptions.ValidationException;
import week3.FeeReportSoftware.dao.AdminDAO;
import week3.FeeReportSoftware.models.Accountant;

import java.util.List;

public class AdminService {
    private final AdminDAO adminDAO;

    public AdminService() {
        this.adminDAO = new AdminDAO();
    }

    // Add Accountant with validation
    public boolean addAccountant(Accountant accountant) throws ValidationException {
        if (accountant.getName().isEmpty() || accountant.getEmail().isEmpty()) {
            throw new ValidationException("Name and Email cannot be empty.");
        }
        if (!accountant.getEmail().matches("^[A-Za-z0-9+_.-]+@[a-zA-Z0-9.-]+$")) {
            throw new ValidationException("Invalid email format.");
        }
        if (accountant.getPhone().length() != 10) {
            throw new ValidationException("Phone number must be 10 digits.");
        }
        return adminDAO.addAccountant(accountant);
    }

    // View all accountants
    public List<Accountant> viewAllAccountants() {
        return adminDAO.viewAllAccountant();
    }

    // Edit Accountant details
    public boolean editAccountant(String email, String newName, String newPhoneNum) throws ValidationException {
        if (newPhoneNum.length() != 10) {
            throw new ValidationException("Phone number must be 10 digits.");
        }
        return adminDAO.editAccountant(email, newName, newPhoneNum);
    }

    // Delete Accountant
    public boolean deleteAccountant(String email) {
        return adminDAO.deleteAccountant(email);
    }

    // Logout
    public void logout() {
        adminDAO.logout();
    }
}
