package week3.FeeReportSoftware.utils;


public class ValidationUtil {

    public static boolean isValidEmail(String email) {
        return email != null && email.matches("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$");
    }

    public static boolean isValidPhone(String phone) {
        return phone != null && phone.matches("\\d{10}");
    }

    public static boolean isValidFee(double fee, double paid) {
        return fee >= 0 && paid >= 0 && paid <= fee;
    }
}
