package CLI_GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import java.util.*;
import java.io.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import DNA_Menu.Validation;

import static Database.db.*;

/**
 * A modern, premium Graphical User Interface for DNA Sequence Management.
 * Uses a sidebar navigation and CardLayout for a smooth user experience.
 */
public class Graphical_User_Interface {
    private JFrame frame;
    private JPanel contentPanel;
    private CardLayout cardLayout;

    // Custom Colors for Premium Feel
    private final Color SIDEBAR_COLOR = new Color(45, 52, 71);
    private final Color BUTTON_HOVER_COLOR = new Color(60, 70, 95);
    private final Color TEXT_COLOR = Color.WHITE;
    private final Font MAIN_FONT = new Font("Segoe UI", Font.PLAIN, 14);
    private final Font HEADER_FONT = new Font("Segoe UI", Font.BOLD, 18);

    public Graphical_User_Interface() {
        try {
            // Set Nimbus Look and Feel for a more modern native look
            for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (Exception e) {
            // Fallback to default if Nimbus isn't available
        }
        initializeUI();
    }

    private void initializeUI() {
        frame = new JFrame("DNA Sequence Management Pro");
        frame.setSize(900, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null); // Center on screen
        frame.setLayout(new BorderLayout());

        // 1. Sidebar Panel
        JPanel sidebar = createSidebar();
        frame.add(sidebar, BorderLayout.WEST);

        // 2. Content Panel (CardLayout)
        cardLayout = new CardLayout();
        contentPanel = new JPanel(cardLayout);
        contentPanel.setBackground(Color.WHITE);

        // Add different "pages" to the content panel
        contentPanel.add(createDashboardPanel(), "Dashboard");
        contentPanel.add(createAddPanel(), "Add");
        contentPanel.add(createUpdatePanel(), "Update");
        contentPanel.add(createDeletePanel(), "Delete");
        contentPanel.add(createListPanel(), "List");

        frame.add(contentPanel, BorderLayout.CENTER);

        frame.setVisible(true);
    }

    private JPanel createSidebar() {
        JPanel sidebar = new JPanel();
        sidebar.setPreferredSize(new Dimension(200, 600));
        sidebar.setBackground(SIDEBAR_COLOR);
        sidebar.setLayout(new BoxLayout(sidebar, BoxLayout.Y_AXIS));

        // Sidebar Header
        JLabel label = new JLabel("DNA MANAGER");
        label.setForeground(TEXT_COLOR);
        label.setFont(HEADER_FONT);
        label.setBorder(BorderFactory.createEmptyBorder(20, 20, 30, 20));
        sidebar.add(label);

        // Navigation Buttons
        sidebar.add(createSidebarButton("Dashboard", "Dashboard"));
        sidebar.add(createSidebarButton("Add DNA", "Add"));
        sidebar.add(createSidebarButton("Update DNA", "Update"));
        sidebar.add(createSidebarButton("Delete DNA", "Delete"));
        sidebar.add(createSidebarButton("View List", "List"));

        return sidebar;
    }

    private JButton createSidebarButton(String text, String cardName) {
        JButton btn = new JButton(text);
        btn.setMaximumSize(new Dimension(200, 50));
        btn.setBackground(SIDEBAR_COLOR);
        btn.setForeground(TEXT_COLOR);
        btn.setFont(MAIN_FONT);
        btn.setFocusPainted(false);
        btn.setBorderPainted(false);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));

        // Hover Effect
        btn.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) { btn.setBackground(BUTTON_HOVER_COLOR); }
            public void mouseExited(MouseEvent e) { btn.setBackground(SIDEBAR_COLOR); }
        });

        // Switch Cards on Click
        btn.addActionListener(e -> cardLayout.show(contentPanel, cardName));

        return btn;
    }

    // --- Panel Creation Methods ---

    private JPanel createDashboardPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(Color.WHITE);
        JLabel welcome = new JLabel("Welcome to DNA Sequence Manager");
        welcome.setFont(new Font("Segoe UI", Font.BOLD, 24));
        panel.add(welcome);
        return panel;
    }

    private JPanel createAddPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(Color.WHITE);
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JTextField idField = new JTextField(20);
        JTextField nameField = new JTextField(20);
        JTextField pathField = new JTextField(20);
        pathField.setEditable(false);
        JButton fileBtn = new JButton("Select DNA File");
        JButton saveBtn = new JButton("Save Sequence");

        gbc.gridx = 0; gbc.gridy = 0; panel.add(new JLabel("Patient ID:"), gbc);
        gbc.gridx = 1; panel.add(idField, gbc);
        
        gbc.gridx = 0; gbc.gridy = 1; panel.add(new JLabel("Patient Name:"), gbc);
        gbc.gridx = 1; panel.add(nameField, gbc);

        gbc.gridx = 0; gbc.gridy = 2; panel.add(new JLabel("DNA File:"), gbc);
        gbc.gridx = 1; 
        JPanel filePanel = new JPanel(new BorderLayout(5, 0));
        filePanel.setBackground(Color.WHITE);
        filePanel.add(pathField, BorderLayout.CENTER);
        filePanel.add(fileBtn, BorderLayout.EAST);
        panel.add(filePanel, gbc);

        gbc.gridx = 1; gbc.gridy = 3; 
        panel.add(saveBtn, gbc);
        
        // File Chooser Action
        fileBtn.addActionListener(e -> {
            JFileChooser chooser = new JFileChooser();
            int result = chooser.showOpenDialog(frame);
            if (result == JFileChooser.APPROVE_OPTION) {
                pathField.setText(chooser.getSelectedFile().getAbsolutePath());
            }
        });

        saveBtn.addActionListener(e -> {
            try {
                String idText = idField.getText();
                String name = nameField.getText();
                String path = pathField.getText();

                // Validation
                if (!Validation.isValidId(Integer.parseInt(idText))) {
                   JOptionPane.showMessageDialog(frame, "Invalid ID. Must be positive integer.");
                   return;
                }
                if (!Validation.isValidName(name)) {
                    JOptionPane.showMessageDialog(frame, "Invalid Name.");
                    return;
                }
                if (!Validation.isValidFilePath(path)) {
                    JOptionPane.showMessageDialog(frame, "Invalid File Path selected.");
                    return;
                }

                // Read and Validate File Content
                StringBuilder sb = new StringBuilder();
                try (BufferedReader br = new BufferedReader(new FileReader(path))) {
                     String line;
                     while ((line = br.readLine()) != null) {
                         sb.append(line.trim().toUpperCase());
                     }
                } catch (IOException ioException) {
                     JOptionPane.showMessageDialog(frame, "Error reading file.");
                     return;
                }
                
                String sequence = sb.toString();
                if (!Validation.isValidDNA(sequence)) {
                    JOptionPane.showMessageDialog(frame, "Invalid DNA Sequence in file (A, T, C, G only).");
                    return;
                }

                addPatient(Integer.parseInt(idText), name, sequence);
                idField.setText(""); nameField.setText(""); pathField.setText("");
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(frame, "ID must be a number!");
            }
        });

        return panel;
    }

    private JPanel createUpdatePanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(Color.WHITE);
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        JTextField idField = new JTextField(20);
        JTextField pathField = new JTextField(20);
        pathField.setEditable(false);
        JButton fileBtn = new JButton("Select New DNA File");
        JButton updateBtn = new JButton("Update Sequence");

        gbc.gridx = 0; gbc.gridy = 0; panel.add(new JLabel("Enter ID:"), gbc);
        gbc.gridx = 1; panel.add(idField, gbc);
        
        gbc.gridx = 0; gbc.gridy = 1; panel.add(new JLabel("New Sequence File:"), gbc);
        gbc.gridx = 1; 
        JPanel filePanel = new JPanel(new BorderLayout(5, 0));
        filePanel.setBackground(Color.WHITE);
        filePanel.add(pathField, BorderLayout.CENTER);
        filePanel.add(fileBtn, BorderLayout.EAST);
        panel.add(filePanel, gbc);

        gbc.gridx = 1; gbc.gridy = 2; panel.add(updateBtn, gbc);
        
        // File Chooser
        fileBtn.addActionListener(e -> {
            JFileChooser chooser = new JFileChooser();
            int result = chooser.showOpenDialog(frame);
            if (result == JFileChooser.APPROVE_OPTION) {
                pathField.setText(chooser.getSelectedFile().getAbsolutePath());
            }
        });

        updateBtn.addActionListener(e -> {
            try {
                 String idText = idField.getText();
                 String path = pathField.getText();
                 
                 if (!Validation.isValidId(Integer.parseInt(idText))) {
                     JOptionPane.showMessageDialog(frame, "Invalid ID.");
                     return;
                 }
                 if (!Validation.isValidFilePath(path)) {
                     JOptionPane.showMessageDialog(frame, "Invalid File.");
                     return;
                 }
                 
                 // Read and Validate
                StringBuilder sb = new StringBuilder();
                try (BufferedReader br = new BufferedReader(new FileReader(path))) {
                     String line;
                     while ((line = br.readLine()) != null) {
                         sb.append(line.trim().toUpperCase());
                     }
                } catch (IOException ioException) {
                     JOptionPane.showMessageDialog(frame, "Error reading file.");
                     return;
                }
                
                String sequence = sb.toString();
                if (!Validation.isValidDNA(sequence)) {
                    JOptionPane.showMessageDialog(frame, "Invalid DNA Sequence in file.");
                    return;
                }

                updatePatient(Integer.parseInt(idText), sequence);
                JOptionPane.showMessageDialog(frame, "Update Successful!"); // Explicit success message
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(frame, "Invalid ID format!");
            }
        });

        return panel;
    }

    private JPanel createDeletePanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(Color.WHITE);
        
        JTextField idField = new JTextField(10);
        JButton deleteBtn = new JButton("Delete Record");

        panel.add(new JLabel("Enter ID to Delete: "));
        panel.add(idField);
        panel.add(deleteBtn);

        deleteBtn.addActionListener(e -> {
            try {
                int id = Integer.parseInt(idField.getText());
                if(Validation.isValidId(id)){
                    deletePatient(id);
                } else {
                    JOptionPane.showMessageDialog(frame, "ID must be positive.");
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(frame, "Invalid ID format!");
            }
        });

        return panel;
    }

    private JPanel createListPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(Color.WHITE);
        
        JButton refreshBtn = new JButton("Refresh List");
        JTextArea displayArea = new JTextArea();
        displayArea.setEditable(false);
        JScrollPane scroll = new JScrollPane(displayArea);

        panel.add(refreshBtn, BorderLayout.NORTH);
        panel.add(scroll, BorderLayout.CENTER);

        refreshBtn.addActionListener(e -> {
            displayArea.setText(Database.db.returnAllPatientsData());
        });

        return panel;
    }

    public static void cases() {
        // Kept for backward compatibility with Main.java
        new Graphical_User_Interface();
    }
}
