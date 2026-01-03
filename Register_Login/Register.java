package Register_Login;
import Database.db;
import java.util.Scanner;

public class Register {
    private String username;
    private String password;
    private int id;
    public Register(){
        // Loop for ID
        while (true) {
             id = DNA_Menu.SafeInput.readInt("Enter your id by which you will access your work: ");
             if (DNA_Menu.Validation.isValidId(id)) {
                 break;
             }
             System.out.println("Invalid ID. ID must be a positive integer.");
        }

        // Loop for Username
        while (true) {
            username = DNA_Menu.SafeInput.readString("Enter worker name (min 3 chars): ");
            if (DNA_Menu.Validation.isValidUsername(username)) {
                break;
            }
            System.out.println("Invalid username. Must be at least 3 characters.");
        }
<<<<<<< HEAD
        in.nextLine();
        System.out.print("Enter worker name: ");
        username = in.nextLine();
        System.out.print("Enter password: ");
        password = in.nextLine();
        String hashedPassword = DNA_Menu.Validation.hashPassword(password);
        db.insert(username,hashedPassword,id);
=======

        // Loop for Password
        while (true) {
            password = DNA_Menu.SafeInput.readString("Enter password (min 4 chars): ");
            if (DNA_Menu.Validation.isValidPassword(password)) {
                break;
            }
            System.out.println("Invalid password. Must be at least 4 characters.");
        }

        db.insert(username,password,id);
>>>>>>> 61887fc314c6067e6d32061efdb6109defaf8335
    }
}
