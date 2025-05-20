#include <iostream>
#include <vector>
#include <fstream>

using std::vector;
using std::cout;

int minim_care_nu_apartine_codare_prufer(vector<int> codare_prufer, int nr_decodare){
    int vf = 0;
    while(true){
        int i = 0;
        while(i < nr_decodare && codare_prufer[i] != vf)
            i++;
        if (i == nr_decodare)
            return vf;
        vf++;
    }
}

void Decodare_Prufer(vector<int> codare_prufer, int vector_tati[], int nr_decodare){
    for (int i = 0; i < nr_decodare; i++){
        int varf = codare_prufer.front();
        int minim_codare_prufer = minim_care_nu_apartine_codare_prufer(codare_prufer, nr_decodare);
        vector_tati[minim_codare_prufer] = varf;
        codare_prufer.erase(codare_prufer.begin());
        codare_prufer.push_back(minim_codare_prufer);
    }
    int vf = minim_care_nu_apartine_codare_prufer(codare_prufer, nr_decodare);
    vector_tati[vf] = -1;
}

int main() {
    std::ifstream fin("in.txt");
    std::ofstream fout("out.txt");

    int nr_decodare, x, n;
    vector<int> codare_prufer;
    int vector_tati[100000];

    fin >> nr_decodare;
    for (int i = 0; i < nr_decodare; i++){
        fin >> x;
        codare_prufer.push_back(x);
    }
    fin.close();

    n = nr_decodare + 1;

    Decodare_Prufer(codare_prufer, vector_tati, nr_decodare);

    fout << n << "\n";
    for (int i = 0; i < n; i++)
        fout << vector_tati[i] << " ";
    fout.close();
    return 0;
}
