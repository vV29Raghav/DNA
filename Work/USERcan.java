package Work;

import java.io.BufferedReader;
import java.io.*;
import java.sql.*;
import java.lang.foreign.PaddingLayout;
import java.util.ArrayList;
import DNA_Menu.DNASequence;
import java.util.*;

import Database.db;
import Work.ADMINcan;

public class USERcan {
    static Scanner in=new Scanner(System.in);
    static HashMap<Integer, DNASequence> patientMap = new HashMap<>(); // Key: patient id

    public static void use(){
        while(true){
            System.out.println("Choose\n 1 Add patient from File  \n 2 Delete recent patinet details \n 3 Update details of Patient \n 4 List patient details \n 5 For adding all data in DB \n 6 for list particular person \n 7 for exit");
        int x=in.nextInt();

            switch (x){
                case 1:
                    addinMap();
                    break;
                case 2:
                    System.out.print("Enter patient ID to delete: ");
                    int idtodelete = in.nextInt();
                    delete(idtodelete);
                    break;
                case 3:
                    System.out.println("You can only change name of patient so enter patinet id");
                    //find the patient using patinet id
                    int i=in.nextInt();
                    System.out.println("Enter new name");
                    String newname=in.nextLine();
                    update(i,newname);
                    break;
                case 4:
                    list_recent_patients();
                    break;
                case 5:
                    db.insert(patientMap);
                    break;
                case 6:
                    System.out.println("Input patinet id");
                    int b=in.nextInt();
                    list_particualarPatient(b);
                    break;
                case 7:
                    System.out.println("Exit");
                    return;
                default:
                    System.out.println("Please input right value");
                    break;
            }
            if(x==7){
                break;
            }
        }
    }


    //BufferReading system and add in Map
    public static void addinMap() {
            in.nextLine();
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
                patientMap.put(id,patient);
                System.out.println("Patient added into memory.");
            } catch (Exception e) {
                System.out.println("Error loading file");
            }
    }

    //search for person by id using getId in DNASequence and set the new name.
    public static void update(int id,String name){
        in.nextLine();
        DNASequence dna = patientMap.get(id);
        if (dna != null) {
            dna.setpersonname(name);
            System.out.println("Patient name updated.");
        } else {
            System.out.println("Patient not found.");
        }
    }

    //Print all patients in hashmap
    public static void list_recent_patients(){
        for(DNASequence dna: patientMap.values()){
            System.out.println(dna);
        }
    }

    //find person by id by using same getId function from DNASequence and print his details by calling toString function
    public static void list_particualarPatient(int id){
        DNASequence dna = patientMap.get(id);
        if (dna != null) {
            System.out.println(dna);
        } else {
            System.out.println("Patient not found.");
        }
    }

    //find the person by id
    public static void delete(int id){
        if (patientMap.remove(id) != null) {
            System.out.println("Patient deleted.");
        } else {
            System.out.println("Patient not found.");
        }
    }
}
