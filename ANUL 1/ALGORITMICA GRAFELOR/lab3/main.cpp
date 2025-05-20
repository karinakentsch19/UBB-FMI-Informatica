#include <iostream>
#include <fstream>
#include <vector>

#define INFINIT 9999999

using namespace std;

typedef struct{
    int vf1, vf2, pondere;
}Muchie;

typedef struct{
    int distanta, parinte;
}Nod;


void initializare(int V, Nod* lista_drumuri_nod, int S){
    for (int i = 0; i < V; i++){
        lista_drumuri_nod[i].distanta = INFINIT;
        lista_drumuri_nod[i].parinte = -1;
    }
    lista_drumuri_nod[S].distanta = 0;
}

void relax(Nod*  lista_drumuri_nod, int u, int v, int pondere){
    if (lista_drumuri_nod[v].distanta > lista_drumuri_nod[u].distanta + pondere) {
        lista_drumuri_nod[v].distanta = lista_drumuri_nod[u].distanta + pondere;
        lista_drumuri_nod[v].parinte = u;
    }
}

bool BellmanFord(Muchie* lista_muchii, Nod*  lista_drumuri_nod, int V, int E, int S){
    initializare(V, lista_drumuri_nod, S);
    for (int i = 0; i < V - 1; i++)
        for (int j = 0 ; j < E; j++) {
            int v = lista_muchii[j].vf1;
            int u = lista_muchii[j].vf2;
            int pondere = lista_muchii[j].pondere;
            relax(lista_drumuri_nod, v, u, pondere);
        }
    for (int i = 0; i < E; i++) {
        int v = lista_muchii[i].vf1;
        int u = lista_muchii[i].vf2;
        int pondere = lista_muchii[i].pondere;
        if (lista_drumuri_nod[v].distanta > lista_drumuri_nod[u].distanta + pondere)
            return false;
    }
    return true;
}

int main(int argc, char* argv[]) {

    ifstream fin(argv[1]);
    ofstream fout(argv[2]);

    // V - numar varfuri
    //E - numar muchii
    // S - varful sursa
    int V, E, S;
    fin >> V >> E >> S;

    auto* lista_muchii = new Muchie [E];

    //citire si formarea vectorului alocat dinamic de muchii
    int varf1, varf2, pondere;
    for (int i = 0; i < E; i++) {
        fin >> varf1 >> varf2 >> pondere;
        lista_muchii[i].vf1 = varf1;
        lista_muchii[i].vf2 = varf2;
        lista_muchii[i].pondere = pondere;
    }
    fin.close();

    auto* lista_drumuri_nod = new Nod [V];
    
    BellmanFord(lista_muchii, lista_drumuri_nod, V, E, S);
    for (int i = 0; i < V; i++)
        if (lista_drumuri_nod[i].distanta == INFINIT)
            fout << "INF ";
        else
            fout << lista_drumuri_nod[i].distanta << " ";
    fout.close();
    return 0;
}
