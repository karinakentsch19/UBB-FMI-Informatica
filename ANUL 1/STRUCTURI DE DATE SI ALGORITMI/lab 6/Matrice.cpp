#include "Matrice.h"

#include <exception>

using namespace std;

Matrice::Matrice(int m, int n) {
	//Complexitate: Teta(mMax), mMax - nr maxim de locatii din tabela
    for (int i = 0; i < mMax; i++)
        this->Linie[i] = nullptr;
    for (int i = 0; i < mMax; i++)
        this->Coloana[i] = nullptr;
    for (int i = 0; i < mMax; i++)
        this->Valoare[i] = nullptr;
    this->nr_linii = m;
    this->nr_coloane = n;
}

int Matrice::nrLinii() const{
    //Complexitate: Teta(1);
    return this->nr_linii;
}


int Matrice::nrColoane() const{
    //Complexitate: Teta(1);
    return this->nr_coloane;
}


TElem Matrice::element(int i, int j) const{
    //Complexitate: O(n), n - numarul de elemente inlantuite la o anumita valoare de dispersie
    if (i < 0 || i >= this->nrLinii())
        throw exception();
    if (j < 0 || j >= this->nrColoane())
        throw exception();

    int valoare_dispersie = d(i,j);
    PNod linie = this->Linie[valoare_dispersie];
    PNod coloana = this->Coloana[valoare_dispersie];
    PNod valoare = this->Valoare[valoare_dispersie];

    while(linie != nullptr){
        if (linie->e == i && coloana->e == j)
            return valoare->e;
        linie = linie->urmator();
        coloana = coloana->urmator();
        valoare = valoare->urmator();
    }
    return NULL_TELEMENT;
}



TElem Matrice::modifica(int i, int j, TElem e) {
    //Complexitate: O(n)
    if (i < 0 || i >= this->nrLinii())
        throw exception();
    if (j < 0 || j >= this->nrColoane())
        throw exception();

    int valoare_dispersie = d(i, j);
    PNod linie = this->Linie[valoare_dispersie];
    PNod coloana = this->Coloana[valoare_dispersie];
    PNod valoare = this->Valoare[valoare_dispersie];

    //cazul in care vrem sa modificam cu un element null => stergerea lui
    if (e == NULL_TELEMENT){
        //elementul se afla la inceputul inlantuirii
        if (linie != nullptr && coloana != nullptr && linie->e == i && coloana->e == j){
            this->Linie[valoare_dispersie] = this->Linie[valoare_dispersie]->urmator();
            this->Coloana[valoare_dispersie] = this->Coloana[valoare_dispersie]->urmator();
            this->Valoare[valoare_dispersie] = this->Valoare[valoare_dispersie]->urmator();
            return valoare->e;
        }
        else {
            PNod anterior_linie, anterior_coloana, anterior_valoare;
            while (linie != nullptr) {
                if (linie->e == i && coloana->e == j) {
                    anterior_linie->urm = linie->urm;
                    anterior_coloana->urm = coloana->urm;
                    anterior_valoare->urm = valoare->urm;
                    return valoare->e;
                }
                anterior_linie = linie;
                linie = linie->urmator();

                anterior_coloana = coloana;
                coloana = coloana->urmator();

                anterior_valoare = valoare;
                valoare = valoare->urmator();
            }
            return NULL_TELEMENT;
        }
    }
    else {
        //cazul in care se modifica/ adauga elementul
        while(linie != nullptr){
            if (linie->e == i && coloana->e == j) {
                TElem vechea_valoare = valoare->e;
                valoare->e = e;
                return vechea_valoare;
            }
            linie = linie->urmator();
            coloana = coloana->urmator();
            valoare = valoare->urmator();
        }
        PNod valoare_de_adaugat = new Nod{e, nullptr};
        valoare_de_adaugat->urm = this->Valoare[valoare_dispersie];
        this->Valoare[valoare_dispersie] = valoare_de_adaugat;

        PNod linie_de_adaugat = new Nod{i, nullptr};
        linie_de_adaugat->urm = this->Linie[valoare_dispersie];
        this->Linie[valoare_dispersie] = linie_de_adaugat;

        PNod coloana_de_adaugat = new Nod{j , nullptr};
        coloana_de_adaugat->urm = this->Coloana[valoare_dispersie];
        this->Coloana[valoare_dispersie] = coloana_de_adaugat;

        return NULL_TELEMENT;
    }
}

int Matrice::d(int i, int j) const {
    //Complexitate: Teta(1);
    return (i + j) % mMax;
}

IteratorMatrice Matrice::iterator() const {
    //Complexitate: Teta(1);
    return IteratorMatrice(*this);
}

