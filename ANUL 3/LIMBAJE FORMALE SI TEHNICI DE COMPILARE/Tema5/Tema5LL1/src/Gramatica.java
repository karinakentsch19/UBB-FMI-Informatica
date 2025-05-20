import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Gramatica {
    private Set<String> neterminale = new HashSet<>();
    private Set<String> terminale = new HashSet<>();

    private Map<String, List<Pereche<List<String>, Integer>>> reguliProductie = new HashMap<>();
    private String simbolDeStart;

    private Map<String, Set<String>> tabelaFirst = new HashMap<>();
    private Map<String, Set<String>> tabelaFollow = new HashMap<>();

    private Map<Pereche<String, String>, Pereche<String, String>> tabelaDeAnaliza = new HashMap<>();

    public Gramatica() {
    }

    private static List<String> obtainListOfAtoms(String token) {

        List<String> atomi = new ArrayList<>();
        Pattern pattern = Pattern.compile("\"([^\"]*)\"|[a-zA-Z]|[()+*={};><!-/%]|[0-9]");
        Matcher matcher = pattern.matcher(token);


        while (matcher.find()) {
            if (matcher.group(1) != null) {
                // If it's a quoted string, add the content inside the quotes
                atomi.add(matcher.group(1));
            } else {
                // Otherwise, add the matched atom
                atomi.add(matcher.group());
            }
        }
        return atomi;
    }

    public void citesteReguliProductie(String numeFisier) {
        try {
            File myObj = new File(numeFisier);
            Scanner myReader = new Scanner(myObj);
            boolean simbolDeStartGasit = false;
            int index = 1;
            while (myReader.hasNext()) {
                String line = myReader.nextLine();
                String[] items = line.split("->");
                if (!simbolDeStartGasit) {
                    simbolDeStart = items[0];
                    simbolDeStartGasit = true;
                }
                String neterminal = items[0];
                List<String> regula = new ArrayList<>();
                if (items.length == 1)
                    regula.add("ε");
                else
                    regula.addAll(obtainListOfAtoms(items[1]));

                neterminale.add(neterminal);
                if (reguliProductie.containsKey(neterminal)) {
                    reguliProductie.get(neterminal).add(new Pereche<>(regula, index));
                    index++;
                } else {
                    List<Pereche<List<String>, Integer>> list = new ArrayList<>();
                    list.add(new Pereche<>(regula, index));
                    reguliProductie.put(neterminal, list);
                    index++;
                }
                Pattern pattern = Pattern.compile("[^A-Zε]");

                for (String atom : regula)
                    if (atom.matches("[^A-Zε]+"))
                        terminale.add(atom);
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    private void creeazaTabelaFirst() {
        boolean changed;
        do {
            changed = false;
            for (String neterminal : neterminale) {
                Set<String> firstNeterminal = new HashSet<>();
                if (tabelaFirst.containsKey(neterminal)) {
                    firstNeterminal = tabelaFirst.get(neterminal);
                }
                for (var pereche : reguliProductie.get(neterminal)) {
                    List<String> regula = pereche.getFirst();
                    System.out.println(neterminal + " " + regula);
                    String primulItem = regula.getFirst();
                    if (terminale.contains(primulItem) || Objects.equals(primulItem, "ε")) {
                        if (!firstNeterminal.contains(primulItem)) {
                            firstNeterminal.add(primulItem);
                            changed = true;
                        }
                    } else {
                        if (tabelaFirst.containsKey(primulItem)) {
                            for (String item : tabelaFirst.get(primulItem)) {
                                if (!firstNeterminal.contains(item)) {
                                    firstNeterminal.add(item);
                                    changed = true;
                                }
                            }
                        }
                    }
                }
                tabelaFirst.put(neterminal, firstNeterminal);
            }
        } while (changed);
    }

    private void creeazaTabelaFollow() {
        tabelaFollow.put(simbolDeStart, new HashSet<>(Collections.singletonList("$")));
        boolean changed;
        List<Pereche<List<String>, String>> productii = new ArrayList<>();
        for (String neterminal : neterminale) {
            for (var productie : reguliProductie.get(neterminal))
                productii.add(new Pereche<>(productie.getFirst(), neterminal));
        }
        do {
            changed = false;
            for (String item : neterminale) {
                Set<String> followNeterminal = new HashSet<>();
                if (tabelaFollow.containsKey(item)) {
                    followNeterminal = tabelaFollow.get(item);
                }
                for (var productie : productii) {
                    List<String> copieProductie = new ArrayList<>();
                    copieProductie.addAll(productie.getFirst());
                    int index = copieProductie.indexOf(item);
                    while (index != -1) {
                        if (index != copieProductie.size() - 1) {
                            String atom = copieProductie.get(index + 1);
                            if (terminale.contains(atom)) {
                                if (!followNeterminal.contains(atom)) {
                                    followNeterminal.add(atom);
                                    changed = true;
                                }
                            } else {
                                Set<String> first = tabelaFirst.get(atom);
                                for (String f : first) {
                                    if (!followNeterminal.contains(f) && !Objects.equals(f, "ε")) {
                                        followNeterminal.add(f);
                                        changed = true;
                                    }
                                }
                                if (first.contains("ε")) {
                                    Set<String> follow = new HashSet<>();
                                    if (tabelaFollow.containsKey(atom)) {
                                        follow = tabelaFollow.get(atom);
                                    }
                                    for (String f : follow) {
                                        if (!followNeterminal.contains(f)) {
                                            followNeterminal.add(f);
                                            changed = true;
                                        }
                                    }
                                }
                            }
                        } else {
                            Set<String> follow = new HashSet<>();
                            if (tabelaFollow.containsKey(productie.getSecond())) {
                                follow = tabelaFollow.get(productie.getSecond());
                            }
                            for (String f : follow) {
                                if (!followNeterminal.contains(f)) {
                                    followNeterminal.add(f);
                                    changed = true;
                                }
                            }
                        }
                        copieProductie = copieProductie.subList(index + 1, copieProductie.size());
                        index = copieProductie.indexOf(item);
                    }
                }
                tabelaFollow.put(item, followNeterminal);
            }
        } while (changed);
    }

    public void creeazaTabeleDeFirstSiFollow() {
        creeazaTabelaFirst();
        creeazaTabelaFollow();
        System.out.println("FIRST sets:");
        for (String nonTerminal : tabelaFirst.keySet()) {
            System.out.println(nonTerminal + ": " + tabelaFirst.get(nonTerminal));
        }
        System.out.println("FOLLOW sets:");
        for (String nonTerminal : tabelaFollow.keySet()) {
            System.out.println(nonTerminal + ": " + tabelaFollow.get(nonTerminal));
        }
    }

    private boolean apartineTabelaFollow(String simbolLinie, String simbolColoana) {
        return tabelaFollow.get(simbolLinie).contains(simbolColoana);
    }

    private boolean apartineTabelaFirst(String productie, String simbolColoana) {
        if (productie.equals("ε"))
            return simbolColoana.equals("ε");
        if (productie.matches("[A-Z]")) {
            return tabelaFirst.get(productie).contains(simbolColoana);
        } else
            return productie.equals(simbolColoana);
    }

    private void adaugaInTabelDeAnaliza(Pereche<String, String> pereche, String valoare, Integer nrProductie) {
//        System.out.println(pereche);
//        System.out.println(valoare + " " + nrProductie);
        Pereche<String, String> p = new Pereche<String, String>(valoare, nrProductie.toString());
//        if (tabelDeAnaliza.containsKey(pereche) && !Objects.equals(tabelDeAnaliza.get(pereche), p))
//            throw new RuntimeException("Conflict in tabelul de analiza");

        tabelaDeAnaliza.put(pereche, p);
    }

    public void creeazaTabelaDeAnaliza() {
        Set<String> coloane = new HashSet<>(terminale);
        Set<String> linii = new HashSet<>(neterminale);
        linii.addAll(terminale);
        coloane.add("$");
        linii.add("$");

        for (String linie : linii) {
            for (String coloana : coloane) {
                tabelaDeAnaliza.put(new Pereche<>(linie, coloana), new Pereche<>("err", "-1"));
            }
        }

        for (String linie : linii) {
            for (String simbolColoana : coloane) {
                Pereche<String, String> pereche = new Pereche<>(linie, simbolColoana);
                if (linie.matches("[A-Z]")) {
                    for (Pereche<List<String>, Integer> productii : reguliProductie.get(linie)) {
                        if (apartineTabelaFirst(productii.getFirst().get(0), simbolColoana)) {
                            String valoare = productii.getFirst().stream()
                                    .reduce("", (partialResult, atom) -> partialResult + '"' + atom + '"');
                            adaugaInTabelDeAnaliza(pereche, valoare, productii.getSecond());

                        }
                        if (apartineTabelaFirst(productii.getFirst().get(0), "ε") && apartineTabelaFollow(linie, simbolColoana)) {

                            String valoare = productii.getFirst().stream()
                                    .reduce("", (partialResult, atom) -> partialResult + '"' + atom + '"');
                            adaugaInTabelDeAnaliza(pereche, valoare, productii.getSecond());
                        }
                    }
                } else if (Objects.equals(linie, simbolColoana) && !Objects.equals(linie, "$"))
                    adaugaInTabelDeAnaliza(pereche, "pop", -1);
                else if (Objects.equals(linie, simbolColoana) && Objects.equals(linie, "$"))
                    adaugaInTabelDeAnaliza(pereche, "acc", -1);
            }
        }
        System.out.printf("%-10s", ""); // Empty space for alignment
        for (String simbolColoana : coloane) {
            System.out.printf("%-10s", simbolColoana); // Column headers
        }
        System.out.println();

        for (String simbolLinie : linii) {
            System.out.printf("%-10s", simbolLinie); // Row header
            for (String simbolColoana : coloane) {
                Pereche<String, String> valoare = tabelaDeAnaliza.get(new Pereche<>(simbolLinie, simbolColoana));

                if (!Objects.equals(valoare.getSecond(), "-1")) {
                    System.out.printf("%-10s", "(" + valoare.getFirst() + "," + valoare.getSecond() + ")");
                } else {
                    System.out.printf("%-10s", valoare.getFirst());
                }
            }
            System.out.println();
        }
    }

    public void verificaSecventa(List<String> secventa) {
        List<Integer> sirDeProductii = new ArrayList<>();
        secventa.add("$");

        Stack<String> stiva = new Stack<String>();
        stiva.push("$");
        stiva.push(simbolDeStart);

        while (true) {
            String simbol = stiva.pop();
            Pereche<String, String> pereche = new Pereche<>(simbol, secventa.getFirst());
            Pereche<String, String> valoareTabelAnaliza = tabelaDeAnaliza.get(pereche);
            if (Objects.equals(valoareTabelAnaliza.getFirst(), "err")) {
                throw new RuntimeException("Secventa nu este acceptata.");
            } else if (Objects.equals(valoareTabelAnaliza.getFirst(), "acc")) {
                System.out.println("Secventa este acceptata\nSirul de productii:" + sirDeProductii);
                return;
            } else if (Objects.equals(valoareTabelAnaliza.getFirst(), "pop")) {
                secventa = secventa.subList(1, secventa.size());
            } else {
                sirDeProductii.add(Integer.parseInt(valoareTabelAnaliza.getSecond()));
                String productie = valoareTabelAnaliza.getFirst();
                String[] parts = productie.split("\"");
                for (int i = parts.length - 1; i >= 0; i--) {
                    if (!parts[i].isEmpty() && !parts[i].equals("ε")) {
                        stiva.push(parts[i]);
                    }
                }
            }
        }
    }

}
