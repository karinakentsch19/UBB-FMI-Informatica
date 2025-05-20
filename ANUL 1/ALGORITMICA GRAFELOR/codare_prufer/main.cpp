#include <iostream>
#include <fstream>
#include <vector>

using std::vector;
using std::cout;

/**
 * Returneaza frunza minima care nu a mai fost gasita inainte
 * @param vector_tati vector de tati
 * @param n numar noduri
 * @return frunza minima sau -1 daca nu mai exista frunze
 */
int frunza_minima(int vector_tati[], int n){
    for (int frunza = 0; frunza < n; frunza++){
        int vf = 0;
        while(vf < n && vector_tati[vf]!=frunza)
            vf++;
        if (vf == n && vector_tati[frunza] != -1)
            return frunza;
    }
    return -1;
}

void Codare_Prufer(int vector_tati[], vector<int>& codare_prufer, int n){
    while(frunza_minima(vector_tati,n)!=-1){
        int frunza_min = frunza_minima(vector_tati, n);
        int predecesor_frunza_min = vector_tati[frunza_min];
        codare_prufer.push_back(predecesor_frunza_min);
        vector_tati[frunza_min] = -1;
    }
}

int main() {
    std::ifstream fin("in.txt");
    std::ofstream fout("out.txt");

    int n, vector_tati[100000];
    fin >> n;
    for (int i = 0; i < n; i++)
        fin >> vector_tati[i];
    fin.close();

    vector <int> codare_prufer;
    Codare_Prufer(vector_tati, codare_prufer, n);
    fout << codare_prufer.size() << "\n";
    for (int i = 0; i < codare_prufer.size(); i++)
        fout << codare_prufer[i] << " ";
    fout.close();
    return 0;
}