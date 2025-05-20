#include <iostream>
#include <cstdlib>
#include <ctime>
#include <cstring>
#include <fstream>
#include <chrono>
#include <thread>
#include <mutex>
#include <condition_variable>

using namespace std;

const int MAX = 10010;
const int MAX_CONV = 10;
const int MAX_NOU = 10010;

/**
 * Aplica o matrice de convolutie asupra unui element specific din matricea principala
 * @param matrice matricea originala pe care se aplica convolutia
 * @param matriceConvolutie matricea de convolutie (filtru) utilizata pt transformare
 * @param k dimensiunea matricii de convolutie
 * @param linieElement linia elementului central asupra caruia se aplica convolutia
 * @param coloanaElement coloana elementului central asupra caruia se aplica convolutia
 * @return valoarea noua a elementului rezultat in urma aplicarii convolutiei
 */
int aplicareConvolutie(int **matrice, int **matriceConvolutie, int k, int linieElement,
                       int coloanaElement) {
    int elementNou = 0;
    for (int i = 0; i < k; i++)
        for (int j = 0; j < k; j++)
            elementNou += matrice[linieElement - k / 2 + i][coloanaElement - k / 2 + j] * matriceConvolutie[i][j];
    return elementNou;
}

/**
 * Creeaza o matrice bordata prin adaugarea de randuri si coloane suplimentare la margini
 * Elementele de pe bordura sunt replicate din marginea matricii originale
 * @param matrice matricea originala
 * @param n numarul de linii al matricii originale
 * @param m numarul de coloane al matrciii originale
 * @param k dimensiunea matricii de convolutie
 * @return matricea bordata
 */
void bordareMatrice(int **matrice, int n, int m, int k) {
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
 * Afiseaza o matrice
 * @param matrice matrice
 * @param n numarul de linii al matricii
 * @param m numarul de coloane al matricii
 */
void afisareMatrice(int **matrice, int n, int m) {
    for (int i = 0; i < n; i++) {
        for (int j = 0; j < m; j++)
            cout << matrice[i][j] << " ";
        cout << endl;
    }
}


void genereazaMatriceRandom(int n, int m, int **matrice, int k, int **matriceConvolutie,
                            string numeFisier) {
    ofstream fout(numeFisier);

    srand(static_cast<unsigned int>(time(0)));

    for (int i = 0; i < n; i++)
        for (int j = 0; j < m; j++) {
            matrice[i][j] = rand() % 100 + 1;
        } //numere intre 0 si 99

    for (int i = 0; i < k; i++)
        for (int j = 0; j < k; j++) {
            matriceConvolutie[i][j] = rand() % 100 + 1;
        } //numere intre 0 si 99

    fout << n << " " << m << endl;
    for (int i = 0; i < n; i++) {
        for (int j = 0; j < m; j++)
            fout << matrice[i][j] << " ";
        fout << endl;
    }

    fout << k << endl;
    for (int i = 0; i < k; i++) {
        for (int j = 0; j < k; j++)
            fout << matriceConvolutie[i][j] << " ";
        fout << endl;
    }
    fout.close();
}

/**
 * Parcurge matricea originala secvential si aplica matricea de convolutie asupra fiecarui element
 * @param matrice matricea bordata
 * @param n numarul de linii al matricii originale
 * @param m numarul de coloane al matricii originale
 * @param matriceConvolutie matricea de convolutie
 * @param k dimensiunea matricii de convolutie
 * @param matriceTransformata matricea rezultata prin aplicarea convolutiei
 */
void parcurgereSecventialaInplance(int **matrice, int n, int m, int **matriceConvolutie, int k) {

    int *linieTemporara = new int[m];

    for (int i = k / 2; i < n + k / 2; i++) {
        //Aplicam convolutia pe linia curenta si stocam rezultatele in linieTemporara
        for (int j = k / 2; j < m + k / 2; j++) {
            linieTemporara[j - k / 2] = aplicareConvolutie(matrice, matriceConvolutie, k, i, j);
        }
        // Copiem rezultatele din linieTemporara în matricea originală
        for (int j = 0; j < m; j++) {
            matrice[i - k / 2][j] = linieTemporara[j];
        }
    }

}

void citire(int **matrice, int &n, int &m, int **matriceConvolutie, int &k,
            string fileName) {
    ifstream fin(fileName);

    fin >> n >> m;
    for (int i = 0; i < n; i++)
        for (int j = 0; j < m; j++)
            fin >> matrice[i][j];

    fin >> k;
    for (int i = 0; i < k; i++)
        for (int j = 0; j < k; j++)
            fin >> matriceConvolutie[i][j];
    fin.close();
}

void scrieInFisier(int **matrice, int n, int m, string fileName) {
    ofstream fout(fileName);
    fout << n << " " << m << endl;
    for (int i = 0; i < n; i++) {
        for (int j = 0; j < m; j++)
            fout << matrice[i][j] << " ";
        fout << endl;
    }
    fout.close();
}

class Barrier {
private:
    std::mutex mtx;
    std::condition_variable cv;
    int count;
    int tripCount;
    int generation;

public:
    explicit Barrier(int n) : count(0), tripCount(n), generation(0) {}

    void wait() {
        std::unique_lock<std::mutex> lock(mtx);
        int currentGeneration = generation; // Capture the current cycle
        count++;
        if (count == tripCount) {
            count = 0; // Reset for the next round
            generation++; // Move to the next cycle
            cv.notify_all(); // Wake all waiting threads
        } else {
            cv.wait(lock, [this, currentGeneration] { return currentGeneration != generation; });
        }
    }
};

void parcurgereParalelaLinie(int **matrice, int n, int m, int **matriceConvolutie, int k, int nrThreaduri) {
    Barrier barrier(nrThreaduri);
    thread *threads = new thread[nrThreaduri]; // Dynamically allocate thread array

    for (int t = 0; t < nrThreaduri; t++) {
        threads[t] = thread([&, t](int start, int step) {
            int **auxVec = new int *[n + k - 1];
            for (int i = 0; i < n + k - 1; i++) {
                auxVec[i] = new int[m + k - 1];
            }

            for (int i = start; i < n; i += step) {
                for (int j = 0; j < m; j++) {
                    auxVec[i + k / 2][j + k / 2] = matrice[i][j];
                }
            }

            barrier.wait();
            for (int i = start + k / 2; i < n + k / 2; i += step) {
                for (int j = k / 2; j < m + k / 2; j++) {
                    int result = 0;
                    for (int u = 0; u < k; u++) {
                        for (int v = 0; v < k; v++) {
                            result += auxVec[i - k / 2 + u][j - k / 2 + v] * matriceConvolutie[u][v];
                        }
                    }
                    matrice[i - k / 2][j - k / 2] = result;
                }
            }

            for (int i = 0; i < n + k - 1; i++) {
                delete[] auxVec[i];
            }
            delete[] auxVec;
        }, t, nrThreaduri);
    }

    for (int i = 0; i < nrThreaduri; i++) {
        threads[i].join();
    }

    delete[] threads; // Clean up threads array
}


int main(int argc, char *argv[]) {
    int n, m, k;
    int **matrice = new int *[MAX];
    for (int i = 0; i < MAX; i++) {
        matrice[i] = new int[MAX];
    }
    int **matriceConvolutie = new int *[MAX_CONV];
    for (int i = 0; i < MAX_CONV; i++) {
        matriceConvolutie[i] = new int[MAX_CONV];
    }

    citire(matrice, n, m, matriceConvolutie, k, "data.txt");
    bordareMatrice(matrice, n, m, k);

    string numarThreads = argv[1];
    string outputFile = argv[2];
    string type;
    if (strcmp(argv[3], "0") == 0) {
        type = "secvential";
    } else if (strcmp(argv[3], "1") == 0) {
        type = "paralel linii";
    }

    if (type == "secvential") {
        auto start = chrono::high_resolution_clock::now();
        parcurgereSecventialaInplance(matrice, n, m, matriceConvolutie, k);
        auto end = chrono::high_resolution_clock::now();
        auto duration = chrono::duration_cast<chrono::microseconds>(end - start);
        cout << duration.count();
        scrieInFisier(matrice, n, m, outputFile);

    } else if (type == "paralel linii") {
        auto start = chrono::high_resolution_clock::now();
        parcurgereParalelaLinie(matrice, n, m, matriceConvolutie, k, stoi(numarThreads));
        auto end = chrono::high_resolution_clock::now();
        auto duration = chrono::duration_cast<chrono::microseconds>(end - start);
        cout << duration.count();
        scrieInFisier(matrice, n, m, outputFile);
    }

    for (int i = 0; i < MAX; i++) {
        delete[] matrice[i];
    }
    delete[] matrice;
    for (int i = 0; i < MAX_CONV; i++) {
        delete[] matriceConvolutie[i];
    }
    delete[] matriceConvolutie;


}

//int main() {
//    int n = 10000, m = 10000, k = 3;
//    int **matrice = new int*[MAX];
//    for (int i = 0; i < MAX; ++i){
//        matrice[i] = new int[MAX];
//    }
//
//    int **matriceConvolutie = new int*[MAX_CONV];
//    for (int i = 0; i < MAX_CONV; ++i){
//        matriceConvolutie[i] = new int[MAX_CONV];
//    }
//
//    genereazaMatriceRandom(n, m, matrice, k, matriceConvolutie, "data.txt");
//
//    for (int i = 0; i < MAX; ++i){
//        delete[] matrice[i];
//    }
//    delete[] matrice;
//    for (int i = 0; i < MAX_CONV; ++i){
//        delete[] matriceConvolutie[i];
//    }
//    delete[] matriceConvolutie;
//    return 0;
//}
