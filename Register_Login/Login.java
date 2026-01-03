package Register_Login;

import Database.db;

import java.util.Scanner;

public class Login {
    private String username;
    private String password;
    public Login(){
<<<<<<< HEAD
        Scanner in=new Scanner(System.in);
        System.out.print("Enter worker name: ");
        username = in.nextLine();
        System.out.print("Enter your password: ");
        password = in.nextLine();
        String hashedPassword = DNA_Menu.Validation.hashPassword(password);
        db.login(username,hashedPassword);

=======
        username = DNA_Menu.SafeInput.readString("Enter worker name: ");
        password = DNA_Menu.SafeInput.readString("Enter your password: ");
        
        if (username.isEmpty() || password.isEmpty()) {
            System.out.println("Username and password cannot be empty.");
            return;
        }
        
        db.login(username,password);
>>>>>>> 61887fc314c6067e6d32061efdb6109defaf8335
    }

}
