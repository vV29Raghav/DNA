import java.time.LocalDate;

public class DNASequence {
    private String id;
    private String personName;
    private String sequence;
    private LocalDate entryDate;

    public DNASequence(String id,String personName, String sequence){
        this.id = id;
        this.personName = personName;
        this.sequence = sequence;
        this.entryDate = LocalDate.now();
    }

    public String getId() {
        return id;
    }
    public String getPersonName() {
        return personName;
    }
    public String getSequence() {
        return sequence;
    }
    public LocalDate getEntryDate() {
        return entryDate;
    }
    public void setSequence(String sequence) {
        this.sequence = sequence;
    }

    public String toString() {
        return "[" + id + "] " + personName + " | Sequence: " + sequence + " | Date: " + entryDate;
    }


}
