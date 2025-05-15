package Work;
import DNA_Menu.DNASequence;
import Database.db;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.*;
//import Work.USERcan;

public class ADMINcan {
    static Scanner in=new Scanner(System.in);
    static Deque<DNASequence> undo=new ArrayDeque<>();

    public static void adminuse(){
        Scanner in=new Scanner(System.in);
        System.out.println("ADMIN_Menu\n 1 adding in database\n 2 for delete in database \n 3 for update in database \n 4 for matching strings using id \n 5 list all database details \n 6 list all user \n 7 for list detail of particular patient \n 8 for delete User \n 9 9 for exiting the program \n 10 fro undo last command");

        while(true){
            int x=in.nextInt();
            switch (x){
                case 1:
                    addPatient();
                    break;
                case 2:
                    deletePatient();
                    break;
                case 3:
                    updatePatientDNA();
                    break;
                case 4:
                    matchDNA();
                    break;
                case 5:
                    listPatient();
                    break;
                case 6:
                    listAllUsers();
                    break;
                case 7:
                    showUser();
                    break;
                case 8:
                    deleteUser();
                    break;
                case 9:
                    System.out.println("Exiting as admin");
                    return;
                case 10:
                    System.out.println("undo last operation");
                    undoLast();
                    break;
                default:
                    System.out.println("Please input right value");
                    break;
            }
        }
    }
    public static void addPatient() {
        System.out.print("Enter patient id");
        int id = in.nextInt();
        in.nextLine();
        System.out.print("Enter patient name: ");
        String name = in.nextLine();
        System.out.print("Enter file path of DNA sequence: ");
        String path = in.nextLine();

        try (BufferedReader br = new BufferedReader(new FileReader(path))){
            StringBuilder sb=new StringBuilder();
            String line;
            while ((line = br.readLine()) != null) {
                sb.append(line.trim().toUpperCase());
            }
            String sequence=sb.toString();
            DNASequence patient = new DNASequence(id,name,sequence);
            db.insert(patient);
            System.out.println("Patient added into memory.");
        } catch (Exception e) {
            System.out.println("Error loading file");
        }

    }

    public static void deletePatient() {
        System.out.print("Enter Patient ID to delete: ");
        int id = in.nextInt();
        DNASequence patient=db.getpatinetbyid(id);
        if(patient!=null){
            undo.push(patient);
            db.del(id);
            System.out.println("Patient deleted");
        }
    }

    public static void updatePatientDNA() {
        System.out.print("Enter Patient ID to update DNA: ");
        int id = in.nextInt();
        in.nextLine();
        DNASequence oldPatient = db.getpatinetbyid(id);
        if(oldPatient!=null){
            undo.push(oldPatient);
        System.out.print("Enter new DNA Sequence: ");
        String sequence = in.nextLine();
        db.updateDNASequence(id, sequence);
            System.out.println("DNA updated");}
        else{
            System.out.println("patient not found");
        }
    }

    public static void matchDNA() {
        System.out.print("Enter Patient ID1 and ID2 for DNA Match: ");
        int id1 = in.nextInt();
        int id2 = in.nextInt();
        db.matchDNA(id1,id2);
    }

    public static void listPatient() {
        db.readPatient();
    }

    public static void listAllUsers() {
        db.readAllUsers();
    }

    public static void showUser() {
        System.out.print("Enter User ID: ");
        int id = in.nextInt();
        db.readUser(id);
    }

    public static void deleteUser() {
        System.out.print("Enter User ID to delete: ");
        int id = in.nextInt();
        db.deleteUser(id);
    }

    public static void undoLast(){
        if(!undo.isEmpty()){
            DNASequence prev=undo.pop();
            db.del(prev.getId());
            db.insert(prev);
            System.out.println("Undo successfull for patient with id"+prev.getId());
        }
        else{
            System.out.println("Nothing to undo");
        }
    }



}
