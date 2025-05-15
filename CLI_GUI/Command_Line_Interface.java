package CLI_GUI;
import java.util.*;
import Register_Login.Login;
import Register_Login.Register;
import Work.USERcan;

import java.util.Scanner;

public class Command_Line_Interface {
    public Command_Line_Interface(){
        Scanner in=new Scanner(System.in);
        System.out.println("Enter 1 for Register and 2 for Login");


        while(true){
            int x=in.nextInt();
            in.nextLine();
            switch (x) {
                case 1:
                    Register s = new Register();
                    USERcan.use();
                    break;
                case 2:
                    Login l = new Login();
                    break;
                default:
                    System.out.println("Add proper input");
                    break;
            }
        }
    }
}
