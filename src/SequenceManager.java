import java.util.*;

public class SequenceManager {
    private HashMap<String,DNASequence> sequences=new HashMap<>();
    public void addSequence(DNASequence dna){
        sequences.put(dna.getId(),dna);
    }
    public DNASequence getSequence(String id) {
        return sequences.get(id);
    }
    public void updateSequence(String id, String newSequence) {
        DNASequence dna = sequences.get(id);
        if (dna != null) {
            dna.setSequence(newSequence);
        }
    }
    public void deleteSequence(String id) {
        sequences.remove(id);
    }
    public List<DNASequence> listAllSequences() {
        return new ArrayList<>(sequences.values());
    }
}
