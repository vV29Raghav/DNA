package Register_Login;

import Database.db;

import java.util.Scanner;

public class Login {
    private String username;
    private String password;
    public Login(){
        username = DNA_Menu.SafeInput.readString("Enter worker name: ");
        password = DNA_Menu.SafeInput.readString("Enter your password: ");
        
        if (username.isEmpty() || password.isEmpty()) {
            System.out.println("Username and password cannot be empty.");
            return;
        }
        
        db.login(username,password);
    }

}
