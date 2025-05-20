#pragma once

#include <vector>

typedef int TCheie;
typedef int TValoare;
#define NULL_TVALOARE NULL

#include <utility>
typedef std::pair<TCheie, TValoare> TElem;

using namespace std;

class IteratorMDO;

typedef bool(*Relatie)(TCheie, TCheie);

class MDO {
	friend class IteratorMDO;
    private:
	/* aici e reprezentarea */
    TElem elemente[10000];
    int urmator[10000], anterior[10000];
    int prim, ultim, primLiber, dimensiune;
    Relatie relatie;

    //cauta o pozitie libera unde se poate aloca un element si o returneaza
    int aloca();

    //dealoca elementul de pe pozitia data
    void dealoca(int pozitie);

    //creeaza un nod si returneaza pozitia acestuia
    int creeazaNod(TElem element);

    public:

	// constructorul implicit al MultiDictionarului Ordonat
	MDO(Relatie r);

	// adauga o pereche (cheie, valoare) in MDO
	void adauga(TCheie c, TValoare v);

	//cauta o cheie si returneaza vectorul de valori asociate
	vector<TValoare> cauta(TCheie c) const;

	//sterge o cheie si o valoare 
	//returneaza adevarat daca s-a gasit cheia si valoarea de sters
	bool sterge(TCheie c, TValoare v);

	//returneaza numarul de perechi (cheie, valoare) din MDO 
	int dim() const;

	//verifica daca MultiDictionarul Ordonat e vid 
	bool vid() const;

	// se returneaza iterator pe MDO
	// iteratorul va returna perechile in ordine in raport cu relatia de ordine
	IteratorMDO iterator() const;

	// destructorul 	
	~MDO();


    //returneaza cel mai frecvent element al vectorului
    TValoare ceaMaiFrecventaValoare() const;

};
