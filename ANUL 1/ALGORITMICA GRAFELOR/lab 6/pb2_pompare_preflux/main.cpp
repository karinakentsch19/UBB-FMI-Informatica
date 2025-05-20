#include <iostream>
#include <fstream>

using std::min;

int exces[500], inaltime[500], graf_rezidual[500][500];

void initializare_preflux(int adiacenta[500][500], int n, int s, int t){
    for (int i = 0; i < n; i++){
        exces[i] = 0;
        inaltime[i] = 0;
    }

    for (int i = 0; i < n; i++)
        for (int j = 0; j < n; j++)
            graf_rezidual[i][j] = adiacenta[i][j];

    inaltime[s] = n;
    for (int j = 0; j < n; j++) {
        graf_rezidual[s][j] = adiacenta[s][j];
        exces[j] = adiacenta[s][j];
        exces[s] = exces[s] - adiacenta[s][j];
    }

}

void pompare(int u, int v, int adiacenta[500][500]){
    int minim = min(exces[u], graf_rezidual[u][v]);
    graf_rezidual[u][v] = graf_rezidual[u][v] - minim;
    graf_rezidual[v][u] = graf_rezidual[v][u] + minim;
    exces[u] = exces[u] - minim;
    exces[v] = exces[v] + minim;
}

void inaltare(int u, int n){
    int inaltime_minima = INT_MAX;
    for (int i = 0 ; i < n; i++)
        if (graf_rezidual[u][i] != 0 && inaltime[i] < inaltime_minima)
            inaltime_minima = inaltime[i];
    inaltime[u] = 1 + inaltime_minima;
}

bool conditie(int u, int n){
    bool found = false;
    for (int i = 0; i < n; i ++)
        if (graf_rezidual[u][i] != 0){
            found = true;
            if (inaltime[u] > inaltime[i])
                return false;
        }
    return found;
}

void pompare_preflux(int adiacenta[500][500], int n, int s, int t){
    initializare_preflux(adiacenta, n, s, t);
    while(true){
        bool found = false;
        for (int u = 0; u < n; u++)
            for (int v = 0; v < n; v++)
                if (u != s && u != t && exces[u]>0 && graf_rezidual[u][v] && inaltime[u] > inaltime[v]){
                    pompare(u,v,adiacenta);
                    found = true;
                }
        if (found)
            continue;

        for (int u = 0; u < n && !found; u++)
            if (u != s && u != t && exces[u] > 0 && conditie(u,n)){
                inaltare(u,n);
                found = true;
            }
        if (found)
            continue;
        break;
    }
}

int main() {

    std::ifstream fin("in.txt");
    std::ofstream fout("out.txt");
    int n, m, vf1, vf2, capacitatea;
    int adiacenta[500][500];

    fin >> n >> m;
    for (int i = 1; i <= m; i++){
        fin >> vf1 >> vf2 >> capacitatea;
        adiacenta[vf1][vf2] = capacitatea;
    }
    fin.close();
    pompare_preflux(adiacenta, n, 0, n-1);
    fout << exces[n-1];
    fout.close();
    return 0;
}