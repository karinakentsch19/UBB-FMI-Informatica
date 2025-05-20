package org.example;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class IdentificatorAtomiLexicali {
    private static final String ATOM_REGEX = "\\b(start|end|read|write|if|else|while|struct|int|bool|float)\\b|<<|>>|==|!=|&&|\\|\\||[{}();,=+\\-/*%<>!&|]|[a-zA-Z][a-zA-Z0-9]*|[0-9]+[.]?[0-9]*";

    public List<String> identificaAtomiLexicali(List<String> data) {
        List<String> atoms = new ArrayList<>();
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
