#include <iostream>
#include "fstream"
#include "vector"
#include "queue"
#include "climits"

using namespace std;

ifstream fin("graf.txt");

int main() {

    vector <int> lista_vecini[100];
    queue <int> Q;
    int lungime_lant[100], parinte[100], rezultat[100];

    int n, x, y, sursa, destinatie;
    fin >> n;
    while (fin >> x >> y)
        lista_vecini[x].push_back(y);

    cout << "Introduceti nodul sursa:";
    cin >> sursa;
    cout << "Introduceti nodul destinatie:";
    cin >> destinatie;

    lungime_lant[sursa] = 0;
    parinte[sursa] = 0;
    for (int i = 1; i <= n; i++)
        if (i != sursa) {
            lungime_lant[i] = LONG_MAX;
            parinte[i] = 0;
        }

    Q.push(sursa);
    while (!Q.empty()){
        int nod = Q.front();
        Q.pop();
        for (int i = 0; i < lista_vecini[nod].size(); i++) {
            int vecin = lista_vecini[nod][i];
            if (lungime_lant[vecin] == LONG_MAX) {
                parinte[vecin] = nod;
                lungime_lant[vecin] = lungime_lant[nod] + 1;
                Q.push(vecin);
            }
        }
    }

    if (lungime_lant[destinatie] == LONG_MAX)
        cout << "Nu exista lant!";
    else{
        int lg = lungime_lant[destinatie], lg2 = lungime_lant[destinatie];
        rezultat[lg] = destinatie;
        while(lg){
            rezultat[lg-1] = parinte[rezultat[lg]];
            lg--;
        }

        cout << "Drumul minim este: ";
        for (int i = 0; i <= lg2; i++)
            cout << rezultat[i] << " ";
    }
    return 0;
}
