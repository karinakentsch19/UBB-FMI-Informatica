import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
public class Main {

    public static void generazaFisiere(int numarTari) {
        int ID = 0;
        Random rand = new Random();

        String folderPath = "Rezultate";
        File folder = new File(folderPath);
        if (!folder.exists()) {
            if (folder.mkdir()) {
                System.out.println("Folder created: " + folderPath);
            } else {
                System.out.println("Failed to create folder: " + folderPath);
                return;
            }
        }

        for (int i = 1; i <= numarTari; i++) {
            int nrConcurenti = rand.nextInt(80, 100);

            Map<Integer, FileWriter> problemWriters = new HashMap<>();
            try {
                for (int problema = 1; problema <= 10; problema++) {
                    String fileName = "RezultateC" + i + "P" + problema + ".txt";
                    File file = new File(folder, fileName);
                    problemWriters.put(problema, new FileWriter(file));
                }

                for (int concurent = 0; concurent < nrConcurenti; concurent++, ID++) {
                    for (int problema = 1; problema <= 10; problema++) {
                        double r = rand.nextDouble();

                        int punctaj;
                        if (r < 0.10) {
                            punctaj = 0;
                        } else if (r < 0.12) {
                            punctaj = -1;
                        } else {
                            punctaj = rand.nextInt(10) + 1;
                        }

                        FileWriter writer = problemWriters.get(problema);
                        writer.write(ID + "," + punctaj + System.lineSeparator());
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                for (FileWriter writer : problemWriters.values()) {
                    try {
                        if (writer != null) {
                            writer.close();
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    public static void secvential(int numarTari) throws FileNotFoundException {
        Set<Pair> clasament = new TreeSet<>();
        List<Integer> persoaneDescalificate = new ArrayList<>();
        Queue<Pair> listaPunctaje = new LinkedList<>();
        Map<Integer, Integer> punctaje = new HashMap<>();

        String folderPath = "Rezultate";
        File folder = new File(folderPath);

        for (int tara = 1; tara < numarTari; tara++) {
            for (int problema = 1; problema <= 10; problema++) {
                String fileName = "RezultateC" + tara + "P" + problema + ".txt";
                File file = new File(folder, fileName);
                Scanner scanner = new Scanner(file);

                while (scanner.hasNextLine()) {
                    String line = scanner.nextLine();
                    String[] itemsOnLine = line.split(",");
                    if (itemsOnLine.length == 2) {
                        Pair pair = new Pair(Integer.parseInt(itemsOnLine[0]), Integer.parseInt(itemsOnLine[1]));
                        listaPunctaje.add(pair);
                    }
                }
            }
        }

        for (Pair pair: listaPunctaje){
            if (pair.second == -1){
                persoaneDescalificate.add(pair.first);
                punctaje.remove(pair.first);
            }
            else{
                if (!persoaneDescalificate.contains(pair.first)){
                    if (punctaje.containsKey(pair.first)){
                        int old = punctaje.get(pair.first);
                        punctaje.put(pair.first, old + pair.second);
                    }
                    else{
                        punctaje.put(pair.first, pair.second);
                    }
                }
            }
        }

        for (Integer id: punctaje.keySet()){
            clasament.add(new Pair(id, punctaje.get(id)));
        }

        try (FileWriter writer = new FileWriter("ClasamentSecvential.csv")) {
            writer.write("Concurent,Punctaj" + System.lineSeparator());
            for (Pair concurent : clasament) {
                writer.write(concurent.getFirst() + "," + concurent.getSecond() + System.lineSeparator());

            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void paralel(int numarTari, int numarThreaduri, int numarReaderi) {
        Thread[] readers = new Thread[numarReaderi];
        Thread[] consumers = new Thread[numarThreaduri - numarReaderi];
        ThreadLinkedList clasament = new ThreadLinkedList();
        ThreadQueue punctaje = new ThreadQueue(numarReaderi);

        int chunkSize = numarTari / numarReaderi;

        for (int i = 0; i < numarReaderi; i++) {
            int threadIndex = i;
            readers[i] = new Thread(() -> {
                int start = (threadIndex + 1) * chunkSize - 1;
                int end;
                if (threadIndex == numarReaderi - 1)
                    end = numarTari + 1;
                else
                    end = start + chunkSize;

                String folderPath = "Rezultate";
                File folder = new File(folderPath);

                for (int tara = start; tara < end; tara++){
                    for (int problema = 1; problema <= 10; problema++){
                        String fileName = "RezultateC" + tara + "P" + problema + ".txt";
                        File file = new File(folder, fileName);
                        Scanner scanner = null;
                        try{
                            scanner = new Scanner(file);
                        } catch (FileNotFoundException e) {
                            throw new RuntimeException(e);
                        }

                        while(scanner.hasNextLine()){
                            String line = scanner.nextLine();
                            String[] tokens = line.split(",");
                            if (tokens.length == 2){
                                Integer id = Integer.valueOf(tokens[0]);
                                Integer punctajProblema = Integer.valueOf(tokens[1]);
                                try{
                                    punctaje.produce(new Pair(id, punctajProblema));
                                } catch (InterruptedException e) {
                                    throw new RuntimeException(e);
                                }
                            }
                        }
                    }
                }
                punctaje.finish();
            });
            readers[i].start();
        }

        for (int i = 0; i < numarThreaduri - numarReaderi; i++){
            consumers[i] = new Thread(() ->{
               while (punctaje.hasValue()){
                   try{
                       Pair pair = punctaje.consume();
                       if (pair != null){
                           clasament.add(pair);
                       }
                   } catch (InterruptedException e) {
                       throw new RuntimeException(e);
                   }
               }
            });
            consumers[i].start();
        }

        for (int i = 0; i < numarReaderi; i++){
            try{
                readers[i].join();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

        for (int i = 0; i < numarThreaduri - numarReaderi; i++){
            try{
                consumers[i].join();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

        try (FileWriter writer = new FileWriter("ClasamentParalel.csv")) {
            writer.write("Concurent,Punctaj" + System.lineSeparator());
            for (Pair concurent : clasament.clasament()) {
                writer.write(concurent.getFirst() + "," + concurent.getSecond() + System.lineSeparator());

            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    public static void main(String[] args) throws FileNotFoundException {
//        long start = System.currentTimeMillis();
//        secvential(5);
//        long end = System.currentTimeMillis();
//        System.out.println("Time: " + (end - start));

        long start = System.currentTimeMillis();
        paralel(5, 4, 2);
        long end = System.currentTimeMillis();
        System.out.println("Time: " + (end - start));
    }
}