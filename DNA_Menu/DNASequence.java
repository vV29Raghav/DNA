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
    public String toString() {
        return "DNASequence{" +
                "id=" + id +
                ", personName='" + personName + '\'' +
                ", sequence='" + sequence + '\'' +
                ", dateRecorded=" + dateRecorded +
                ", timeRecorded=" + timeRecorded +
                '}';
    }
}
