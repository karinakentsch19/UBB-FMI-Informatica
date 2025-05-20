#include <iostream>
#include <cassert>
#include "Matrice.h"
#include "../Teste/TestExtins.h"
#include "../Teste/TestScurt.h"
#include "fstream"

using namespace std;

ifstream f("in.txt");

void afisare_matrice(Matrice m){
    for (int i = 0; i < m.nrLinii(); i++){
        for (int j = 0; j < m.nrColoane(); j++)
            cout << m.element(i, j) << " ";
        cout << endl;
    }
}

void test_matrice_transpusa(){
    Matrice mat(2,2);
    assert(mat.nrLinii() == 2);
    assert(mat.nrColoane() == 2);

    mat.modifica(0,0,2);
    assert(mat.element(0,0) == 2);
    mat.modifica(0,1,1);
    assert(mat.element(0,1) == 1);
    mat.modifica(1,0,4);
    assert(mat.element(1,0) == 4);
    mat.modifica(1,1,3);
    assert(mat.element(1,1) == 3);

    Matrice transpusa = mat.transpusa();
    assert(transpusa.element(0,0) == 2);
    assert(transpusa.element(0,1) == 4);
    assert(transpusa.element(1,0) == 1);
    assert(transpusa.element(1,1) == 3);

}

int main() {

//	testAll();
//	testAllExtins();
//	cout << "End";

    test_matrice_transpusa();
    cout << "Test matrice transpusa trecut cu succes!" << endl;

    int n, m;
    f >> n >> m;
    Matrice m1(n,m);
    int numar;
    for (int i = 0; i < m1.nrLinii(); i++)
        for (int j = 0; j < m1.nrColoane(); j++) {
            f >> numar;
            m1.modifica(i, j, numar);
        }
    cout << "Matricea initiala: " << endl;
    afisare_matrice(m1);

    Matrice mat_transpusa = m1.transpusa();
    cout << "Afisare matrice transpusa: " << endl;
    afisare_matrice(mat_transpusa);
    f.close();
}