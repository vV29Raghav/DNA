package Work;
import DNA_Menu.DNASequence;
import Database.db;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.*;
//import Work.USERcan;

public class ADMINcan {
    // static Scanner in=new Scanner(System.in); // Removed unsafe scanner
    static Deque<DNASequence> undo=new ArrayDeque<>();

    public static void adminuse(){
        // Scanner in=new Scanner(System.in); // Removed unsafe scanner
        System.out.println("ADMIN_Menu\n 1 adding in database\n 2 for delete in database \n 3 for update in database \n 4 for matching strings using id \n 5 list all database details \n 6 list all user \n 7 for list detail of particular patient \n 8 for delete User \n 9 9 for exiting the program \n 10 fro undo last command");

        while(true){
            int x = DNA_Menu.SafeInput.readInt("Choose Option: ");
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
        int id;
        while(true) {
            id = DNA_Menu.SafeInput.readInt("Enter patient id: ");
            if (DNA_Menu.Validation.isValidId(id)) break;
            System.out.println("Invalid ID.");
        }
        
        String name;
        while(true) {
             name = DNA_Menu.SafeInput.readString("Enter patient name: ");
             if(DNA_Menu.Validation.isValidName(name)) break;
             System.out.println("Invalid name.");
        }
        
        String path;
        while(true) {
            path = DNA_Menu.SafeInput.readString("Enter file path of DNA sequence: ");
            if (DNA_Menu.Validation.isValidFilePath(path)) break;
            System.out.println("File does not exist or is invalid.");
        }

        try (BufferedReader br = new BufferedReader(new FileReader(path))){
            StringBuilder sb=new StringBuilder();
            String line;
            while ((line = br.readLine()) != null) {
                sb.append(line.trim().toUpperCase());
            }
            String sequence=sb.toString();
            
            if (!DNA_Menu.Validation.isValidDNA(sequence)) {
                System.out.println("Error: File contains invalid characters. Only A, T, C, G allowed.");
                return;
            }
            
            DNASequence patient = new DNASequence(id,name,sequence);
            db.insert(patient);
            System.out.println("Patient added into memory.");
        } catch (Exception e) {
            System.out.println("Error loading file");
        }

    }

    public static void deletePatient() {
        int id = DNA_Menu.SafeInput.readInt("Enter Patient ID to delete: ");
        DNASequence patient=db.getpatinetbyid(id);
        if(patient!=null){
            undo.push(patient);
            db.del(id);
            System.out.println("Patient deleted");
        }
    }

    public static void updatePatientDNA() {
        int id = DNA_Menu.SafeInput.readInt("Enter Patient ID to update DNA: ");
        DNASequence oldPatient = db.getpatinetbyid(id);
        if(oldPatient!=null){
            undo.push(oldPatient);
            
            String path;
            while(true) {
                path = DNA_Menu.SafeInput.readString("Enter file path of new DNA sequence: "); // CHANGED to file path as per req
                if (DNA_Menu.Validation.isValidFilePath(path)) break;
                System.out.println("File does not exist.");
            }
            
            try (BufferedReader br = new BufferedReader(new FileReader(path))) {
                StringBuilder sb = new StringBuilder();
                String line;
                while ((line = br.readLine()) != null) {
                    sb.append(line.trim().toUpperCase());
                }
                String sequence = sb.toString();
                 if (!DNA_Menu.Validation.isValidDNA(sequence)) {
                    System.out.println("Invalid DNA in file.");
                    return;
                }
                db.updateDNASequence(id, sequence);
                System.out.println("DNA updated");
            } catch (Exception e) {
                 System.out.println("Error reading file.");
            }
         }
        else{
            System.out.println("patient not found");
        }
    }

    public static void matchDNA() {
        System.out.println("Enter Patient ID1 and ID2 for DNA Match: ");
        int id1 = DNA_Menu.SafeInput.readInt("ID 1: ");
        int id2 = DNA_Menu.SafeInput.readInt("ID 2: ");
        db.matchDNA(id1,id2);
    }

    public static void listPatient() {
        db.readPatient();
    }

    public static void listAllUsers() {
        db.readAllUsers();
    }

    public static void showUser() {
        int id = DNA_Menu.SafeInput.readInt("Enter User ID: ");
        db.readUser(id);
    }

    public static void deleteUser() {
        int id = DNA_Menu.SafeInput.readInt("Enter User ID to delete: ");
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
