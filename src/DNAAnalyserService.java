public class DNAAnalyserService {
    private SequenceManager repo = new SequenceManager();

    public void addDNA(String id, String name, String sequence) {
        repo.addSequence(new DNASequence(id, name, sequence));
    }

    public void listAll() {
        for (DNASequence dna : repo.listAllSequences()) {
            System.out.println(dna);
        }
    }

    public void compareSequences(String id1, String id2) {
        var s1 = repo.getSequence(id1);
        var s2 = repo.getSequence(id2);
        if (s1 == null || s2 == null) {
            System.out.println("One or both sequences not found.");
            return;
        }
        double match = Utils.matchPercentage(s1.getSequence(), s2.getSequence());
        System.out.printf("Match Percentage: %.2f%%\n", match);
    }

    public void printGCContent(String id) {
        var s = repo.getSequence(id);
        if (s != null) {
            System.out.printf("GC Content: %.2f%%\n", Utils.calculateGCContent(s.getSequence()));
        }
    }

    public void printComplement(String id) {
        var s = repo.getSequence(id);
        if (s != null) {
            System.out.println("Complement: " + Utils.getComplement(s.getSequence()));
        }
    }
}
