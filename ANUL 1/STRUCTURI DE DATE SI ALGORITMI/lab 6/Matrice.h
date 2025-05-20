#pragma once

typedef int TElem;

#define NULL_TELEMENT 0

class Nod;

typedef Nod *PNod;

class Nod{
private:
    TElem e;
    PNod urm;
public:
    friend class Matrice;
    Nod(TElem e, PNod urm):e{e}, urm{urm}{}
    TElem element(){
        return e;
    }
    PNod urmator(){
        return urm;
    }
};

#define mMax 10000

class IteratorMatrice;

class Matrice {

private:
    //mMax- numarul maxim de locatii din tabela
    PNod Linie[mMax];
    PNod Coloana[mMax];
    PNod Valoare[mMax];
    int nr_linii, nr_coloane;

public:

	//constructor
	//se arunca exceptie daca nrLinii<=0 sau nrColoane<=0
	Matrice(int nrLinii, int nrColoane);

    //functia de dispersie: d(i,j) = (i+j)%mMax;
    //returneaza valoarea acesteia pt o pereche de indici (i,j)
    int d(int i, int j) const;

	//destructor
	~Matrice(){};

	//returnare element de pe o linie si o coloana
	//se arunca exceptie daca (i,j) nu e pozitie valida in Matrice
	//indicii se considera incepand de la 0
	TElem element(int i, int j) const;


	// returnare numar linii
	int nrLinii() const;

	// returnare numar coloane
	int nrColoane() const;


	// modificare element de pe o linie si o coloana si returnarea vechii valori
	// se arunca exceptie daca (i,j) nu e o pozitie valida in Matrice
	TElem modifica(int i, int j, TElem);

    // creaza un iterator asupra elementelor nenule (care nu sunt egale cu NULL_TELEM) din Matrice.
    // Iteratorul va returna elementele pe parcurgând matricea pe linii (mai întâi elementele primei linii, apoi ale celei de-a doua linie, etc).
    IteratorMatrice iterator() const;

};

class IteratorMatrice{
private:
    int curent_linie, curent_coloana;
    const Matrice& mat;

    IteratorMatrice(const Matrice& m): mat{m}{
        curent_linie = 0;
        curent_coloana = 0;
    }
    friend class Matrice;
public:

    TElem elem(){
        //Complexitate: O(n)
        return mat.element(curent_linie, curent_coloana);
    }

    void urmator(){
        //Complexitate: Teta(1);
        if (curent_coloana == mat.nrColoane() -1){
            curent_linie++;
            curent_coloana = 0;
        }
        else
            curent_coloana++;
    }

    bool valid(){
        //Complexitate: Teta(1)
        if (curent_linie < mat.nrLinii() && curent_coloana < mat.nrColoane())
            return true;
        return false;
    }
};






