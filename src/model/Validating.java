package model;

public class Validating {

    public static boolean isNumber(String input) {
        if (input == null || input.isBlank()) return false;
        for (char c : input.toCharArray()) {
            if (!Character.isDigit(c)) {
                return false;
            }
        }
        return true;
    }

    public static boolean isValidStr(String input) {
        return input != null && !input.trim().isEmpty();
    }
}