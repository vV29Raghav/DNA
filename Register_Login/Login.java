package Register_Login;

import Database.db;

import java.util.Scanner;

public class Login {
    private String username;
    private String password;
    public Login(){
        Scanner in=new Scanner(System.in);
        System.out.print("Enter worker name: ");
        username = in.nextLine();
        System.out.print("Enter your password: ");
        password = in.nextLine();
        String hashedPassword = DNA_Menu.Validation.hashPassword(password);
        db.login(username,hashedPassword);

    }

}
