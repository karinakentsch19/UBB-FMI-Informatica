#include <iostream>
#include "fstream"

using namespace std;

ifstream f("matrice_tranzitiva.txt");

/***
 * Parcurgere in adancime - se obtine vector viz[] care indica nodurile care au fost accesate
 * @param nod int
 * @param viz int - retine daca un nod a fost vizitat
 * @param n int
 * @param adiacenta int - matricea de adiacenta
 */
void DFS(int nod, int viz[], int n, int adiacenta[][100]){
    viz[nod] = 1;
    for (int j = 0; j <= n; j++)
        if (adiacenta[nod][j] == 1 && viz[j] != 1)
            DFS(j, viz, n, adiacenta);
}

int main() {

    int n, adiacenta[100][100], tranzitiva[100][100], viz[100];

    //citire numar noduri si graful dat prin matricea de adaicenta
    f >> n;
    for (int i = 0; i <= n; i++)
        for (int j = 0; j <= n; j++)
            f >> adiacenta[i][j];

    //se determina pt fiecare nod i - nodurile care au putut fi accesate din acel nod si se retine acest lucru in matricea tranzitiva
    for (int i = 0; i <= n; i++){
        for (int k = 0; k <= n; k++)
            viz[k] = 0;
        DFS(i, viz, n, adiacenta);
        for (int j = 0; j <= n; j++)
            tranzitiva[i][j] = viz[j];
    }

    //afisare matrice tranzitiva
    for (int i = 0; i <= n; i++){
        for (int j = 0; j <= n; j++)
            cout << tranzitiva[i][j] << " ";
        cout << endl;
    }
    return 0;
}
