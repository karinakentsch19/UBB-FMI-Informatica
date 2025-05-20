#pragma once
#include "stack"
#include "AB.h"
#include "queue"

using std::queue;
using std::stack;

class IteratorAB {
public:

	friend class AB;

	//operatii pe iterator
	virtual void prim()=0;

	virtual void urmator()=0;

	virtual TElem element() = 0;

	virtual bool valid() = 0;
};

class IteratorPreordine: public IteratorAB{
    friend class AB;
private:


	//contine o referinta catre containerul pe care il itereaza

   	const AB& ab;
    stack<int> stiva_anterior;

	/* aici e reprezentarea specifica a itertorului*/
    int curent;
    stack<int> stiva;

public:

        //constructorul primeste o referinta catre Container
        //iteratorul va referi primul element din container
        IteratorPreordine(const AB& _ab);

		//reseteaza pozitia iteratorului la inceputul containerului
		void prim();

		//muta iteratorul in container
		// arunca exceptie daca iteratorul nu e valid
		void urmator();

		//verifica daca iteratorul e valid (indica un element al containerului)
		bool valid();

		//returneaza valoarea elementului din container referit de iterator
		//arunca exceptie daca iteratorul nu e valid
		TElem element();

        void k_pasi_inapoi(int k);
};


#include <exception>
class IteratorInordine: public IteratorAB{
   friend class AB;
private:
  //constructorul primeste o referinta catre Container
  //iteratorul va referi primul element din container
  IteratorInordine(const AB& _ab);

  //contine o referinta catre containerul pe care il itereaza

  const AB& ab;
  /* aici e reprezentarea specifica a iteratorului*/
  int curent;
  stack<int> stiva;


public:

		//reseteaza pozitia iteratorului la inceputul containerului
		void prim();

		//muta iteratorul in container
		// arunca exceptie daca iteratorul nu e valid
		void urmator();

		//verifica daca iteratorul e valid (indica un element al containerului)
		bool valid();

		//returneaza valoarea elementului din container referit de iterator
		//arunca exceptie daca iteratorul nu e valid
		TElem element();
};


class IteratorPostordine: public IteratorAB{
   friend class AB;
private:

    //constructorul primeste o referinta catre Container
	//iteratorul va referi primul element din container
  	IteratorPostordine(const AB& _ab);
    //contine o referinta catre containerul pe care il itereaza

	const AB& ab;
	/* aici e reprezentarea specifica a itertorului*/
    int curent;
    stack<pair<int,int>> stiva;
    queue<int> coada;

public:

		//reseteaza pozitia iteratorului la inceputul containerului
		void prim();

		//muta iteratorul in container
		// arunca exceptie daca iteratorul nu e valid
		void urmator();

		//verifica daca iteratorul e valid (indica un element al containerului)
		bool valid();

		//returneaza valoarea elementului din container referit de iterator
		//arunca exceptie daca iteratorul nu e valid
		TElem element();
};

class IteratorLatime: public IteratorAB{
   friend class AB;
private:

   //constructorul primeste o referinta catre Container
	//iteratorul va referi primul element din container
   IteratorLatime(const AB& _ab);
   //contine o referinta catre containerul pe care il itereaza

   const AB& ab;
 	/* aici e reprezentarea pecifica a itratorului*/
    int curent;
    queue<int> coada;

public:

		//reseteaza pozitia iteratorului la inceputul containerului
		void prim();

		//muta iteratorul in container
		// arunca exceptie daca iteratorul nu e valid
		void urmator();

		//verifica daca iteratorul e valid (indica un element al containerului)
		bool valid();

		//returneaza valoarea elementului din container referit de iterator
		//arunca exceptie daca iteratorul nu e valid
		TElem element();
};
