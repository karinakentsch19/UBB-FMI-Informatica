#include <iostream>
#include <cstring>
#include "fstream"
#include "queue"

using std::queue;
using std::min;

/**
 * Returneaza true/false daca exista drum in graful rezidual de la s la t
 * @param graf_rezidual int
 * @param s int
 * @param t int
 * @param parent int
 * @return true/false
 */
bool BFS(int graf_rezidual[100][100], int s, int t, int parent[], int n){
    bool visited[100];
    memset(visited, false, sizeof(visited));

    queue<int> coada;
    coada.push(s);
    visited[s] = true;
    parent[s] = -1;

    while(!coada.empty()){
        int nod = coada.front();
        coada.pop();

        for (int i = 0; i < n; i++)
            if (!visited[i] && graf_rezidual[nod][i] > 0){
                coada.push(i);
                parent[i] = nod;
                visited[i] = true;
            }
    }

    return visited[t];
}

int edmond_karp(int graf[100][100], int s, int t, int n){
    int graf_rezidual[100][100];
    for (int i = 0; i < n; i++)
        for (int j = 0; j < n; j++)
            graf_rezidual[i][j] = graf[i][j];

    int parent[100];
    int flux_maxim = 0;

    while(BFS(graf_rezidual, s, t, parent, n)){
        int flux_drum = INT_MAX;
        int nod = t;
        while(nod != s){
            int u = parent[nod];
            flux_drum = min(flux_drum, graf_rezidual[u][nod]);
            nod = parent[nod];
        }

        nod = t;
        while(nod != s){
            int u = parent[nod];
            graf_rezidual[u][nod] -= flux_drum;
            graf_rezidual[nod][u] += flux_drum;
            nod = parent[nod];
        }
        flux_maxim += flux_drum;
    }

    return flux_maxim;

}

int main() {

    std::ifstream fin("in.txt");
    std::ofstream fout("out.txt");

    int graf[100][100], n, m, vf1, vf2, capacitate;
    fin >> n >> m;
    for (int i = 1; i <= m; i++){
        fin >> vf1 >> vf2 >> capacitate;
        graf[vf1][vf2] = capacitate;
    }

    fin.close();
    fout << edmond_karp(graf, 0, n - 1,n);
    fout.close();
    return 0;
}
