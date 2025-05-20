import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
//        AutomatFinit automatFinit = new AutomatFinit("exempluInClass.txt");
//        AutomatFinit automatFinit = new AutomatFinit("exempluAutomatFinit.txt");
        AutomatFinit automatFinit = new AutomatFinit("automatFinitConstanteC.txt");
        Scanner scanner = new Scanner(System.in);
        int optiune;

        do {
            System.out.println("Meniu:");
            System.out.println("1 - multimea starilor");
            System.out.println("2 - alfabetul");
            System.out.println("3 - tranzitii");
            System.out.println("4 - multimea starilor finale");
            System.out.println("5 - verifica secventa");
            System.out.println("6 - cel mai lung prefix dintr-o secventa");
            System.out.println("0 - exit");

            optiune = scanner.nextInt();
            scanner.nextLine();

            switch (optiune) {
                case 1:
                    automatFinit.afiseazaMultimeaStarilor();
                    break;
                case 2:
                    automatFinit.afiseazaAlfabetul();
                    break;
                case 3:
                    automatFinit.afiseazaTranzitiile();
                    break;
                case 4:
                    automatFinit.afiseazaStarileFinale();
                    break;
                case 5:
                    //ex: 12123113 true
                    System.out.println("Introduceti o secventa: ");
                    String secventa = scanner.nextLine();
                    System.out.println(automatFinit.verificaSecventa(secventa));
                    break;
                case 6:
                    //ex: 1231134
                    System.out.println("Introduceti o secventa: ");
                    String secv = scanner.nextLine();
                    System.out.println(automatFinit.celMaiLungPrefixAcceptat(secv));
                    break;
                case 0:
                    System.out.println("Iesire din program.");
                    break;
                default:
                    System.out.println("Optiune invalida!");
            }
        } while (optiune != 0);

        scanner.close();
    }
}