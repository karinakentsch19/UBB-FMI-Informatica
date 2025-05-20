#include <iostream>
#include "vector"
#include "queue"
#define INFINIT 999999;
#include "fstream"

using namespace std;

void initializare(int V, int drumuri[], int S){
    for (int i = 0; i < V; i++)
        drumuri[i] = INFINIT;
    drumuri[S] = 0;
}

void relax(int drumuri[], int u, int v, int pondere){
    if (drumuri[v] > drumuri[u] + pondere)
        drumuri[v] = drumuri[u] + pondere;
}

bool BellmanFord(vector <pair<int, int>> graf[], int drumuri[], int V, int S){
    initializare(V, drumuri, S);
    for (int k = 0; k < V - 1; k++)
        for (int i = 0; i <= V; i++)
            for (int j = 0; j < graf[i].size(); j++){
                int u = i;
                int v = graf[i][j].first;
                int pondere = graf[i][j].second;
                relax(drumuri, u, v, pondere);
            }
    for (int i = 0; i < V; i++)
        for (int j = 0; j < graf[i].size(); j++){
            int u = i;
            int v = graf[i][j].first;
            int pondere = graf[i][j].second;
            if (drumuri[v] > drumuri[u] + pondere)
                return false;
        }
    return true;
}

void Reponderare(vector <pair<int, int>> graf[], int drumuri[], int V){
    for (int i = 0; i < V; i++)
        for (int j = 0; j < graf[i].size(); j++){
            int nod = graf[i][j].first;
            int pondere = graf[i][j].second;
            graf[i][j] = {nod, pondere + drumuri[i] - drumuri[nod]};
        }
}

void Dijkstra(int costuri[], int sursa, int V, vector <pair<int, int>> graf[]){
    priority_queue <pair<int, int>, vector<pair<int, int> >, greater<pair<int, int> > > coada;
    bool vizitat[1001] = {false};
    initializare(V, costuri, sursa);
    coada.push({costuri[sursa], sursa});

    while(!coada.empty()){
        int nod = coada.top().second;
        coada.pop();
        if (vizitat[nod])
            continue;
        vizitat[nod] = true;
        for (int i = 0; i < graf[nod].size(); i++) {
            int u = nod;
            int v = graf[nod][i].first;
            int pondere = graf[nod][i].second;
            relax(costuri, u, v, pondere);
            coada.push({pondere + costuri[nod], v});
        }
    }
}

int main(int argc, char* argv[]) {

    ifstream fin(argv[1]);
    ofstream fout(argv[2]);

    vector <pair<int, int>> graf[1001];
    int E, V, vf1, vf2, pondere;

    fin >> V >> E;

    for (int i = 0; i < E; i++){
        fin >> vf1 >> vf2 >> pondere;
        graf[vf1].push_back({vf2, pondere});
    }

    for (int i = 0; i < V; i++)
        graf[V].push_back({i,0});
    fin.close();

    int drumuri[1001] = {0}, costuri[1001];
    bool ciclu_pozitiv = BellmanFord(graf, drumuri, V, 0);
    if (!ciclu_pozitiv)
        fout << -1;
    else{
        Reponderare(graf, drumuri, V);
        //afisare graf
        for (int i = 0; i < V; i++)
            for (int j = 0; j < graf[i].size(); j++) {
                fout << i << " " << graf[i][j].first << " ";
                if (graf[i][j].second >= 999999)
                    fout << "INF" << endl;
                else
                    fout << graf[i][j].second << endl;
            }
        for (int i = 0; i < V; i++){
            Dijkstra(costuri, i, V, graf);
            //drumul minim revine la valoarea initiala
            for (int j = 0; j < V; j++)
                costuri[j] = costuri[j] - drumuri[i] + drumuri[j];

            //afisare drumuri
            for (int k = 0; k < V; k++)
                if (costuri[k] >= 999999)
                    fout << "INF ";
                else
                    fout << costuri[k] << " ";
            fout << endl;
        }
    }
    fout.close();
    return 0;
}
