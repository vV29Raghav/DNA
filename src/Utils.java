public class Utils {
    public static double matchPercentage(String seq1, String seq2) {
        int minLen = Math.min(seq1.length(), seq2.length());
        int matches = 0;
        for (int i = 0; i < minLen; i++) {
            if (seq1.charAt(i) == seq2.charAt(i)) matches++;
        }
        return (matches / (double) minLen) * 100.0;
    }

    public static String getComplement(String sequence) {
        return sequence.replace("A", "t").replace("T", "a")
                .replace("G", "c").replace("C", "g").toUpperCase();
    }

    public static double calculateGCContent(String sequence) {
        long gcCount = sequence.chars().filter(c -> c == 'G' || c == 'C').count();
        return (gcCount / (double) sequence.length()) * 100.0;
    }
}
