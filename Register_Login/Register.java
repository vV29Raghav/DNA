package Register_Login;
import Database.db;
import java.util.Scanner;

public class Register {
    private String username;
    private String password;
    private int id;
    public Register(){
        Scanner in=new Scanner(System.in);
        System.out.print("Enter your id by which you will access your work");
        while (true){
            try {
                id = in.nextInt();
                break;
            } catch (Exception e) {
                System.out.println("Please enter Integer Value");
                break;
            }
        }
        in.nextLine();
        System.out.print("Enter worker name: ");
        username = in.nextLine();
        System.out.print("Enter password: ");
        password = in.nextLine();
        db.insert(username,password,id);
    }
}
