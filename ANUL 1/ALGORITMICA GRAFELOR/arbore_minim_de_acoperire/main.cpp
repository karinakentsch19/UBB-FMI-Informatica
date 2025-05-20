#include <iostream>
#include <fstream>
#include <vector>
#include <algorithm>

using std::sort;
using std::vector;
using std::pair;

typedef pair<int, int> Muchie;

/*
struct nod{
    int parent, key;
};

void Prim(vector<pair<TVarf, TPondere>> lista_adiacenta[], int radacina, int n, nod lista_noduri[]){
    priority_queue <pair<int, int>, vector<pair<int, int> >, greater<pair<int, int> > > coada;
    for (int i = 0; i < n; i++){
        lista_noduri[i].key = INFINIT;
        lista_noduri[i].parent = NIL;
    }
    lista_noduri[radacina].key = 0;
    coada.push({lista_noduri[radacina].key, radacina});
    while(coada.empty()){
        int vf = coada.top().second;
        for (int i = 0; i < lista_adiacenta[vf].size(); i++)
            if (lista_adiacenta[vf][i].second < lista_noduri[lista_adiacenta[vf][i].first].key){
                lista_noduri[lista_adiacenta[vf][i].first].parent = vf;
                lista_noduri[lista_adiacenta[vf][i].first].key = lista_adiacenta[vf][i].second;
                coada.push({lista_noduri[lista_adiacenta[vf][i].first].key, lista_adiacenta[vf][i].first});
            }
    }
}*/



class DisjointSet{
private:
    int parent[50000],  rank[50000], n;
public:
    DisjointSet(int nr_noduri){
        this->n = nr_noduri;
        for (int i = 0; i < n; i++){
            this->rank[i] = 0;
            this->parent[i] = i;
        }
    }

    /**
     * Gaseste parintele nodului nod
     * @param nod int
     * @return parintele nodului
     */
    int find_set(int nod){
         if (nod != parent[nod])
             parent[nod] = find_set(parent[nod]);
        return parent[nod];
    }

    /**
     * Union dupa rank
     * @param x nod
     * @param y nod
     */
    void union_set(int x, int y){
        x = find_set(x);
        y = find_set(y);
        if (rank[x] > rank[y])
            parent[y] = x;
        else
            parent[x] = y;
        if (rank[x] == rank[y])
            rank[y]++;
    }

};


void Kruskal(vector <pair<int, Muchie>> lista_muchii, int n, int& suma_ponderi, vector<pair<int, int>>& arbore_minim_de_acoperire){
    sort(lista_muchii.begin(), lista_muchii.end());
    DisjointSet set(n);
    for (int i = 0; i < lista_muchii.size(); i++){

        int u = lista_muchii[i].second.first;
        int v = lista_muchii[i].second.second;

        int set_u = set.find_set(u);
        int set_v = set.find_set(v);

        //verificam daca muchia (u,v) creeaza un ciclu
        if (set_u != set_v){
            arbore_minim_de_acoperire.push_back({u,v});
            suma_ponderi += lista_muchii[i].first;

            set.union_set(set_u, set_v);
        }
    }
}



int main() {
    std::ifstream fin("in.txt");
    std::ofstream fout("out.txt");

    vector <pair<int, Muchie>> lista_muchii;
    vector<pair<int, int>> arbore_minim_de_acoperire;
    int n, m, vf1, vf2, pondere, suma_ponderi = 0;
    fin >> n >> m;
    for (int i = 1; i <= m; i++){
        fin >> vf1 >> vf2 >> pondere;
        lista_muchii.push_back({pondere,{vf1,vf2}});
    }

    Kruskal(lista_muchii, n, suma_ponderi, arbore_minim_de_acoperire);
    fout << suma_ponderi << "\n";
    for (int i = 0; i < arbore_minim_de_acoperire.size(); i++)
        fout << arbore_minim_de_acoperire[i].first << " " << arbore_minim_de_acoperire[i].second << "\n";



    /*
    vector<pair<TVarf, TPondere>> lista_adiacenta[50000]
    int n, m, vf1, vf2, pondere;
    fin >> n >> m;
    for (int i = 1; i <= m; i++){
        fin >> vf1 >> vf2 >> pondere;
        lista_adiacenta[vf1].push_back({vf2, pondere});
    }


    nod lista_noduri[50000];
    Prim(lista_adiacenta, 0, n, lista_noduri);

    for (int i = 0; i < n - 1; i++)
        fout << lista_noduri[i].parent << " " << i << "\n";*/
    return 0;
}
