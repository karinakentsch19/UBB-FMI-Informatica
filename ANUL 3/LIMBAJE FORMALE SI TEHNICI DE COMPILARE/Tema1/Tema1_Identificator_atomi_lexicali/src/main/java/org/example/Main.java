package org.example;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
public class Main {

    public static void main(String[] args) {
        FileReader fileReader = new FileReader("codeSample.txt");
        List<String> data = fileReader.readFromFile();
        IdentificatorAtomiLexicali identificator = new IdentificatorAtomiLexicali();
        List<String> atomi = identificator.identificaAtomiLexicali(data);
        System.out.println(atomi);
    }
}