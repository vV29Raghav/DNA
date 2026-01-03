package DNA_Menu;

import java.util.regex.Pattern;
import java.io.File;

public class Validation {

    // Validates if DNA sequence only contains A, T, C, G
    public static boolean isValidDNA(String sequence) {
        if (sequence == null || sequence.trim().isEmpty()) {
            return false;
        }
        return sequence.matches("[ATCG]+");
    }

    // Validates ID is positive
    public static boolean isValidId(int id) {
        return id > 0;
    }

    // Validates a name (simple check: not empty, reasonable length)
    public static boolean isValidName(String name) {
        return name != null && !name.trim().isEmpty() && name.length() <= 100;
    }

    // Validates username for registration (e.g., at least 3 chars)
    public static boolean isValidUsername(String username) {
        return username != null && username.trim().length() >= 3;
    }

    // Validates password (e.g., at least 4 chars)
    public static boolean isValidPassword(String password) {
        return password != null && password.trim().length() >= 4;
    }
    
    // Validate file path exists and is a file
    public static boolean isValidFilePath(String path) {
        if (path == null || path.trim().isEmpty()) return false;
        File file = new File(path);
        return file.exists() && file.isFile();
    }
}
