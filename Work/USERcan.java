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
    static HashMap<String,DNASequence> pateintMap=new HashMap<>();

    public static void use(){
        while(true){ System.out.println("Choose\n 1 Add patient from File  \n 2 Delete recent patinet details \n 3 Update details of Patient \n 4 List patient details \n 5 For adding all data in DB \n 6 for list particular person \n 7 for exit");
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
                    db.insert(pateintMap);
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
                pateintMap.put(sequence,patient);
                System.out.println("Patient added into memory.");
            } catch (Exception e) {
                System.out.println("Error loading file");
            }
    }
    public static void update(int id,String name){
        in.nextLine();
        for (DNASequence dna : pateintMap.values()) {
            if(dna.getId()==id){
                dna.setpersonname(name);
                System.out.println("Updated name");
                return;
            }
        }
        System.out.println("Patient not found");
    }
    public static void list_recent_patients(){
        for(DNASequence dna: pateintMap.values()){
            System.out.println(dna.toString());
        }
    }
    public static void list_particualarPatient(int id){
    for(DNASequence dna:pateintMap.values()){
        if(dna.getId()==id){
            System.out.println(dna);
            return;
        }
    }
        System.out.println("Patient not found");
    }
    public static void delete(int id){
        String remove=null;
        for (Map.Entry<String, DNASequence> entry : pateintMap.entrySet()) {
            if (entry.getValue().getId() == id) {
                remove = entry.getKey();
                break;
            }
        }
        if (remove!=null){
            pateintMap.remove(remove);
            System.out.println("patinet deleted");
        }
        else {
            System.out.println("patinet not found");
        }
    }
}
