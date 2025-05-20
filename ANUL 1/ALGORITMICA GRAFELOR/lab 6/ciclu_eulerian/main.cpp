#include <iostream>
#include <fstream>
#include <vector>
#include "stack"
#include "queue"

using std::queue;
using std::stack;
using std::vector;

std::ifstream fin("in.txt");
std::ofstream fout("out.txt");

bool verif_toate_noduri_grad_par(vector<int> adiacenta[], int n){
    for (int i = 0; i < n; i++)
        if (adiacenta[i].size() % 2 != 0)
            return false;
    return true;
}

void gasire_ciclu_eulerian(vector<int> adiacenta[], int nod, /*stack<int>& stiva_noduri*/ queue<int>& coada){
    for (int i = 0; i < adiacenta[nod].size(); i++){
        int vecin = adiacenta[nod][i];
        if (vecin != -1){
            adiacenta[nod][i] = -1;
            //marcam ca muchia a fost vizitata
            for (int j = 0; j < adiacenta[vecin].size(); j++)
                if (adiacenta[vecin][j] == nod)
                    adiacenta[vecin][j] = -1;
            gasire_ciclu_eulerian(adiacenta, vecin, coada);
            coada.push(vecin);
            //stiva_noduri.push(vecin);
        }
    }
}

void ciclu_eulerian(vector<int>adiacenta[], int n){
    if (!verif_toate_noduri_grad_par(adiacenta, n))
        fout << "Nu exista ciclu eulerian" << '\n';
    else{
        //stack<int> stiva_noduri;
        queue<int> coada_noduri;
        int nod = 0;
        while (adiacenta[nod].empty())
            nod++;
        gasire_ciclu_eulerian(adiacenta, nod, coada_noduri);

        while(!coada_noduri.empty()){
            fout << coada_noduri.front() << " ";
            coada_noduri.pop();
        }
    }
}

int main() {
    int n, m, vf1, vf2;
    vector<int> adiacenta[10100];

    fin >> n >> m;
    for (int i = 1; i <= m; i++){
        fin >> vf1 >> vf2;
        adiacenta[vf1].push_back(vf2);
        adiacenta[vf2].push_back(vf1);
    }
    fin.close();
    ciclu_eulerian(adiacenta, n);
    fout.close();

    return 0;
}
