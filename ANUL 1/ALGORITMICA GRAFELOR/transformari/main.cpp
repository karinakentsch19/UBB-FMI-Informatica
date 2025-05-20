#include <iostream>
#include "fstream"

using std::cout;
using std::endl;

struct ListaAdiacenta{
    int varf;
    int vf_adiacente[100];
    int grad_varf;
};

struct ListaMuchii{
    int varf1, varf2;
};

/**
 * Transforma matricea de adiacenta in matrice de incidenta
 * @param adiacenta int
 * @param incidenta int
 * @param n int
 */
void matrice_adiacenta_in_matrice_incidenta(int adiacenta[][100], int incidenta[][100], int n){
    int index = 0;
    for (int j = 0; j < n; j++)
        for (int i = j + 1; i < n; i++)
            if (adiacenta[i][j] == 1){
                incidenta[i][index] = 1;
                incidenta[j][index] = 1;
                index++;
            }
}

int main() {
    std::ifstream fin("in.txt");

    /*
    //citire numar de noduri + graf sub forma de matrice de adiacenta
    //TRANSFORMARE MAT. ADIACENTA IN MAT.INCIDENTA
    int n;
    fin >> n;
    int adiacenta[100][100], incidenta[100][100];

    for (int i = 0; i < n; i++)
        for (int j = 0 ; j < n; j++)
            fin >> adiacenta[i][j];

    matrice_adiacenta_in_matrice_incidenta(adiacenta,incidenta,n);

    for (int i = 0; i < n; i++) {
        for (int j = 0; j < n; j++)
            cout << incidenta[i][j] << " ";
        cout << endl;
    }*/




    /*
     * //citire numar noduri + graf dat cu lista de muchii
     * //TRANSFORMARE LISTA MUCHII IN MAT. INCIDENTA
    int n, incidenta[100][100], vf1, vf2, nr_muchii = 1;
    fin >> n;

    while(fin >> vf1 >> vf2){
        incidenta[vf1][nr_muchii] = 1;
        incidenta[vf2][nr_muchii] = 1;
        nr_muchii++;
    }

    for (int i = 1; i <= n; i++) {
        for (int j = 1; j <= n; j++)
            cout << incidenta[i][j] << " ";
        cout << endl;
    }*/




    /*
    //citire numar noduri + lista de muchii
    //TRANSFORMARE LISTA DE MUCHII IN LISTA DE ADIACENTA
    int n, vf1, vf2;
    fin >> n;
    ListaAdiacenta lista[100];

    for (int i = 1; i <= n; i++)
        lista[i].grad_varf = 0;

    while(fin >> vf1 >> vf2){
        lista[vf1].varf = vf1;
        lista[vf2].varf = vf2;
        lista[vf1].vf_adiacente[lista[vf1].grad_varf++] = vf2;
        lista[vf2].vf_adiacente[lista[vf2].grad_varf++] = vf1;
    }

    for (int i = 1; i <= n; i++){
        cout << lista[i].varf << ": ";
        for (int j = 0; j < lista[i].grad_varf; j++)
            cout << lista[i].vf_adiacente[j] << " ";
        cout << endl;
    }*/




    /*
    //citire mat incidenta + nr noduri
    //TRANSFORMARE MAT. INCIDENTA IN MAT. ADIACENTA
    int n, incidenta[100][100], adiacenta[100][100];
    fin >> n;
    for (int i = 1; i <= n; i++)
        for (int j = 1; j <= n; j++)
            fin >> incidenta[i][j];

    //calculam numarul de muchii
    int m = 0;
    for (int i = 1; i <= n; i++)
        for (int j = 1; j <= n; j++)
            if (incidenta[i][j] == 1)
                m++;

    m = m / 2;

    for (int j = 1; j <= m; j++){
        int vf1 = -1, vf2 = -1;
        for (int i = 1; i <= n; i++)
            if (incidenta[i][j] == 1) {
                if (vf1 == -1)
                    vf1 = i;
                else {
                    vf2 = i;
                    adiacenta[vf1][vf2] = adiacenta[vf2][vf1] = 1;
                    break;
                }
            }
    }

    for (int i = 1; i <= n; i++){
        for (int j = 1; j <= n; j++)
            cout << adiacenta[i][j] << " ";
        cout << endl;
    }*/






    /*
    //TRANSFORMARE MATRICE INCIDENTA IN LISTA DE MUCHII
    int n, incidenta[100][100], adiacenta[100][100];
    ListaMuchii lista_muchii[100];
    fin >> n;
    for (int i = 1; i <= n; i++)
        for (int j = 1; j <= n; j++)
            fin >> incidenta[i][j];

    //calculam numarul de muchii
    int m = 0;
    for (int i = 1; i <= n; i++)
        for (int j = 1; j <= n; j++)
            if (incidenta[i][j] == 1)
                m++;
    m = m / 2;

    int indice = 0;
    for (int j = 1; j <= m; j++) {
        int vf1 = -1, vf2 = -1;
        for (int i = 1; i <= n; i++)
            if (incidenta[i][j] == 1) {
                if (vf1 == -1)
                    vf1 = i;
                else {
                    vf2 = i;
                    lista_muchii[indice].varf1 = vf1;
                    lista_muchii[indice].varf2 = vf2;
                    indice++;
                    break;
                }
            }
    }

    for (int i = 0; i < n; i++)
        cout << lista_muchii[i].varf1 << " " << lista_muchii[i].varf2 << endl;*/

    return 0;
}
