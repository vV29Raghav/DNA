import CLI_GUI.Command_Line_Interface;
import CLI_GUI.Graphical_User_Interface;

import java.util.Scanner;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        System.out.println(" Input G for Graphical User Interface \n Input C for Command Line Interface");
        Scanner in=new Scanner(System.in);
        Character a=in.nextLine().charAt(0);
        while(true){
            if (a == 'G') {
                Graphical_User_Interface gui = new Graphical_User_Interface();
                break;
            } else if (a == 'C') {
                Command_Line_Interface cli = new Command_Line_Interface();
                break;
            } else {
                System.out.println("Enter C or G for proper Functioning");
            }
        }
    }

}