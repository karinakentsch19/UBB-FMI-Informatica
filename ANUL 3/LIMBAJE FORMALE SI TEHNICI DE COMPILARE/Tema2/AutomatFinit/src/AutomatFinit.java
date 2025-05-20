import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class AutomatFinit {
    private List<String> stari;

    private List<String> alfabet;

    private String stareDeStart;

    private List<String> stariFinale;

    private List<String>[][] tranzitii;

    public AutomatFinit(String numeFisier) {
        try {
            File file = new File(numeFisier);
            Scanner scanner = new Scanner(file);
            String linieStari = scanner.nextLine();
            String linieAlfabet = scanner.nextLine();
            String linieStareStart = scanner.nextLine();
            String linieStariFinale = scanner.nextLine();

            this.stari = List.of(linieStari.split(","));
            this.alfabet = List.of(linieAlfabet.split(","));
            this.stareDeStart = linieStareStart;
            this.stariFinale = List.of(linieStariFinale.split(","));

            this.tranzitii = new ArrayList[this.stari.size()][this.stari.size()];

            for (int i = 0; i < stari.size(); i++)
                for (int j = 0; j < stari.size(); j++)
                    tranzitii[i][j] = new ArrayList<>();

            while (scanner.hasNextLine()) {
                String tranzitie = scanner.nextLine();
                String[] elem = tranzitie.split(",");
                int sursa = stari.indexOf(elem[0]);
                int destinatie = stari.indexOf(elem[2]);
                String simbol = elem[1];
                tranzitii[sursa][destinatie].add(simbol);
            }

            scanner.close();

        } catch (FileNotFoundException exception) {
            exception.printStackTrace();
        }
    }

    public void afiseazaMultimeaStarilor(){
        System.out.println("STARI:");
        for (String stare : stari)
            System.out.print(stare + " ");
        System.out.println();
    }

    public void afiseazaAlfabetul(){
        System.out.println("ALFABET:");
        for (String simbol : alfabet)
            System.out.print(simbol + " ");
        System.out.println();
    }

    public void afiseazaTranzitiile(){
        System.out.println("TRANZITII:");
        for (int i = 0; i < stari.size(); i++)
            for (int j = 0; j < stari.size(); j++)
                for (String simbol : tranzitii[i][j])
                    System.out.println("Stare 1: " + stari.get(i) + " Simbol: " + simbol + " Stare 2: " + stari.get(j));
        System.out.println();
    }

    public void afiseazaStarileFinale() {
        System.out.println("STARI DE FINAL:");
        for (String stare : stariFinale)
            System.out.print(stare + " ");
        System.out.println();
    }

    public boolean verificaSecventa(String secventa) {
        return parcurgere(stareDeStart, secventa);
    }

    private boolean parcurgere(String stare, String secventa) {
        if (secventa.isEmpty()) {
            if (stariFinale.contains(stare))
                return true;
            else
                return false;
        }
        int indStare = stari.indexOf(stare);
        for (int j = 0; j < stari.size(); j++)
            for (String simbol : tranzitii[indStare][j])
                if (simbol.charAt(0) == secventa.charAt(0))
                    return parcurgere(stari.get(j), secventa.substring(1));
        return false;
    }

    public String celMaiLungPrefixAcceptat(String secventa) {
        for (int i = secventa.length() - 1; i >= 0; i--)
            if (parcurgere(stareDeStart, secventa.substring(0, i + 1)))
                return secventa.substring(0, i + 1);
        return "";
    }

}
