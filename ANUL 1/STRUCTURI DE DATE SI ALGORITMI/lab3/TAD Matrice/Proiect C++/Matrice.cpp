#include "Matrice.h"
#include "iostream"
#include <exception>
#include <stdexcept>

using namespace std;


Matrice::Matrice(int m, int n) {
    //Overall Complexity : O(1)
    this->nr_linii = m;
    this->nr_coloane = n;
    if (nr_linii <= 0 || nr_coloane <= 0)
        throw;
    this->Linie_prim = new Nod(0, nullptr, nullptr);
    this->Linie_ultim = new Nod(0, nullptr, nullptr);
    this->Linie_prim->urm = this->Linie_ultim;
    this->Linie_ultim->ante = this->Linie_prim;

    this->Coloana_prim = new Nod(0, nullptr, nullptr);
    this->Coloana_ultim = new Nod(0, nullptr, nullptr);
    this->Coloana_prim->urm = this->Coloana_ultim;
    this->Coloana_ultim->ante = this->Coloana_prim;

    this->Valoare_prim = new Nod(0, nullptr, nullptr);
    this->Valoare_ultim = new Nod(0, nullptr, nullptr);
    this->Valoare_prim->urm = this->Valoare_ultim;
    this->Valoare_ultim->ante = this->Valoare_prim;
}

int Matrice::nrLinii() const{
    //Overall Complexity : O(1)
	return this->nr_linii;
}


int Matrice::nrColoane() const{
    //Overall Complexity : O(1)
	return this->nr_coloane;
}


TElem Matrice::element(int i, int j) const{
    //Overall Complexity: O(k), unde k e numarul de elemente nenule din matrice
    if (i < 0 || i >= this->nr_linii)
        throw invalid_argument("Linie invalida!");
    if (j < 0 || j >= this->nr_coloane)
        throw invalid_argument("Coloana invalida!");

    PNod pointer_linie = this->Linie_prim;
	PNod pointer_coloana = this->Coloana_prim;
    PNod pointer_valoare = this->Valoare_prim;

    while (pointer_linie->urm != this->Linie_ultim && pointer_coloana->urm != this->Coloana_ultim && pointer_valoare->urm != this->Valoare_ultim){
        if (pointer_linie->urm->e == i && pointer_coloana->urm->e == j)
            return pointer_valoare->urm->e;
        pointer_linie = pointer_linie->urm;
        pointer_coloana = pointer_coloana->urm;
        pointer_valoare = pointer_valoare->urm;
    }
	return NULL_TELEMENT;
}



TElem Matrice::modifica(int i, int j, TElem e) {
    //Overall complexity: O(k) unde k este numarul de elemente nenule din matrice
    if (i < 0 || i >= this->nr_linii)
        throw invalid_argument("Linie invalida!");
    if (j < 0 || j >= this->nr_coloane)
        throw invalid_argument("Coloana invalida!");

    PNod pointer_linie = this->Linie_prim;
    PNod pointer_coloana = this->Coloana_prim;
    PNod pointer_valoare = this->Valoare_prim;

    while (pointer_linie->urm != this->Linie_ultim && pointer_coloana->urm != this->Coloana_ultim && pointer_valoare->urm != this->Valoare_ultim){
        if (pointer_linie->urm->e == i && pointer_coloana->urm->e == j){
            TElem val_veche = pointer_valoare->urm->e;
            pointer_valoare->urm->e = e;
            return val_veche;
        }
        pointer_linie = pointer_linie->urm;
        pointer_coloana = pointer_coloana->urm;
        pointer_valoare = pointer_valoare->urm;
    }

    pointer_linie = this->Linie_prim;
    pointer_coloana = this->Coloana_prim;
    pointer_valoare = this->Valoare_prim;

    PNod linie_de_inserat = new Nod(i, nullptr, nullptr);
    PNod coloana_de_inserat = new Nod(j, nullptr, nullptr);
    PNod valoare_de_inserat = new Nod(e, nullptr, nullptr);

    while (pointer_linie->urm != this->Linie_ultim && pointer_coloana->urm != this->Coloana_ultim && pointer_valoare->urm != this->Valoare_ultim){
        if (pointer_linie->urm->e > i || pointer_linie->urm->e == i && pointer_coloana->urm->e > j)
            break;
        pointer_linie = pointer_linie->urm;
        pointer_coloana = pointer_coloana->urm;
        pointer_valoare = pointer_valoare->urm;
    }
    linie_de_inserat->urm = pointer_linie->urm;
    pointer_linie->urm->ante = linie_de_inserat;
    pointer_linie->urm = linie_de_inserat;
    linie_de_inserat->ante = pointer_linie;

    coloana_de_inserat->urm = pointer_coloana->urm;
    pointer_coloana->urm->ante = coloana_de_inserat;
    pointer_coloana->urm = coloana_de_inserat;
    coloana_de_inserat->ante = pointer_coloana;

    valoare_de_inserat->urm = pointer_valoare->urm;
    pointer_valoare->urm->ante = valoare_de_inserat;
    pointer_valoare->urm = valoare_de_inserat;
    valoare_de_inserat->ante = pointer_valoare;

    return NULL_TELEMENT;
}

Matrice Matrice::transpusa(){
    //Overall complexity: O(n*m) unde n - nr de linii, m-nr de coloane
    Matrice matrice_transpusa(this->nr_coloane, this->nr_linii);
    for (int i = 0; i < this->nr_linii; i++)
        for (int j = 0; j < this->nr_coloane; j++) {
            TElem element_mat = this->element(i, j);
            matrice_transpusa.modifica(j, i, element_mat);
        }
    return matrice_transpusa;
}

Nod::Nod(TElem e, PNod urm, PNod ante){
    this->e = e;
    this->urm = urm;
    this->ante = ante;
}

TElem Nod::element() {
    return this->e;
}

PNod Nod::urmator(){
    return this->urm;
}

PNod Nod::anterior(){
    return this->ante;
}





