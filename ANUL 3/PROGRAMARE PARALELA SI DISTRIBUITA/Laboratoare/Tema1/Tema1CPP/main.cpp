#include <iostream>
#include <cstdlib>
#include <ctime>
#include <cstring>
#include <fstream>
#include <chrono>
#include <thread>

using namespace std;

const int MAX = 10010;
const int MAX_CONV = 10;
const int MAX_NOU = 10010;
int matrice[MAX][MAX] = {0}, matriceBordata[MAX][MAX] = {0}, matriceConvolutie[MAX_CONV][MAX_CONV] = {0}, matriceNoua[MAX_NOU][MAX_NOU] = {0};

/**
 * Aplica o matrice de convolutie asupra unui element specific din matricea principala
 * @param matrice matricea originala pe care se aplica convolutia
 * @param matriceConvolutie matricea de convolutie (filtru) utilizata pt transformare
 * @param k dimensiunea matricii de convolutie
 * @param linieElement linia elementului central asupra caruia se aplica convolutia
 * @param coloanaElement coloana elementului central asupra caruia se aplica convolutia
 * @return valoarea noua a elementului rezultat in urma aplicarii convolutiei
 */
int aplicareConvolutie(int matrice[MAX][MAX], int matriceConvolutie[MAX_CONV][MAX_CONV], int k, int linieElement,
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
 * @param matriceBordata matricea rezultata in urma bordarii
 * @return matricea bordata
 */
void bordareMatrice(int matrice[MAX][MAX], int n, int m, int k, int (&matriceBordata)[MAX][MAX]) {
    //parte centrala
    for (int i = 0; i < n; i++) {
        for (int j = 0; j < m; j++) {
            matriceBordata[i + k / 2][j + k / 2] = matrice[i][j];
        }
    }

    // bordura de nord
    for (int i = 0; i < k / 2; i++) {
        for (int j = 0; j < m; j++) {
            matriceBordata[i][j + k / 2] = matrice[0][j];
        }
    }

    // bordura de sud
    for (int i = 0; i < k / 2; i++) {
        for (int j = 0; j < m; j++) {
            matriceBordata[n + k / 2 + i][j + k / 2] = matrice[n - 1][j];
        }
    }

    // bordura de vest
    for (int i = 0; i < n + 2 * k / 2; i++) {
        for (int j = 0; j < k / 2; j++) {
            matriceBordata[i][j] = matriceBordata[i][k / 2];
        }
    }

    // bordura de est
    for (int i = 0; i < n + 2 * k / 2; i++) {
        for (int j = 0; j < k / 2; j++) {
            matriceBordata[i][m + k / 2 + j] = matriceBordata[i][m + k / 2 - 1];
        }
    }
}

/**
 * Afiseaza o matrice
 * @param matrice matrice
 * @param n numarul de linii al matricii
 * @param m numarul de coloane al matricii
 */
void afisareMatrice(int matrice[MAX][MAX], int n, int m) {
    for (int i = 0; i < n; i++) {
        for (int j = 0; j < m; j++)
            cout << matrice[i][j] << " ";
        cout << endl;
    }
}


void genereazaMatriceRandom(int n, int m, int (&matrice)[MAX][MAX], int k, int (&matriceConvolutie)[MAX_CONV][MAX_CONV],
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
void parcurgereSecventiala(int matrice[MAX][MAX], int n, int m, int matriceConvolutie[MAX_CONV][MAX_CONV], int k,
                           int (&matriceTransformata)[MAX_NOU][MAX_NOU]) {
    for (int i = k / 2; i < n + k / 2; i++)
        for (int j = k / 2; j < m + k / 2; j++) {
            matriceTransformata[i - k / 2][j - k / 2] = aplicareConvolutie(matrice, matriceConvolutie, k, i, j);
        }
}

void citire(int (&matrice)[MAX][MAX], int &n, int &m, int (&matriceConvolutie)[MAX_CONV][MAX_CONV], int &k,
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

void scrieInFisier(int matrice[MAX_NOU][MAX_NOU], int n, int m, string fileName) {
    ofstream fout(fileName);
    fout << n << " " << m << endl;
    for (int i = 0; i < n; i++) {
        for (int j = 0; j < m; j++)
            fout << matrice[i][j] << " ";
        fout << endl;
    }
    fout.close();
}

void parcurgereParalelaColoana(int matrice[MAX][MAX], int n, int m, int matriceConvolutie[MAX_CONV][MAX_CONV], int k,
                               int (&matriceTransformata)[MAX_NOU][MAX_NOU], int nrThreaduri) {
    thread *threads = new thread[nrThreaduri];
    for (int i = 0; i < nrThreaduri; i++) {
        threads[i] = thread([&](int start, int pas) {
            for (int j = start + k / 2; j < m + k / 2; j += pas)
                for (int i = k / 2; i < n + k / 2; i++)
                    matriceTransformata[i - k / 2][j - k / 2] = aplicareConvolutie(matrice, matriceConvolutie, k, i, j);
        }, i, nrThreaduri);

    }
    for (int i = 0; i < nrThreaduri; i++)
        threads[i].join();
}

void parcurgereParalelaLinie(int matrice[MAX][MAX], int n, int m, int matriceConvolutie[MAX_CONV][MAX_CONV], int k,
                             int (&matriceTransformata)[MAX_NOU][MAX_NOU], int nrThreaduri) {
    thread *threads = new thread[nrThreaduri];
    for (int i = 0; i < nrThreaduri; i++) {
        threads[i] = thread([&](int start, int pas) {
            for (int i = start + k / 2; i < n + k / 2; i += pas)
                for (int j = k / 2; j < m + k / 2; j++)
                    matriceTransformata[i - k / 2][j - k / 2] = aplicareConvolutie(matrice, matriceConvolutie, k, i, j);
        }, i, nrThreaduri);

    }
    for (int i = 0; i < nrThreaduri; i++)
        threads[i].join();
}

int main(int argc, char *argv[]) {
    int n, m, k;
//    int matrice[MAX][MAX] = {0}, matriceBordata[MAX][MAX] = {0}, matriceConvolutie[MAX_CONV][MAX_CONV] = {
//            0}, matriceNoua[MAX_NOU][MAX_NOU] = {0};

    citire(matrice, n, m, matriceConvolutie, k, "data.txt");
    bordareMatrice(matrice, n, m, k, matriceBordata);

    string numarThreads = argv[1];
    string outputFile = argv[2];
    string type;
    if (strcmp(argv[3], "0") == 0) {
        type = "secvential";
    } else if (strcmp(argv[3], "1") == 0) {
        type = "paralel coloane";
    } else type = "paralel linii";

    if (type == "secvential") {
        auto start = chrono::high_resolution_clock::now();
        parcurgereSecventiala(matriceBordata, n, m, matriceConvolutie, k, matriceNoua);
        auto end = chrono::high_resolution_clock::now();
        auto duration = chrono::duration_cast<chrono::microseconds>(end - start);
        cout << duration.count();
        scrieInFisier(matriceNoua, n, m, outputFile);

    } else if (type == "paralel coloane") {
        auto start = chrono::high_resolution_clock::now();
        parcurgereParalelaColoana(matriceBordata, n, m, matriceConvolutie, k, matriceNoua, stoi(numarThreads));
        auto end = chrono::high_resolution_clock::now();
        auto duration = chrono::duration_cast<chrono::microseconds>(end - start);
        cout << duration.count();
        scrieInFisier(matriceNoua, n, m, outputFile);
    } else {
        auto start = chrono::high_resolution_clock::now();
        parcurgereParalelaLinie(matriceBordata, n, m, matriceConvolutie, k, matriceNoua, stoi(numarThreads));
        auto end = chrono::high_resolution_clock::now();
        auto duration = chrono::duration_cast<chrono::microseconds>(end - start);
        cout << duration.count();
        scrieInFisier(matriceNoua, n, m, outputFile);
    }
}

//int main() {
//    int n = 10000, m = 10000, k = 5;
////    int matrice[MAX][MAX] = {0}, matriceBordata[MAX][MAX] = {0}, matriceConvolutie[MAX_CONV][MAX_CONV] = {0};
//    genereazaMatriceRandom(n, m, matrice, k, matriceConvolutie, "data.txt");
////    afisareMatrice(matrice, n, m);
//    return 0;
//}
