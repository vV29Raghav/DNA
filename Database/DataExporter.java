package Database;

import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class DataExporter {
    private static final String URL = "jdbc:mysql://localhost:3306/dna";
    private static final String USER = "root";
    private static final String PASSWORD = "Mapple28!";

    public static boolean exportToCSV(String filePath) {
        String query = "SELECT * FROM dna_sequences";
        
        // Ensure file ends with .csv
        if (!filePath.toLowerCase().endsWith(".csv")) {
            filePath += ".csv";
        }

        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query);
             FileWriter writer = new FileWriter(filePath)) {

            // Header
            writer.append("ID,Person Name,Sequence,Date,Time\n");

            while (rs.next()) {
                writer.append(String.valueOf(rs.getInt("id")));
                writer.append(",");
                writer.append(escapeSpecialCharacters(rs.getString("person_name")));
                writer.append(",");
                writer.append(rs.getString("sequence"));
                writer.append(",");
                writer.append(String.valueOf(rs.getDate("date")));
                writer.append(",");
                writer.append(String.valueOf(rs.getTime("time")));
                writer.append("\n");
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    private static String escapeSpecialCharacters(String data) {
        if (data == null) return "";
        String escapedData = data.replaceAll("\\R", " ");
        if (data.contains(",") || data.contains("\"") || data.contains("'")) {
            data = data.replace("\"", "\"\"");
            escapedData = "\"" + data + "\"";
        }
        return escapedData;
    }
}
