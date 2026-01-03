package DNA_Menu;

import java.sql.Date;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;

public class DNASequence {

        private int id;
        private String personName;
        private String sequence;
        private LocalDate dateRecorded;
        private LocalTime timeRecorded;


        //called whe we add file data to hashmap
        public DNASequence(int id,String name,String sequence) {
            this.id = id;
            this.personName = name;
            this.sequence = sequence;
            this.dateRecorded = LocalDate.now();
            this.timeRecorded = LocalTime.now();
        }
        public int getId() {
            return id;
        }
        public String getPersonName() {
            return personName;
        }
        public String getSequence() {
            return sequence;
        }
        public Date getDate() {
            return Date.valueOf(this.dateRecorded);
        }
        public Time getTime() {
            return Time.valueOf(this.timeRecorded);
        }
        public void setpersonname(String personName) {
        this.personName = personName;
    }
    // --- Scientific Analysis Features ---

    // Calculate GC Content (percentage of G and C Bases)
    public double calculateGCContent() {
        if (sequence == null || sequence.isEmpty()) return 0.0;
        long gcCount = sequence.chars().filter(ch -> ch == 'G' || ch == 'C').count();
        return (double) gcCount / sequence.length() * 100.0;
    }

    // Generate Reverse Complement Strand
    public String getReverseComplement() {
        if (sequence == null) return "";
        StringBuilder sb = new StringBuilder();
        for (int i = sequence.length() - 1; i >= 0; i--) {
            char base = sequence.charAt(i);
            switch (base) {
                case 'A' -> sb.append('T');
                case 'T' -> sb.append('A');
                case 'C' -> sb.append('G');
                case 'G' -> sb.append('C');
                default -> sb.append(base);
            }
        }
        return sb.toString();
    }
    
    // Search for a specific motif (sub-sequence)
    public boolean containsMotif(String motif) {
        return sequence != null && sequence.contains(motif.toUpperCase());
    }

    @Override
    public String toString() {
        return "DNASequence{" +
                "id=" + id +
                ", personName='" + personName + '\'' +
                ", sequence='" + sequence + '\'' +
                ", GC Content=" + String.format("%.2f", calculateGCContent()) + "%" +
                ", dateRecorded=" + dateRecorded +
                ", timeRecorded=" + timeRecorded +
                '}';
    }
}
