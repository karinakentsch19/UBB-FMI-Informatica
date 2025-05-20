#include <iostream>

#include "TestExtins.h"
#include "TestScurt.h"
#include "AB.h"
#include "IteratorAB.h"

using namespace std;

void afisare(){
    /*
    AB a11(11), a12(12), a10(10), a8(8), a9(9);
    a8.adaugaSubDr(a11);
    a10.adaugaSubSt(a12);
    AB a, a6(a8,6,a), a7(a9,7,a10), a4(a,4,a6),a5(a7,5,a),a2(a4,2,a),a3(a,3,a5),ab(a2,1,a3);*/
    AB a8(8), a, a7(a,7,a8), a6(a,6,a7), a5(a,5,a6), a4(a,4,a5), a3(a,3,a4), a2(a,2,a3), ab(a,1,a2);

    IteratorAB* iterator = ab.iterator("postordine");
    while(iterator->valid()){
        cout << iterator->element() << " ";
        iterator->urmator();
    }
}

void test_k_pasi_inapoi(){
    //arborele este
    //       1
    //      / \
    //     2   3
    //    /     \
    //   4       5
    //    \     /
    //     6   7
    //    /   / \
    //   8   9   10
    //    \      /
    //    11    12
    AB a11(11), a12(12), a10(10), a8(8), a9(9);
    a8.adaugaSubDr(a11);
    a10.adaugaSubSt(a12);
    AB a, a6(a8,6,a), a7(a9,7,a10), a4(a,4,a6),a5(a7,5,a),a2(a4,2,a),a3(a,3,a5),ab(a2,1,a3);
    IteratorPreordine iterator{ab};
    cout<<"Parcurgere preordine completa:\n";
    while(iterator.valid()){
        cout << iterator.element() << " ";
        iterator.urmator();
    }
    iterator.k_pasi_inapoi(5);
    cout <<"\n5 pasi inapoi: \n";
    while(iterator.valid()){
        cout << iterator.element() << " ";
        iterator.urmator();
    }
    cout <<"\n";
}

int main() {
	testAll();
//    afisare();
    test_k_pasi_inapoi();
	testAllExtins();
	cout<<"End";
}
