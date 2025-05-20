#pragma once
#include <string>
using namespace std;

typedef int TElem;

class AB;

class IteratorAB;

class AB {

private:
	int radacina = -1, primLiber;
    int Element[10000], stanga[10000], dreapta[10000];

    //cauta o pozitie libera unde se poate aloca un element si o returneaza
    int aloca(AB* ab);

    //dealoca elementul de pe pozitia data
    void dealoca(AB* ab, int pozitie);

    //creeaza un nod si returneaza pozitia acestuia
    int creeazaNod(AB* ab,TElem element);

    //Copiaza arborele cu radacina data in arborele this
    int copiere(AB* ab, const int rad, const int elem[],const int st[],const int dr[]);

    //distruge un arbore
    void distruge(int rad,int elem[], int st[], int dr[]);

public:
	friend class IteratorAB;
	friend class IteratorPreordine;
	friend class IteratorInordine;
	friend class IteratorPostordine;
	friend class IteratorLatime;

		//constructorul implicit
		AB();

		//contructor de copiere
		AB(const AB& ab);


		//creeaza un arbore binar care se reduce la o frunza
		AB(TElem elem);

		//constructor arbore binar
		AB(const AB& st, TElem elem, const AB& dr);


		//adauga unui arbore binar subarborele stang
		//arunca exceptie daca arborele este vid
		void adaugaSubSt(const AB& st);

		//adauga unui arbore binar subarborele drept
		//arunca exceptie daca arborele este vid
		void adaugaSubDr(const AB& dr);

        //distruge un subarbore
        void distrugeSubarbori(int rad,int elem[], int st[], int dr[]);

		//verifica daca arborele este vid
        bool vid() const;

		//accesare element din radacina
		//arunca exceptie daca arborele este vid
		TElem element() const;

		//accesare subarbore stang
		//arunca exceptie daca arborele este vid
		AB stang();

		//accesare subarbore drept
		//arunca exceptie daca arborele este vid
		AB drept();

		//iteratori pe arborele binar (ordine="preordine", "postordine", "inordine", "latime")
		IteratorAB* iterator(string ordine) const;

		// destructorul arborelui binar
		~AB();
};



