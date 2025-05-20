#include "AB.h"
#include "IteratorAB.h"
#include <exception>
#include <string>


AB::AB() {
    //constructor implicit
    //Complexitate: Teta(10000)
    radacina = -1;
    primLiber = 0;
    for (int i = 0; i < 10000; i++)
        Element[i] = -1;
    for (int i = 0; i < 9999; i++)
        stanga[i] = i + 1;
    stanga[9999] = -1;
    for(int i = 9999; i > 0; i--)
        dreapta[i] = i - 1;
    dreapta[0] = -1;
}

AB::AB(const AB& ab) {
    //constructor de copiere
	//Complexitate: Teta(n) n = nr noduri arbore
    radacina = -1;
    primLiber = 0;
    for (int i = 0; i < 10000; i++)
        Element[i] = -1;
    for (int i = 0; i < 9999; i++)
        stanga[i] = i + 1;
    stanga[9999] = -1;
    for(int i = 9999; i > 0; i--)
        dreapta[i] = i - 1;
    dreapta[0] = -1;
    radacina = copiere(this,ab.radacina, ab.Element, ab.stanga, ab.dreapta);
}

AB::AB(TElem e){
    //creeaza arbore binar care se reduce la o frunza (adica radacina)
    //Complexitate: Teta(10000)
    radacina = -1;
    primLiber = 0;
    for (int i = 0; i < 10000; i++)
        Element[i] = -1;
    for (int i = 0; i < 9999; i++)
        stanga[i] = i + 1;
    stanga[9999] = -1;
    for(int i = 9999; i > 0; i--)
        dreapta[i] = i - 1;
    dreapta[0] = -1;

    radacina = creeazaNod(this,e);
}

AB::AB(const AB& st, TElem e, const AB& dr){
    //constructor binar
    //complexitate: teta(n)
    //distrug vechiul arbore
    distruge(radacina, Element, stanga, dreapta);

    //initializare vectori
    radacina = -1;
    primLiber = 0;
    for (int i = 0; i < 10000; i++)
        Element[i] = -1;
    for (int i = 0; i < 9999; i++)
        stanga[i] = i + 1;
    stanga[9999] = -1;
    for(int i = 9999; i > 0; i--)
        dreapta[i] = i - 1;
    dreapta[0] = -1;

    //creez radacina
    radacina = creeazaNod(this,e);

    //copiez subarborii
    stanga[radacina] = copiere(this,st.radacina, st.Element, st.stanga, st.dreapta);
    dreapta[radacina] = copiere(this,dr.radacina, dr.Element, dr.stanga, dr.dreapta);
}


void AB::adaugaSubSt(const AB& st){
 	 //complexitate: teta(n) n = nr noduri
     if (st.radacina == -1)
         throw exception();
     else{
         //distrug vechii subarbori ai subarborelui stang
         distrugeSubarbori(stanga[radacina], Element, stanga, dreapta);
         //copiez noul subarbore
         stanga[radacina] = copiere(this,st.radacina, st.Element, st.stanga, st.dreapta);
     }
}

void AB::adaugaSubDr(const AB& dr){
	//complexitate: teta(n) n=nr noduri
    if (dr.radacina == -1)
        throw exception();
    else {
        //distrug vechii subarbori ai subarborelui stang
        distrugeSubarbori(dreapta[radacina], Element, stanga, dreapta);
        //copiez noul subarbore
        dreapta[radacina] = copiere(this, dr.radacina, dr.Element, dr.stanga, dr.dreapta);
    }
}

TElem AB::element() const{
    //complexitate: teta(1)
    if (radacina == -1)
        throw exception();
    else
        return Element[radacina];
}

AB AB::stang(){
 	//complexitate: teta(n) n = nr noduri
    if (radacina == -1)
        throw exception();
    else {
        AB *ab = new AB;
        ab->radacina = copiere(ab, stanga[radacina], Element, stanga, dreapta);
        return *ab;
    }
}

AB AB::drept(){
    //complexitate: teta(n) n = nr noduri
    if (radacina == -1)
        throw exception();
    else {
        AB *ab = new AB;
        ab->radacina = copiere(ab, dreapta[radacina], Element, stanga, dreapta);
        return *ab;
    }
}

AB::~AB() {
    //complexitate: teta(n)
    //distruge(radacina, Element, stanga, dreapta);
}

bool AB::vid() const{
	if (radacina == -1)
	    return true;
    return false;
}


IteratorAB* AB::iterator(string s) const {
	if (s=="preordine")
		return new IteratorPreordine(*this);
	if (s=="inordine")
		return new IteratorInordine(*this);
	if (s=="postordine")
		return new IteratorPostordine(*this);
	if (s=="latime")
		return new IteratorLatime(*this);
	return nullptr;
}

int AB::copiere(AB* ab, const int rad,const int elem[],const int st[],const int dr[]){
    //copiaza arborelele cu radacina in arborele this
    //complexitate: Teta(n), n=nr noduri din arbore
    if (rad != -1){
        int nod = creeazaNod(ab, elem[rad]);
        ab->stanga[nod] = copiere(ab,st[rad], elem, st, dr);
        ab->dreapta[nod] = copiere(ab,dr[rad], elem, st, dr);
        return nod;
    }
    return -1;
}

int AB::aloca(AB* ab) {
    //Complexitate: Teta(1)
    int i = ab->primLiber;
    ab->primLiber = ab->stanga[ab->primLiber];
    return i;
}

void AB::dealoca(AB* ab,int pozitie) {
    //Complexitate: Teta(1)
    ab->stanga[pozitie] = ab->primLiber;
    ab->primLiber = pozitie;
}

int AB::creeazaNod(AB* ab,TElem element){
    //Complexitate: Teta(1)
    int poz = aloca(ab);
    ab->stanga[poz] = -1;
    ab->dreapta[poz] = -1;
    ab->Element[poz] = element;
    return poz;
}

void AB::distruge(int rad, int elem[], int st[], int dr[]) {
    //distruge un arbore
    //complexitate: Teta(n), n = nr noduri
    if (rad != -1){
        distruge(st[rad], elem, st, dr);
        distruge(dr[rad], elem, st, dr);
        dealoca(this,rad);
    }
};

void AB::distrugeSubarbori(int rad, int elem[], int st[], int dr[]) {
    //complexitate: Teta(n), n = nr noduri
    if (rad != -1){
        distruge(st[rad], elem, st, dr);
        distruge(dr[rad], elem, st, dr);
    }
};
