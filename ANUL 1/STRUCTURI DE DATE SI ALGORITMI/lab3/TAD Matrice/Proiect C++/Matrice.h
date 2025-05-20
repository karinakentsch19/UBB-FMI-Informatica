#pragma once

typedef int TElem;
#define NULL_TELEMENT 0

class Nod;
//se defineste tipul PNod ca fiind adresa unui Nod

typedef Nod *PNod;

class Nod
{
private:
    TElem e;
    PNod urm;
    PNod ante;
public:

    friend class Matrice;
    //constructor
    Nod(TElem e, PNod urm, PNod ante);

    TElem element();
    PNod urmator();
    PNod anterior();



};

class Matrice {

private:
	/* aici e reprezentarea */
    PNod Linie_prim;
    PNod Linie_ultim;
    PNod Coloana_prim;
    PNod Coloana_ultim;
    PNod Valoare_prim;
    PNod Valoare_ultim;
    int nr_linii, nr_coloane;

public:

	//constructor
	//se arunca exceptie daca nrLinii<=0 sau nrColoane<=0
	Matrice(int nrLinii, int nrColoane);


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

    //transpusa matricei
    Matrice transpusa();

};







