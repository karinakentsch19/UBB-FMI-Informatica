import java.io.*;
import java.util.Objects;
import java.util.Random;
import java.util.Scanner;
import java.util.concurrent.CyclicBarrier;

public class Main {

    /**
     * Aplica o matrice de convolutie asupra unui element specific din matricea principala
     *
     * @param matrice           matricea originala pe care se aplica convolutia
     * @param matriceConvolutie matricea de convolutie (filtru) utilizata pt transformare
     * @param k                 dimensiunea matricii de convolutie
     * @param linieElement      linia elementului central asupra caruia se aplica convolutia
     * @param coloanaElement    coloana elementului central asupra caruia se aplica convolutia
     * @return valoarea noua a elementului rezultat in urma aplicarii convolutiei
     */
    public static int aplicareConvolutie(int[][] matrice, int[][] matriceConvolutie, int k, int linieElement, int coloanaElement) {
        int elementNou = 0;
        for (int i = 0; i < k; i++)
            for (int j = 0; j < k; j++) {
                elementNou += matrice[linieElement - k / 2 + i][coloanaElement - k / 2 + j] * matriceConvolutie[i][j];
            }
        return elementNou;
    }

    /**
     * Adauga o bordura unei matrici de dimensiune nxm pentru a permite aplicarea convolutiei
     *
     * @param matrice matricea initiala care va fi bordata
     * @param n       numar linii
     * @param m       numar coloane
     * @param k       dimensiunea matricei de convolutie
     */
    public static void bordareMatrice(int[][] matrice, int n, int m, int k) {
        //parte centrala
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                matrice[i + k / 2][j + k / 2] = matrice[i][j];
            }
        }

        // bordura de nord
        for (int i = 0; i < k / 2; i++) {
            for (int j = 0; j < m; j++) {
                matrice[i][j + k / 2] = matrice[0][j];
            }
        }

        // bordura de sud
        for (int i = 0; i < k / 2; i++) {
            for (int j = 0; j < m; j++) {
                matrice[n + k / 2 + i][j + k / 2] = matrice[n - 1][j];
            }
        }

        // bordura de vest
        for (int i = 0; i < n + 2 * k / 2; i++) {
            for (int j = 0; j < k / 2; j++) {
                matrice[i][j] = matrice[i][k / 2];
            }
        }

        // bordura de est
        for (int i = 0; i < n + 2 * k / 2; i++) {
            for (int j = 0; j < k / 2; j++) {
                matrice[i][m + k / 2 + j] = matrice[i][m + k / 2 - 1];
            }
        }
    }


    /**
     * Aplica operatia de convolutie asupra matricii
     *
     * @param matrice           matricea initiala care va fi modificata prin convolutie
     * @param n                 numar linii
     * @param m                 numar coloane
     * @param matriceConvolutie matricea de convolutie
     * @param k                 dimensiunea matricei de convolutie
     */
    public static void parcurgereSecventialaInplace(int[][] matrice, int n, int m, int[][] matriceConvolutie, int k) {
        int[] linieTemporara = new int[m]; // Vector temporar de dimensiune m

        for (int i = k / 2; i < n + k / 2; i++) {
            // Aplicăm convoluția pe linia curentă și stocăm rezultatele în linieTemporara
            for (int j = k / 2; j < m + k / 2; j++) {
                linieTemporara[j - k / 2] = aplicareConvolutie(matrice, matriceConvolutie, k, i, j);
            }

            // Copiem rezultatele din linieTemporara în matricea originală
            for (int j = 0; j < m; j++) {
                matrice[i - k / 2][j] = linieTemporara[j];
            }
        }
    }


    /**
     * Afiseaza o matrice
     *
     * @param matrice matrice
     * @param n       numarul de linii al matricii
     * @param m       numarul de coloane al matricii
     */
    public static void afisareMatrice(int[][] matrice, int n, int m) {
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++)
                System.out.print(matrice[i][j] + " ");
            System.out.println();
        }
    }

    /**
     * Genereaza o matrice si o matrice de convolutie cu valori random si le salveaza in fisier
     *
     * @param n          numarul de linii ale matricii principale
     * @param m          numarul de coloane ale matricii principale
     * @param k          dimensiunea matricii de convolutie
     * @param numeFisier numele fisierului in care se salveaza
     */
    public static void genereazaMatriceRandom(int n, int m, int k, String numeFisier) {
        int[][] matrice = new int[n][m];
        Random rand = new Random();
        for (int i = 0; i < n; i++)
            for (int j = 0; j < m; j++)
                matrice[i][j] = rand.nextInt(6000);


        int[][] convolutia = new int[k][k];
        for (int i = 0; i < k; i++)
            for (int j = 0; j < k; j++)
                convolutia[i][j] = rand.nextInt(6000);

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(numeFisier))) {
            writer.write(n + " " + m);
            writer.newLine();
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < m; j++) {
                    writer.write(matrice[i][j] + " ");
                }
                writer.newLine(); // move to the next line after each row
            }
            writer.write(k + " ");
            writer.newLine();
            for (int i = 0; i < k; i++) {
                for (int j = 0; j < k; j++) {
                    writer.write(convolutia[i][j] + " ");
                }
                writer.newLine(); // move to the next line after each row
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Clasa utilizata pentru aplicarea convolutiei paralele pe liniile/coloanele matricii
     * start = linia/coloana de inceput pt fiecare thread
     * step = pasul/numarul de linii/coloane sarite intre threaduri
     * n,m,k = dimensiuni matrici
     * matrice = matricea originala bordata
     * matriceConvolutie = matricea de convolutie
     * matriceRezultat = matricea rezultata
     * <p>
     * Fiecare thread aplica convolutia pe liniile/coloanele din matrice in functie de parametrii start si step
     */
    static class PersonalThread extends Thread {
        private int start;

        private int step;

        private int n, m, k;

        private int[][] matrice;

        private int[][] matriceConvolutie;

        private int[] vecini;

        private static CyclicBarrier barrier;

        public PersonalThread(int start, int step, int n, int m, int k, int[][] matrice, int[][] matriceConvolutie, CyclicBarrier barrier) {
            this.start = start;
            this.step = step;
            this.n = n;
            this.m = m;
            this.k = k;
            this.matrice = matrice;
            this.matriceConvolutie = matriceConvolutie;
            this.barrier = barrier;
            this.vecini = new int[(k * k)];
        }


        @Override
        public void run() {
            // Copiem vecinii necesari pentru calculul convoluției
            for (int i = start + k / 2; i < n + k / 2; i += step) {
                for (int j = k / 2; j < m + k / 2; j++) {
                    // Copiem elementele necesare din matricea originală în vecini
                    for (int ki = -k / 2; ki <= k / 2; ki++) {
                        for (int kj = -k / 2; kj <= k / 2; kj++) {
                            vecini[(ki + k / 2) * k + (kj + k / 2)] = matrice[i + ki][j + kj];
                        }
                    }
                    // Așteptăm celelalte thread-uri să termine copierea
                    try {
                        barrier.await(); // Bariera de sincronizare
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    // Acum putem aplica convoluția folosind vecinii
                    matrice[i - k / 2][j - k / 2] = aplicareConvolutieVecini(vecini, matriceConvolutie, k);
                }
            }
        }

        // Metoda pentru aplicarea convoluției folosind vecinii
        private int aplicareConvolutieVecini(int[] vecini, int[][] matriceConvolutie, int k) {
            int suma = 0;
            for (int ki = 0; ki < k; ki++) {
                for (int kj = 0; kj < k; kj++) {
                    suma += vecini[ki * k + kj] * matriceConvolutie[ki][kj];
                }
            }
            return suma;
        }

    }

    /**
     * Aplica convolutia asupra unei matrice in mod paralel utilizand mai multe threaduri
     *
     * @param matrice           matricea bordata
     * @param n                 numar de linii al matricii originale
     * @param m                 numar de coloane al matricii originale
     * @param matriceConvolutie matricea de convolutie
     * @param k                 dimensiunea matricii de convolutie
     * @param nrThreaduri       numar de threaduri care se executa paralel
     * @return matricea rezultata dupa aplicarea convolutiei in mod paralel
     * @throws InterruptedException
     */
    public static void parcurgereParalela(int[][] matrice, int n, int m, int[][] matriceConvolutie, int k, int nrThreaduri) throws InterruptedException {
        PersonalThread[] threads = new PersonalThread[nrThreaduri];
        CyclicBarrier barrier = new CyclicBarrier(nrThreaduri);

        for (int j = 0; j < nrThreaduri; j++) {
            threads[j] = new PersonalThread(j, nrThreaduri, n, m, k, matrice, matriceConvolutie, barrier);
            threads[j].start();
        }

        for (int j = 0; j < nrThreaduri; j++)
            threads[j].join();
    }

    /**
     * Scrie continutul unei matrice in fisier
     *
     * @param matrice    matrice
     * @param n          numar linii matrice
     * @param m          numar coloane matrice
     * @param numeFisier numele fisierului in care se scrie matricea
     */
    public static void scrieInFisier(int[][] matrice, int n, int m, String numeFisier) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(numeFisier))) {
            writer.write(n + " " + m);
            writer.newLine();
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < m; j++) {
                    writer.write(matrice[i][j] + " ");
                }
                writer.newLine(); // move to the next line after each row
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Citeste datele din fisier, aplica convolutia in mod secvential/paralel si scrie rezultatul in fisier
     *
     * @param args args[0] = nr threaduri args[1] = numele fisierului de iesire args[2] = tipul de parcurgere
     * @throws FileNotFoundException
     * @throws InterruptedException
     */
    public static void main(String[] args) throws FileNotFoundException, InterruptedException {
        File file = new File("data.txt");
        Scanner scanner = new Scanner(file);

        int n, m, k;
        int[][] matrice = new int[10010][10010];
        int[][] matriceConvolutie = new int[100][100];
        n = scanner.nextInt();
        m = scanner.nextInt();
        for (int i = 0; i < n; i++)
            for (int j = 0; j < m; j++)
                matrice[i][j] = scanner.nextInt();

        k = scanner.nextInt();
        for (int i = 0; i < k; i++)
            for (int j = 0; j < k; j++)
                matriceConvolutie[i][j] = scanner.nextInt();

        scanner.close();

        bordareMatrice(matrice, n, m, k);


        String numarThreads = args[0];
        String outputFile = args[1];
        String type = Objects.equals(args[2], "0") ? "secvential" : "paralel";

        if (type.equals("secvential")) {
            long start = System.currentTimeMillis();
            parcurgereSecventialaInplace(matrice, n, m, matriceConvolutie, k);
            long end = System.currentTimeMillis();
            System.out.println(end - start);
            scrieInFisier(matrice, n, m, outputFile);
        } else {
            long start = System.currentTimeMillis();
            parcurgereParalela(matrice, n, m, matriceConvolutie, k, Integer.parseInt(numarThreads));
            long end = System.currentTimeMillis();
            System.out.println(end - start);
            scrieInFisier(matrice, n, m, outputFile);
        }
    }

//    public static void main(String[] args) throws FileNotFoundException {
//        int n = 10000, m = 10000, k = 3;
//        genereazaMatriceRandom(n, m, k, "data.txt");
//    }
}