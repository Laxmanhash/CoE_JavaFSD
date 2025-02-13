package week3.FeeReportSoftware.service;

import week3.FeeReportSoftware.customExceptions.ValidationException;
import week3.FeeReportSoftware.dao.AccountantDAO;
import week3.FeeReportSoftware.models.Student;

import java.util.List;

public class AccountantService {
    private final AccountantDAO accountantDAO;

    public AccountantService() {
        this.accountantDAO = new AccountantDAO();
    }

    // Add Student with Validation
    public boolean addStudent(Student student) throws ValidationException {
        if (student.getName().isEmpty() || student.getEmail().isEmpty()) {
            throw new ValidationException("Name and Email cannot be empty.");
        }
        if (student.getFee() < student.getPaid()) {
            throw new ValidationException("Paid amount cannot exceed total fee.");
        }
        return accountantDAO.addStudent(student);
    }

    // View All Students
    public List<Student> viewAllStudents() {
        return accountantDAO.viewAllStudents();
    }

    // Edit Student
    public boolean editStudent(int id, String name, String course, double fee, double paid, double due, String address, String phone) throws ValidationException {
        if (paid > fee) {
            throw new ValidationException("Paid amount cannot exceed the total fee.");
        }
        return accountantDAO.editStudent(id, name, course, fee, paid, due, address, phone);
    }

    // Delete Student
    public boolean deleteStudent(int id) {
        return accountantDAO.deleteStudent(id);
    }

    // View Pending Fees
    public List<Student> viewPendingFees() {
        return accountantDAO.viewPendingFees();
    }

    // Logout (Dummy Implementation)
    public void logout() {
        accountantDAO.logout();
    }
}
