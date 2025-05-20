import java.io.*;
import java.util.*;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

public class Main {

    public static void saveTabelaFIPInFile(List<ElementFIP> tabelaFIP, String fileName) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            writer.write("Cod,Pozitie in TS");
            writer.newLine();
            for (ElementFIP element : tabelaFIP) {
                writer.write(element.getCod() + "," + element.getPozitieInTS());
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void saveTabelaTSInFile(TabelaDeDispersie<Integer, String> tabelaTS, String fileName) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            writer.write("Pozitie,Atom lexical");
            writer.newLine();
            for (Integer pozitie : tabelaTS.keySet()) {
                writer.write(pozitie + "," + tabelaTS.get(pozitie));
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Integer getPozitieInTS(TabelaDeDispersie<Integer, String> tabelaTS, String atom) {
        for (Integer key : tabelaTS.keySet()) {
            if (Objects.equals(tabelaTS.get(key), atom))
                return key;
        }
        return -1;
    }

    public static Map<String, Integer> readFromFile(String fileName){
        Map<String, Integer> atomi = new HashMap<>();
        try {
            List<String> fileData = new ArrayList<>();
            File myObj = new File(fileName);
            Scanner myReader = new Scanner(myObj);
            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                String[] splits = data.split(":");
                atomi.put(splits[0], Integer.parseInt(splits[1]));
            }
            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
        return atomi;
    }

    public static void creeazaTabeleFIPsiTS(List<String> atomi, String fileFIP, String fileTS) {
        String regexID = "[a-zA-Z][a-zA-Z0-9]*";
        String regexCONST = "[0-9]+[.]?[0-9]*";
        String regexCuvantRezervat = "\\b(start|end|read|write|if|else|while|struct|int|bool|float)\\b|<<|>>|==|!=|&&|\\|\\||[{}();,=+\\-/*%<>!&|]";
        int codFIP = 2, linieTS = 1;
        List<ElementFIP> tabelaFIP = new ArrayList<>();
        TabelaDeDispersie<Integer, String> tabelaTS = new TabelaDeDispersie<>(atomi.size());
        Map<String, Integer> atomiLexicali = readFromFile("codificari.txt");

        for (String atom : atomi) {
            if (Pattern.matches(regexCuvantRezervat, atom)) {
                ElementFIP elementFIP = new ElementFIP(atomiLexicali.get(atom), -1);
                tabelaFIP.add(elementFIP);

            } else if (Pattern.matches(regexID, atom)) {
                if (!tabelaTS.containsValue(atom)) {
                    tabelaTS.add(linieTS, atom);
                    linieTS++;
                }
                ElementFIP elementFIP = new ElementFIP(0, getPozitieInTS(tabelaTS, atom));
                tabelaFIP.add(elementFIP);
            } else if (Pattern.matches(regexCONST, atom)) {
                if (!tabelaTS.containsValue(atom)) {
                    tabelaTS.add(linieTS, atom);
                    linieTS++;
                }
                ElementFIP elementFIP = new ElementFIP(1, getPozitieInTS(tabelaTS, atom));
                tabelaFIP.add(elementFIP);
            }
        }
        saveTabelaFIPInFile(tabelaFIP, fileFIP);
        saveTabelaTSInFile(tabelaTS, fileTS);
        atomiLexicali.forEach((atom, cod) -> System.out.println(atom + ": " + cod));
    }

    public static void main(String[] args) throws InvalidTokenException {
        try {
            FileReader fileReader = new FileReader("codeSample.txt");
            List<String> data = fileReader.readFromFile();
            IdentificatorAtomiLexicali identificator = new IdentificatorAtomiLexicali();
            List<String> atomi = identificator.identificaAtomiLexicali(data);
//        System.out.println(atomi);
            creeazaTabeleFIPsiTS(atomi, "tabelFIP.csv", "tabelTS.csv");
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }
}