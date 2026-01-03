package CLI_GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import java.util.*;
import java.io.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import DNA_Menu.Validation;
import Database.DataExporter;
import Database.db;

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
            for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (Exception e) {}
        initializeUI();
    }

    // Static entry point for Main.java compatibility
    public static void cases() {
        new Graphical_User_Interface();
    }

    private void initializeUI() {
        frame = new JFrame("DNA Sequence Management Pro");
        frame.setSize(950, 650);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setLayout(new BorderLayout());

        // Sidebar
        JPanel sidebar = createSidebar();
        frame.add(sidebar, BorderLayout.WEST);

        // Content
        cardLayout = new CardLayout();
        contentPanel = new JPanel(cardLayout);
        contentPanel.setBackground(Color.WHITE);

        // Add Panels
        contentPanel.add(createDashboardPanel(), "Dashboard");
        contentPanel.add(createAddPanel(), "Add");
        contentPanel.add(createUpdatePanel(), "Update");
        contentPanel.add(createDeletePanel(), "Delete");
        contentPanel.add(createListPanel(), "List");
        contentPanel.add(createSearchPanel(), "Search");
        contentPanel.add(createAnalysisPanel(), "Analysis");
        contentPanel.add(createExportPanel(), "Export");

        frame.add(contentPanel, BorderLayout.CENTER);
        frame.setVisible(true);
    }

    private JPanel createSidebar() {
        JPanel sidebar = new JPanel();
        sidebar.setPreferredSize(new Dimension(200, 650));
        sidebar.setBackground(SIDEBAR_COLOR);
        sidebar.setLayout(new BoxLayout(sidebar, BoxLayout.Y_AXIS));

        JLabel label = new JLabel("DNA MANAGER");
        label.setForeground(TEXT_COLOR);
        label.setFont(HEADER_FONT);
        label.setBorder(BorderFactory.createEmptyBorder(20, 20, 30, 20));
        sidebar.add(label);

        sidebar.add(createSidebarButton("Dashboard", "Dashboard"));
        sidebar.add(createSidebarButton("Add DNA", "Add"));
        sidebar.add(createSidebarButton("Update DNA", "Update"));
        sidebar.add(createSidebarButton("Delete DNA", "Delete"));
        sidebar.add(createSidebarButton("View List", "List"));
        sidebar.add(createSidebarButton("Search DNA", "Search"));
        sidebar.add(createSidebarButton("DNA Analysis", "Analysis"));
        sidebar.add(createSidebarButton("Export Data", "Export"));

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

        btn.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) { btn.setBackground(BUTTON_HOVER_COLOR); }
            public void mouseExited(MouseEvent e) { btn.setBackground(SIDEBAR_COLOR); }
        });

        btn.addActionListener(e -> cardLayout.show(contentPanel, cardName));
        return btn;
    }

    // --- Panels ---

    private JPanel createDashboardPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(Color.WHITE);
        JLabel welcome = new JLabel("Welcome to DNA Sequence Manager Pro");
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
        JPanel filePanel = new JPanel(new BorderLayout(5, 0));
        filePanel.setBackground(Color.WHITE);
        filePanel.add(pathField, BorderLayout.CENTER);
        filePanel.add(fileBtn, BorderLayout.EAST);
        gbc.gridx = 1; panel.add(filePanel, gbc);
        gbc.gridx = 1; gbc.gridy = 3; panel.add(saveBtn, gbc);

        fileBtn.addActionListener(e -> {
            JFileChooser chooser = new JFileChooser();
            if (chooser.showOpenDialog(frame) == JFileChooser.APPROVE_OPTION) {
                pathField.setText(chooser.getSelectedFile().getAbsolutePath());
            }
        });

        saveBtn.addActionListener(e -> {
            try {
                String idText = idField.getText();
                String name = nameField.getText();
                String path = pathField.getText();

                if (!Validation.isValidId(Integer.parseInt(idText))) { JOptionPane.showMessageDialog(frame, "Invalid ID."); return; }
                if (!Validation.isValidName(name)) { JOptionPane.showMessageDialog(frame, "Invalid Name."); return; }
                if (!Validation.isValidFilePath(path)) { JOptionPane.showMessageDialog(frame, "Invalid File."); return; }

                StringBuilder sb = new StringBuilder();
                try (BufferedReader br = new BufferedReader(new FileReader(path))) {
                    String line;
                    while ((line = br.readLine()) != null) sb.append(line.trim().toUpperCase());
                } catch (IOException ex) { JOptionPane.showMessageDialog(frame, "Error reading file."); return; }

                String sequence = sb.toString();
                if (!Validation.isValidDNA(sequence)) { JOptionPane.showMessageDialog(frame, "Invalid DNA in file."); return; }

                db.addPatient(Integer.parseInt(idText), name, sequence);
                idField.setText(""); nameField.setText(""); pathField.setText("");
            } catch (NumberFormatException ex) { JOptionPane.showMessageDialog(frame, "ID must be a number!"); }
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
        gbc.gridx = 0; gbc.gridy = 1; panel.add(new JLabel("New File:"), gbc);
        JPanel filePanel = new JPanel(new BorderLayout(5, 0));
        filePanel.setBackground(Color.WHITE);
        filePanel.add(pathField, BorderLayout.CENTER);
        filePanel.add(fileBtn, BorderLayout.EAST);
        gbc.gridx = 1; panel.add(filePanel, gbc);
        gbc.gridx = 1; gbc.gridy = 2; panel.add(updateBtn, gbc);

        fileBtn.addActionListener(e -> {
            JFileChooser chooser = new JFileChooser();
            if (chooser.showOpenDialog(frame) == JFileChooser.APPROVE_OPTION) {
                pathField.setText(chooser.getSelectedFile().getAbsolutePath());
            }
        });

        updateBtn.addActionListener(e -> {
            try {
                String idText = idField.getText();
                String path = pathField.getText();
                if (!Validation.isValidId(Integer.parseInt(idText))) { JOptionPane.showMessageDialog(frame, "Invalid ID."); return; }
                if (!Validation.isValidFilePath(path)) { JOptionPane.showMessageDialog(frame, "Invalid File."); return; }

                StringBuilder sb = new StringBuilder();
                try (BufferedReader br = new BufferedReader(new FileReader(path))) {
                    String line;
                    while ((line = br.readLine()) != null) sb.append(line.trim().toUpperCase());
                } catch (IOException ex) { JOptionPane.showMessageDialog(frame, "Error reading file."); return; }

                String sequence = sb.toString();
                if (!Validation.isValidDNA(sequence)) { JOptionPane.showMessageDialog(frame, "Invalid DNA in file."); return; }

                db.updatePatient(Integer.parseInt(idText), sequence);
            } catch (Exception ex) { JOptionPane.showMessageDialog(frame, "Invalid Input!"); }
        });
        return panel;
    }

    private JPanel createDeletePanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(Color.WHITE);
        JTextField idField = new JTextField(10);
        JButton deleteBtn = new JButton("Delete Record");
        panel.add(new JLabel("Enter ID: "));
        panel.add(idField);
        panel.add(deleteBtn);

        deleteBtn.addActionListener(e -> {
            try {
                int id = Integer.parseInt(idField.getText());
                if(Validation.isValidId(id)) db.deletePatient(id);
                else JOptionPane.showMessageDialog(frame, "ID must be positive.");
            } catch (Exception ex) { JOptionPane.showMessageDialog(frame, "Invalid ID!"); }
        });
        return panel;
    }

    private JPanel createListPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(Color.WHITE);
        JButton refreshBtn = new JButton("Refresh List");
        JTextArea displayArea = new JTextArea();
        displayArea.setEditable(false);
        panel.add(refreshBtn, BorderLayout.NORTH);
        panel.add(new JScrollPane(displayArea), BorderLayout.CENTER);
        refreshBtn.addActionListener(e -> displayArea.setText(db.returnAllPatientsData()));
        return panel;
    }

    private JPanel createSearchPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        JPanel top = new JPanel(new FlowLayout());
        JTextField searchField = new JTextField(20);
        JButton searchBtn = new JButton("Search Name");
        JTextArea area = new JTextArea();
        area.setEditable(false);

        top.add(new JLabel("Search Name:"));
        top.add(searchField);
        top.add(searchBtn);
        panel.add(top, BorderLayout.NORTH);
        panel.add(new JScrollPane(area), BorderLayout.CENTER);

        searchBtn.addActionListener(e -> {
            String q = searchField.getText().toLowerCase();
             String all = db.returnAllPatientsData();
             if (q.isEmpty()) { area.setText(all); return; }
             StringBuilder sb = new StringBuilder();
             for(String line : all.split("-------------------------------------------------------------\n")) {
                 if(line.toLowerCase().contains(q)) sb.append(line).append("\n-------------------------------------------------------------\n");
             }
             area.setText(sb.length() > 0 ? sb.toString() : "No matches.");
        });
        return panel;
    }

    private JPanel createAnalysisPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(Color.WHITE);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10,10,10,10);
        JTextField idField = new JTextField(15);
        JButton btn = new JButton("Analyze");
        JTextArea res = new JTextArea(15, 50);
        res.setEditable(false);

        gbc.gridx=0; gbc.gridy=0; panel.add(new JLabel("Enter ID:"), gbc);
        gbc.gridx=1; panel.add(idField, gbc);
        gbc.gridx=2; panel.add(btn, gbc);
        gbc.gridx=0; gbc.gridy=1; gbc.gridwidth=3; panel.add(new JScrollPane(res), gbc);

        btn.addActionListener(e -> {
            try {
                int id = Integer.parseInt(idField.getText());
                DNA_Menu.DNASequence dna = db.getpatinetbyid(id);
                if(dna != null) {
                    res.setText("Results for ID: " + id + "\n" +
                            "Name: " + dna.getPersonName() + "\n" +
                            "Sequence Length: " + dna.getSequence().length() + "\n" +
                            "GC Content: " + String.format("%.2f", dna.calculateGCContent()) + "%\n" +
                            "Reverse Complement: " + dna.getReverseComplement());
                } else res.setText("Not found.");
            } catch(Exception ex) { res.setText("Invalid ID."); }
        });
        return panel;
    }

    private JPanel createExportPanel() {
         JPanel panel = new JPanel(new GridBagLayout());
         panel.setBackground(Color.WHITE);
         JButton exportBtn = new JButton("Export to CSV");
         panel.add(exportBtn);
         exportBtn.addActionListener(e -> {
             JFileChooser fc = new JFileChooser();
             if(fc.showSaveDialog(frame) == JFileChooser.APPROVE_OPTION) {
                 if(DataExporter.exportToCSV(fc.getSelectedFile().getAbsolutePath()))
                     JOptionPane.showMessageDialog(frame, "Export Successful!");
                 else JOptionPane.showMessageDialog(frame, "Export Failed.");
             }
         });
         return panel;
    }
}
