package DNA_Menu;

import java.util.Scanner;

public class SafeInput {
    private static final Scanner scanner = new Scanner(System.in);

    public static int readInt(String prompt) {
        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine();
            try {
                return Integer.parseInt(input.trim());
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a valid integer.");
            }
        }
    }

    public static String readString(String prompt) {
        System.out.print(prompt);
        return scanner.nextLine().trim();
    }
    
    // For cases where we just want a line but might be empty (though readString trims)
    public static String readLine(String prompt) {
        System.out.print(prompt);
        return scanner.nextLine();
    }
}
