//

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

class InvalidTokenException extends Exception {
    private final List<String> errors;

    public InvalidTokenException(List<String> errors) {
        super("Invalid tokens found");
        this.errors = errors;
    }

    public List<String> getErrors() {
        return errors;
    }

    @Override
    public String getMessage() {
        StringBuilder stringBuilder = new StringBuilder(super.getMessage()).append(":\n");
        for (String error : errors) {
            stringBuilder.append(error).append("\n");
        }
        return stringBuilder.toString();
    }
}
//
//public class IdentificatorAtomiLexicali {
//    private static final String ATOM_REGEX = "\\b(start|end|read|write|if|else|while|struct|int|bool|float)\\b|<<|>>|==|!=|&&|\\|\\||[{}();,=+\\-/*%<>!]|\\b[a-zA-Z][a-zA-Z0-9]*\\b|\\b[0-9]+[.]?[0-9]*\\b";
//    private static final String TOKEN_REGEX = "\\S+";
//
//    public List<String> identificaAtomiLexicali(List<String> data) throws InvalidTokenException {
//        List<String> atoms = new ArrayList<>();
//        Pattern pattern = Pattern.compile(ATOM_REGEX);
//        List<String> errors = new ArrayList<>();
//
////        for (String line : data) {
////            Matcher matcher = pattern.matcher(line);
////            while (matcher.find()) {
////                atoms.add(matcher.group());
////            }
////        }
//
//        for (int i = 0; i < data.size(); i++){
//            String line = data.get(i);
//
//            Matcher matcher = pattern.matcher(line);
//            int lastMatchEnd = 0;
//
//            while(matcher.find()){
//                if (matcher.start() > lastMatchEnd){
//                    String invalidSegment = line.substring(lastMatchEnd, matcher.start()).trim();
//                    if (!invalidSegment.isEmpty()){
//                        errors.add("Line " + (i+1) + ": Invalid token: " + invalidSegment);
//                    }
//                }
//                atoms.add(matcher.group());
//                lastMatchEnd = matcher.end();
//            }
//            if (lastMatchEnd < line.length()){
//                String invalidSegment = line.substring(lastMatchEnd).trim();
//                if (!invalidSegment.isEmpty()){
//                    errors.add("Line " + (i+1) + ": Invalid token: " + invalidSegment);
//                }
//            }
//        }
//        if (!errors.isEmpty()) {
//            throw new InvalidTokenException(errors);
//        }
//        return atoms;
//    }
//}



public class IdentificatorAtomiLexicali {

    private static final List<String> KEYWORDS = Arrays.asList(
            "start", "end", "read", "write", "if", "else", "while", "struct", "int", "bool", "float"
    );

    private static final List<String> OPERATORS = Arrays.asList(
            "<<", ">>", "==", "!=", "&&", "||", "{", "}", "(", ")", ";", ",", "=", "+", "-", "/", "*", "%", "<", ">", "!"
    );

    public List<String> identificaAtomiLexicali(List<String> data) throws InvalidTokenException {
        List<String> atoms = new ArrayList<>();
        List<String> errors = new ArrayList<>();
        AutomatFinit automatFinitID = new AutomatFinit("automatFinitID.txt");
        AutomatFinit automatFinitConstanteIntregi = new AutomatFinit("automatFinitConstanteIntregi.txt");
        AutomatFinit automatFinitConstanteReale = new AutomatFinit("automatFinitConstanteReale.txt");

        for (int i = 0; i < data.size(); i++) {
            String line = data.get(i);
            int lastMatchEnd = 0;

            for (int j = 0; j < line.length(); ) {
                char currentChar = line.charAt(j);
                StringBuilder token = new StringBuilder();

                if (Character.isWhitespace(currentChar)) {
                    j++;
                    continue;
                }

                if (Character.isLetter(currentChar) || Character.isDigit(currentChar)) {
                    while (j < line.length() && (Character.isLetterOrDigit(line.charAt(j)) || line.charAt(j) == '.')) {
                        token.append(line.charAt(j));
                        j++;
                    }
                    String tokenStr = token.toString();
                    if (KEYWORDS.contains(tokenStr) || automatFinitID.verificaSecventa(tokenStr) || automatFinitConstanteReale.verificaSecventa(tokenStr) || automatFinitConstanteIntregi.verificaSecventa(tokenStr)) {
                        atoms.add(tokenStr);
                    } else {
                        errors.add("Line " + (i + 1) + ": Invalid token: " + tokenStr);
                    }
                    lastMatchEnd = j;
                    continue;
                }

                boolean matched = false;
                for (String op : OPERATORS) {
                    if (line.startsWith(op, j)) {
                        atoms.add(op);
                        j += op.length();
                        matched = true;
                        break;
                    }
                }

                if (!matched) {
                    token.append(currentChar);
                    j++;
                    errors.add("Line " + (i + 1) + ": Invalid token: " + token.toString());
                }

                lastMatchEnd = j;
            }

            if (lastMatchEnd < line.length()) {
                String invalidSegment = line.substring(lastMatchEnd).trim();
                if (!invalidSegment.isEmpty()) {
                    errors.add("Line " + (i + 1) + ": Invalid token: " + invalidSegment);
                }
            }
        }

        if (!errors.isEmpty()) {
            throw new InvalidTokenException(errors);
        }

        return atoms;
    }
}
