#include <iostream>
#include <cstring>
#include "fstream"
#include "vector"
#include "queue"

using namespace std;

/***
 * Cauta un element intr-un vecotr
 * @param lista int
 * @param lg_vector int
 * @param element int
 * @return true - daca l-a gasit
 *         false - daca nu l-a gasit
 */
bool cauta_elem_in_vector(int lista[], int lg_vector, int element){
    for (int i = 0; i <= lg_vector; i++)
        if (lista[i] == element)
            return true;
    return false;
}

/***
 * Algoritmul Moore determina drumul minim dintr-un nod sursa intr-un nod destinatie
 * Pe baza acestuia se determina solutia labirintului
 * @param sursa int - nod sursa
 * @param destinatie int - nod destinatie
 * @param lista_vecini lista vecinilor pt fiecare nod
 * @param matrice_noua matricea in care se construieste rezultatul
 * @param nr_nod int
 * @param nr_linii int
 * @param nr_coloane int
 */
void Moore(int sursa, int destinatie, vector <int> lista_vecini[], int matrice_noua[][200], int nr_nod, int nr_linii, int nr_coloane){
    queue <int> Q;
    int lungime_lant[500], parinte[500];

    lungime_lant[sursa] = 0;
    parinte[sursa] = 0;

    for (int i = 1; i <= nr_nod; i++)
        if (i != sursa) {
            lungime_lant[i] = LONG_MAX;
            parinte[i] = 0;
        }

    Q.push(sursa);
    while (!Q.empty()){
        int nod = Q.front();
        Q.pop();
        for (int i = 0; i < lista_vecini[nod].size(); i++) {
            int vecin = lista_vecini[nod][i];
            if (lungime_lant[vecin] == LONG_MAX) {
                parinte[vecin] = nod;
                lungime_lant[vecin] = lungime_lant[nod] + 1;
                Q.push(vecin);
            }
        }
    }

    int rezultat[500];
    int lg = lungime_lant[destinatie], lg2 = lungime_lant[destinatie];
    rezultat[lg] = destinatie;
    while(lg){
        rezultat[lg-1] = parinte[rezultat[lg]];
        lg--;
    }

    cout << "Nodurile care compun solutia labirintului sunt: ";
    for (int i = 0; i <= lg2; i++)
        cout << rezultat[i] << " ";

    cout << endl << endl;
        for (int i = 0; i < nr_linii; i++) {
            for (int j = 0; j < nr_coloane; j++)
                if (cauta_elem_in_vector(rezultat, lg2, matrice_noua[i][j]))
                    cout << 0 << " ";
                else
                    if (matrice_noua[i][j] == -1)
                    cout << 1 << " ";
                    else
                        cout << "  ";
            cout << endl;
        }
}


int main() {

    ifstream f("pb3.txt");

    vector <int> lista_vecini[500];
    char matrice[200][200];
    int nr_linii = 0;
    //citire matrice
    while(f.getline(matrice[nr_linii], 200))
        nr_linii++;
    unsigned long long nr_coloane = strlen(matrice[0]);

    //fiecare spatiu reprezinta un nod
    //formam o matrice care are -1 pt pereti, iar restul reprezinta noduri numerotate de la 0
    int matrice_noua[200][200], nod = 1, sursa, destinatie;
    for (int i = 0; i < nr_linii; i++)
        for (int j = 0; j < nr_coloane; j++)
            if (matrice[i][j] == '1')
                matrice_noua[i][j] = -1;
            else{
                if (matrice[i][j] == 'S')
                    sursa = nod;
                else
                    if (matrice[i][j] == 'F')
                        destinatie = nod;
                matrice_noua[i][j] = nod;
                nod++;
            }
    nod--;
    for (int i = 0; i < nr_linii; i++)
        for (int j = 1; j < nr_coloane - 1; j++)
            if (i == 0){
                if (matrice_noua[i][j-1] != -1)
                    lista_vecini[matrice_noua[i][j]].push_back(matrice_noua[i][j-1]);
                if (matrice_noua[i][j+1] != -1)
                    lista_vecini[matrice_noua[i][j]].push_back(matrice_noua[i][j+1]);
                if (matrice_noua[i+1][j] != -1)
                    lista_vecini[matrice_noua[i][j]].push_back(matrice_noua[i+1][j]);
            }
            else
                if (i == nr_linii - 1){
                    if (matrice_noua[i][j-1] != -1)
                        lista_vecini[matrice_noua[i][j]].push_back(matrice_noua[i][j-1]);
                    if (matrice_noua[i][j+1] != -1)
                        lista_vecini[matrice_noua[i][j]].push_back(matrice_noua[i][j+1]);
                    if (matrice_noua[i-1][j] != -1)
                        lista_vecini[matrice_noua[i][j]].push_back(matrice_noua[i-1][j]);
                }
                else{
                    if (matrice_noua[i][j-1] != -1)
                        lista_vecini[matrice_noua[i][j]].push_back(matrice_noua[i][j-1]);
                    if (matrice_noua[i][j+1] != -1)
                        lista_vecini[matrice_noua[i][j]].push_back(matrice_noua[i][j+1]);
                    if (matrice_noua[i-1][j] != -1)
                        lista_vecini[matrice_noua[i][j]].push_back(matrice_noua[i-1][j]);
                    if (matrice_noua[i+1][j] != -1)
                        lista_vecini[matrice_noua[i][j]].push_back(matrice_noua[i+1][j]);
                }
    Moore(sursa, destinatie, lista_vecini, matrice_noua, nod, nr_linii, nr_coloane);
    return 0;
}
