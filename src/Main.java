import  java.util.*;
//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        DNAAnalyserService service = new DNAAnalyserService();
        Scanner sc = new Scanner(System.in);
        while (true) {
            System.out.println("\n1. Add DNA\n2. List DNA\n3. Compare DNA\n4. GC Content\n5. Complement\n6. Exit");
            int choice = sc.nextInt();
            sc.nextLine();
            switch (choice) {
                case 1:
                    System.out.print("Enter ID: ");
                    String id = sc.nextLine();
                    System.out.print("Enter Name: ");
                    String name = sc.nextLine();
                    System.out.print("Enter Sequence: ");
                    String seq = sc.nextLine();
                    service.addDNA(id, name, seq);
                    break;
                case 2:
                    service.listAll();
                    break;
                case 3:
                    System.out.print("Enter first ID: ");
                    String id1 = sc.nextLine();
                    System.out.print("Enter second ID: ");
                    String id2 = sc.nextLine();
                    service.compareSequences(id1, id2);
                    break;
                case 4:
                    System.out.print("Enter ID: ");
                    String gid = sc.nextLine();
                    service.printGCContent(gid);
                    break;
                case 5:
                    System.out.print("Enter ID: ");
                    String cid = sc.nextLine();
                    service.printComplement(cid);
                    break;
                case 6:
                    return;
            }
        }
    }
}