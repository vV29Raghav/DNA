package Database;
import CLI_GUI.Graphical_User_Interface;
import DNA_Menu.DNASequence;
import Work.ADMINcan;
import Work.USERcan;

import javax.swing.*;
import java.time.*;
import java.util.*;
import java.sql.*;
import java.sql.Date;

public class db {
    private static final String URL = "jdbc:mysql://localhost:3306/dna";
    private static final String USER = "root";
    private static final String PASSWORD = "Mapple28!";

    //Prepare connection with database
    private static Connection getConnection() {
            try {
                return  DriverManager.getConnection(URL, USER, PASSWORD);
            }
            catch (SQLException e) {
                e.printStackTrace();
                return null;
            }
    }



//<-------------------------------------------------UserWork-------------------------------------------------------->



    //Insert Whole map data in Database
    public static void insert(Map<Integer, DNASequence> patientMap){
        String insertSQL = "INSERT INTO dna_sequences (id, sequence, date, time, person_name) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(insertSQL)) {

            for (DNASequence dna : patientMap.values()) {
                pstmt.setInt(1, dna.getId());
                pstmt.setString(2, dna.getSequence());
                pstmt.setDate(3, dna.getDate());
                pstmt.setTime(4, dna.getTime());
                pstmt.setString(5, dna.getPersonName());
                pstmt.executeUpdate();
            }
            System.out.println("All records inserted to DB.");
            patientMap.clear();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }



//<-------------------------------------------------AdminWork------------------------------------------------------>



    //Insertion by Admin
    public static void insert(DNASequence dna){
        String insertSQL = "INSERT INTO dna_sequences (id, sequence, date, time, person_name) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = getConnection(); PreparedStatement pstmt = conn.prepareStatement(insertSQL)) {
            pstmt.setInt(1, dna.getId());
            pstmt.setString(2, dna.getSequence());
            pstmt.setDate(3, dna.getDate());
            pstmt.setTime(4, dna.getTime());
            pstmt.setString(5, dna.getPersonName());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    //Delete patient by id done by admin
    public static void del(int id) {
        String query = "DELETE FROM dna_sequences WHERE id = ?";
        try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    //Update patinet sequence by admin using id
    public static void updateDNASequence(int id, String sequence) {
        String query = "UPDATE dna_sequences SET sequence = ? WHERE id = ?";
        try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, sequence);
            stmt.setInt(2, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    //Read all patients by admin
    public static void readPatient() {
        String query = "SELECT * FROM dna_sequences";
        try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(query)) {
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                    System.out.println("ID: " + rs.getInt("id"));
                    System.out.println("Name: " + rs.getString("person_name"));
                    System.out.println("DNA Sequence: " + rs.getString("sequence"));
                    System.out.println("Date: " + rs.getDate("date"));
                    System.out.println("Time: " + rs.getTime("time"));
                    System.out.println("-----------------------------");
            }
        }
        catch (SQLException e) {
                e.printStackTrace();
        }
    }


    //Delete User by id by admin
    public static void deleteUser(int id) {
        String deleteQuery = "DELETE FROM users WHERE id = ?";
        try (Connection conn = getConnection(); PreparedStatement pstmt = conn.prepareStatement(deleteQuery)) {
            pstmt.setInt(1, id);
            int rows = pstmt.executeUpdate();
            if (rows > 0) {
                System.out.println("User deleted successfully.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    //Read Particular user by admin
    public static void readUser(int id) {
        String selectQuery = "SELECT * FROM users WHERE id = ?";
        try (Connection conn = getConnection(); PreparedStatement pstmt = conn.prepareStatement(selectQuery)) {
            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                System.out.println("ID: " + rs.getInt("id"));
                System.out.println("Username: " + rs.getString("username"));
                System.out.println("Password: " + rs.getString("password"));
            } else {
                System.out.println("No user found with ID: " + id);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    //get patient by admin using id
    public static DNASequence getpatinetbyid(int id){
        DNASequence patient=null;
        String query = "SELECT * FROM dna_sequences WHERE id = ?";
        try(Connection conn = getConnection(); PreparedStatement pstmt = conn.prepareStatement(query)){
            pstmt.setInt(1, id);
            ResultSet rs=pstmt.executeQuery();
            if(rs.next()){
                String name=rs.getString("person_name");
                String sequence=rs.getString("sequence");
                patient=new DNASequence(id,name,sequence);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return patient;
    }


    //Read all user by admin
    public static void readAllUsers() {
        String selectQuery = "SELECT * FROM users";
        try (Connection conn = getConnection(); Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(selectQuery)) {
            while (rs.next()) {
                System.out.println("ID: " + rs.getInt("id") +
                        ", Username: " + rs.getString("username") +
                        ", Password: " + rs.getString("password"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    //Match DNA by admin only
    public static void matchDNA(int id1,int id2) {
        String query = "SELECT sequence FROM dna_sequences WHERE id = ?";
        String seq1 = null;
        String seq2 = null;
        try (Connection conn = getConnection()) {
            try (PreparedStatement stmt1 = conn.prepareStatement(query)) {
                stmt1.setInt(1, id1);
                ResultSet rs1 = stmt1.executeQuery();
                if (rs1.next()) {
                    seq1 = rs1.getString("sequence");
                    System.out.println("Sequence for ID " + id1 + ": " + seq1);
                } else {
                    System.out.println("No sequence found for ID " + id1);
                    return;
                }
            }
            try (PreparedStatement stmt2 = conn.prepareStatement(query)) {
                stmt2.setInt(1, id2);
                ResultSet rs2 = stmt2.executeQuery();
                if (rs2.next()) {
                    seq2 = rs2.getString("sequence");
                    System.out.println("Sequence for ID " + id2 + ": " + seq2);
                } else {
                    System.out.println("No sequence found for ID " + id2);
                    return;
                }
            }
            int minLen = Math.min(seq1.length(), seq2.length());
            int matches = 0;

            // Loop through both sequences and compare each character
            for (int i = 0; i < minLen; i++) {
                if (seq1.charAt(i) == seq2.charAt(i)) {
                    matches++;
                }
            }
            double matchPercentage = (double) matches / minLen * 100;
            System.out.println(matchPercentage);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }



//<-------------------------------------------------------Register_Login--------------------------------------------->



    //Register function called this insert to first initialize the connection with database then add the data to databse
    public static void insert(String username,String password,int id) {
        String insertQuery = "INSERT INTO users (username, password,id) VALUES (?, ?, ?)";
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(insertQuery)){
            pstmt.setString(1,username);
            pstmt.setString(2,password);
            pstmt.setInt(3,id);
            int rows=pstmt.executeUpdate();
            if (rows > 0) {
                System.out.println("Record inserted successfully.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //Called by login class after identifying user or admin
    public static void login(String username, String password) {
        if (username.equals("raghav") && password.equals("fa11RAGHAV")) {
            System.out.println("Welcome Admin Raghav! Full permissions granted.");
            ADMINcan.adminuse();
        }
        else{
            String loginQuery = "SELECT * FROM users WHERE username = ? AND password = ?";

            try (Connection conn = getConnection();
                 PreparedStatement pstmt = conn.prepareStatement(loginQuery)) {

                pstmt.setString(1, username);
                pstmt.setString(2, password);

                ResultSet rs = pstmt.executeQuery();

                if (rs.next()) {
                    System.out.println("Welcome " + username + "! User-level permissions granted.");
                    USERcan user = new USERcan();
                    user.use();
                }
                else {
                    System.out.println("User not found in the database.");
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

    }



//<---------------------------------------Graphical_User_Interface----------------------------------------------------->



    //Add patient to database (GUI)
    public static void addPatient(int id, String name, String sequence) {
        String insertSQL = "INSERT INTO dna_sequences (id, sequence, person_name) VALUES (?, ?, ?)";
        try (Connection conn = getConnection(); PreparedStatement pstmt = conn.prepareStatement(insertSQL)) {
            pstmt.setInt(1, id);
            pstmt.setString(2, sequence);
            pstmt.setString(3, name);
            pstmt.executeUpdate();
            JOptionPane.showMessageDialog(null, "DNA Sequence Added!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    // Method to update a DNA sequence(GUI)
    public static void updatePatient(int id, String newSequence) {
        String updateSQL = "UPDATE dna_sequences SET sequence = ? WHERE id = ?";
        try (Connection conn = getConnection(); PreparedStatement pstmt = conn.prepareStatement(updateSQL)) {
            pstmt.setString(1, newSequence);
            pstmt.setInt(2, id);
            pstmt.executeUpdate();
            JOptionPane.showMessageDialog(null, "DNA Sequence Updated!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    // Method to delete a DNA sequence(GUI)
    public static void deletePatient(int id) {
        String deleteSQL = "DELETE FROM dna_sequences WHERE id = ?";
        try (Connection conn = getConnection(); PreparedStatement pstmt = conn.prepareStatement(deleteSQL)) {
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
            JOptionPane.showMessageDialog(null, "DNA Sequence Deleted!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    // Method to list all DNA sequences(GUI)
    public static void listAllPatients() {
        String query = "SELECT * FROM dna_sequences";
        try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(query)) {
            ResultSet rs = stmt.executeQuery();
            StringBuilder sb = new StringBuilder();
            while (rs.next()) {
                sb.append("ID: ").append(rs.getInt("id"))
                        .append(", Name: ").append(rs.getString("person_name"))
                        .append(", Sequence: ").append(rs.getString("sequence"))
                        .append("\n-----------------------------\n");
            }
            JTextArea textArea = new JTextArea(20, 50);
            textArea.setText(sb.toString());
            textArea.setEditable(false);
            JOptionPane.showMessageDialog(null, new JScrollPane(textArea), "All DNA Sequences", JOptionPane.PLAIN_MESSAGE);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


}
