import java.util.ArrayList;
import java.util.List;

// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
public class Main {
    public static void main(String[] args) {
        Gramatica gramatica = new Gramatica();
        gramatica.citesteReguliProductie("gramatica.txt");
//        gramatica.citesteReguliProductie("reguliDeProductie.txt");
        gramatica.creeazaTabeleDeFirstSiFollow();
        gramatica.creeazaTabelaDeAnaliza();
        List<String> secventa = new ArrayList<>();
//        secventa care e acceptata
//        secventa.add("ceva");
//        secventa.add("+");
//        secventa.add("ceva");

//        secventa neacceptata
        secventa.add("ceva");
        secventa.add("+");
        secventa.add("+");

//        IdentificatorAtomi identificatorAtomi = new IdentificatorAtomi();
//        secventa = identificatorAtomi.identificaAtomiLexicali("codeSample.txt");
        gramatica.verificaSecventa(secventa);
    }
}