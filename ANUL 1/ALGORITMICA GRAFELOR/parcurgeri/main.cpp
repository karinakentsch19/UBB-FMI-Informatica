#include <iostream>
#include "fstream"


using namespace std;
ifstream fin("in.txt");

/***
 * Parcurgere in adancime a grafului si completarea vectorului viz care retine pt fiecare nod daca a fost vizitat
 * @param x int - un varf al grafului
 * @param viz vector de vizitat - retine daca un nod a fost vizitat
 * @param adiacenta matricea de adiacenta
 * @param n numar de varfuri
 */
void DFS(int x, int viz[], int adiacenta[][100], int n, int numar){

    viz[x] = numar;
    for (int i = 1; i <= n; i++)
        if (adiacenta[x][i] == 1 && viz[i] == 0)
            DFS(i, viz, adiacenta, n, numar);
}

/**
 * Cauta primul nod nevizitat din vector
 * @param viz int
 * @return nodul nevizitat sau -1 daca nu exista noduri nevizitate
 */
int cauta_nod_nevizitat(int viz[], int n){
    for (int i = 1; i <= n; i++)
        if (viz[i] == 0)
            return i;
    return -1;
}


int main() {

    int n, m, vf1, vf2, adiacenta[100][100], numar = 1, viz[100]={0};
    fin >> n >> m;
    while (fin >> vf1 >> vf2) {
        adiacenta[vf1][vf2] = 1;
        adiacenta[vf2][vf1] = 1;
    }

    while(cauta_nod_nevizitat(viz,n)!=-1){
        int nod = cauta_nod_nevizitat(viz, n);
        DFS(nod, viz, adiacenta, n, numar);
        numar++;
    }

    int nr = 1;
    while(nr < numar ){
        cout << "Componenta " << nr << ": ";
        for (int i = 1; i <= n; i++)
            if (viz[i] == nr)
                cout << i << " ";
        cout << endl;
        nr++;
    }
    return 0;
}
