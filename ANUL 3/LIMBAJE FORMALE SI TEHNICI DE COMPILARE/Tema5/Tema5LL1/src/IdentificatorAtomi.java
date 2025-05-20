import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class IdentificatorAtomi {
    private static final String ATOM_REGEX = "\\b(start|end|read|write|if|else|while|struct|int|bool|float)\\b|<<|>>|==|!=|&&|\\|\\||[{}();,=+\\-/*%<>!&|]|[a-zA-Z0-9]";

    private List<String> readFromFile(String fileName){
        try {
            List<String> fileData = new ArrayList<>();
            File myObj = new File(fileName);
            Scanner myReader = new Scanner(myObj);
            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                fileData.add(data);
            }
            myReader.close();
            return fileData;
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
        return new ArrayList<>();
    }

    public List<String> identificaAtomiLexicali(String fileName) {
        List<String> atoms = new ArrayList<>();
        List<String> data = readFromFile(fileName);
        Pattern pattern = Pattern.compile(ATOM_REGEX);

        for (String line : data) {
            Matcher matcher = pattern.matcher(line);
            while (matcher.find()) {
                atoms.add(matcher.group());
            }
        }
        return atoms;
    }
}
