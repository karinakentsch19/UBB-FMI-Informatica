#include <iostream>
#include "fstream"
#include "vector"
#include "queue"
#include <cstring>
#include "climits"

using namespace std;

ifstream f("pb4.txt");

struct nod{
    char color[10];
    int d, parinte;
};

int main() {

    vector <int> lista_vecini[100];
    int n, x, y, sursa;
    nod lista_noduri[100];
    //citire graf
    f >> n;
    while (f >> x >> y)
        lista_vecini[x].push_back(y);

    cout << "Introduceti nodul sursa:";
    cin >> sursa;

    //BFS - parcurgere in latime
    for (int i = 1; i <= n; i++)
        if (i != sursa){
            strcpy(lista_noduri[i].color, "alb");
            lista_noduri[i].d = LONG_MAX;
            lista_noduri[i].parinte = 0;
        }

    strcpy(lista_noduri[sursa].color, "gri");
    lista_noduri[sursa].d = 0;
    lista_noduri[sursa].parinte = 0;

    queue <int> Q;
    Q.push(sursa);

    while(!Q.empty()){
        int nod = Q.front();
        Q.pop();
        for (int i = 0; i < lista_vecini[nod].size(); i++){
            int vecin = lista_vecini[nod][i];
            if (strcmp(lista_noduri[vecin].color, "alb") == 0){
                strcpy(lista_noduri[vecin].color, "gri");
                lista_noduri[vecin].d = lista_noduri[nod].d + 1;
                lista_noduri[vecin].parinte = nod;
                Q.push(vecin);
            }
        }
        strcpy(lista_noduri[nod].color, "negru");
    }

    for (int i = 1; i <= n; i++)
        if (strcmp(lista_noduri[i].color, "negru") == 0)
            cout << "Nodul: " << i << "  Distanta: " << lista_noduri[i].d << "  Parinte: "<< lista_noduri[i].parinte << endl;
    return 0;
}
