#include <iostream>
#include "vector"
#include "fstream"
#include "cstring"

using namespace std;
ifstream f("pb5.txt");

struct nod{
    char color[10];
    int d, parinte, f;
};

/***
 * Determina arborele construit din nodul u
 * @param lista_noduri nod
 * @param n int
 * @param u int
 * @param time int
 * @param lista_vecini vector <int>
 */
void DFS_VISIT(nod lista_noduri[], int n, int u, int time, vector <int> lista_vecini[]){
    time = time + 1;
    lista_noduri[u].d = time;
    strcpy(lista_noduri[u].color, "gri");
    for (int i = 0; i < lista_vecini[u].size(); i++){
        int vecin = lista_vecini[u][i];
        if (strcmp(lista_noduri[vecin].color,"alb") == 0){
            lista_noduri[vecin].parinte = u;
            DFS_VISIT(lista_noduri, n, vecin, time, lista_vecini);
        }
    }
    strcpy(lista_noduri[u].color, "negru");
    time = time + 1;
    lista_noduri[u].f = time;
    cout << "Nodul: " << u << "  Distanta: " << lista_noduri[u].d << "  Parinte: " << lista_noduri[u].parinte << "  Timp finalizare: " << lista_noduri[u].f << endl;
}

/***
 * Determina padurea grafului
 * @param lista_noduri nod
 * @param n int
 * @param lista_vecini vector <int>
 */
void DFS(nod lista_noduri[], int n, vector <int> lista_vecini[]){
    for (int i = 1; i <= n; i++){
        strcpy(lista_noduri[i].color, "alb");
        lista_noduri[i].parinte = 0;
    }
    int time = 0;
    for (int i = 1; i <= n; i++)
        if (strcmp(lista_noduri[i].color, "alb") == 0) {
            DFS_VISIT(lista_noduri, n, i, time, lista_vecini);
            cout << endl;
        }
}

int main() {

    vector <int> lista_vecini[100];
    nod lista_noduri[100];

    int n, x, y;
    //citire graf
    f >> n;
    while(f >> x >> y)
        lista_vecini[x].push_back(y);

    DFS(lista_noduri, n, lista_vecini);
    return 0;
}
