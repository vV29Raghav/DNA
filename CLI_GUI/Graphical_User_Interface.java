package CLI_GUI;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import java.util.*;

import static Database.db.*;

public class Graphical_User_Interface {
    static Scanner in=new Scanner(System.in);
    public Graphical_User_Interface(){
        cases();
    }
    public static void cases(){
        JFrame frame = new JFrame("DNA Sequence Management");
        frame.setSize(400, 300);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new FlowLayout());
        JButton addButton = new JButton("Add DNA");
        frame.add(addButton);
        JButton updateButton = new JButton("Update DNA");
        frame.add(updateButton);
        JButton deleteButton = new JButton("Delete DNA");
        frame.add(deleteButton);
        JButton listButton = new JButton("List DNA Sequences");
        frame.add(listButton);
        addButton.addActionListener(e -> {
            String idStr = JOptionPane.showInputDialog("Enter Patient ID:");
            int id = Integer.parseInt(idStr);
            String name = JOptionPane.showInputDialog("Enter Patient Name:");
            String sequence = JOptionPane.showInputDialog("Enter DNA Sequence:");
            addPatient(id, name, sequence);
        });
        updateButton.addActionListener(e -> {
            String idStr = JOptionPane.showInputDialog("Enter Patient ID to Update:");
            int id = Integer.parseInt(idStr);
            String newSequence = JOptionPane.showInputDialog("Enter New DNA Sequence:");
            updatePatient(id, newSequence);
        });
        deleteButton.addActionListener(e -> {
            String idStr = JOptionPane.showInputDialog("Enter Patient ID to Delete:");
            int id = Integer.parseInt(idStr);
            deletePatient(id);
        });
        listButton.addActionListener(e -> listAllPatients());
        frame.setVisible(true);
    }

}
