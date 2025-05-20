#include "IteratorMDO.h"
#include "MDO.h"
#include <iostream>
#include <vector>

#include <exception>
using namespace std;

MDO::MDO(Relatie r) {
    //complexitate: Teta(dimensiune)
	this->dimensiune = 0;
    this->prim = -1;
    this->ultim = -1;
    this->primLiber = 0;
    for (int i = 0; i < 9999; i++)
        this->urmator[i] = i+1;
    this->urmator[9999] = -1;
    for (int i = 9999; i > 0; i--)
        this->anterior[i] = i-1;
    this->anterior[0] = -1;
    this->relatie = r;
}


void MDO::adauga(TCheie c, TValoare v) {
	//Complexitate: O(n)
    TElem element;
    element.first = c;
    element.second = v;
    int pozitie = creeazaNod(element);
    if (this->prim == -1){
        this->urmator[pozitie] = this->ultim;
        this->anterior[pozitie] = this->prim;
        this->ultim = pozitie;
        this->prim = pozitie;
    }
    else{
        if (this->relatie(element.first,this->elemente[this->prim].first)){
            this->elemente[pozitie] = element;
            this->urmator[pozitie] = this->prim;
            this->anterior[this->prim] = pozitie;
            this->prim = pozitie;
            this->anterior[pozitie] = -1;
        }
        else{
            if (!this->relatie(element.first,this->elemente[this->ultim].first)){
                this->elemente[pozitie] = element;
                this->urmator[this->ultim] = pozitie;
                this->anterior[pozitie] =this->ultim;
                this->ultim = pozitie;
                this->urmator[pozitie] = -1;
            }
            else{
                int i = this->prim;
                while (i != -1 && this->relatie(this->elemente[i].first, element.first))
                    i = this->urmator[i];
                if (i == -1){
                    this->elemente[pozitie] = element;
                    this->urmator[this->ultim] = pozitie;
                    this->anterior[pozitie] =this->ultim;
                    this->ultim = pozitie;
                    this->urmator[pozitie] = -1;
                }
                else {
                    this->elemente[pozitie] = element;
                    this->urmator[pozitie] = i;
                    this->anterior[pozitie] = this->anterior[i];
                    this->urmator[this->anterior[i]] = pozitie;
                    this->anterior[i] = pozitie;
                }
            }
        }
    }
    this->dimensiune++;
}

vector<TValoare> MDO::cauta(TCheie c) const {
	//Complexitatea: O(n)
    vector <TValoare> valori;
    int i = this->prim;
    while (i != -1){
        if (this->elemente[i].first == c)
            valori.push_back(this->elemente[i].second);
        else
            if (!this->relatie(this->elemente[i].first,c))
                break;
        i = this->urmator[i];
    }
    return valori;
}

bool MDO::sterge(TCheie c, TValoare v) {
	//Complexitatea: O(n)
    TElem element;
    element.first = c;
    element.second = v;

    if (this->prim != -1 && this->elemente[this->prim] == element){
        int poz = this->prim;
        this->anterior[this->urmator[this->prim]] = -1;
        this->prim = this->urmator[this->prim];
        dealoca(poz);
        this->dimensiune--;
        return true;
    }
    else{
        if (this->ultim != -1 && this->elemente[this->ultim] == element){
            int poz = this->ultim;
            this->urmator[this->anterior[this->ultim]] = -1;
            this->ultim = this->anterior[this->ultim];
            dealoca(poz);
            this->dimensiune--;
            return true;
        }
        else{
            int i = this->prim;
            while (i != -1){
                if (this->elemente[i] == element) {
                    this->urmator[this->anterior[i]] = this->urmator[i];
                    this->anterior[this->urmator[i]] = this->anterior[i];
                    dealoca(i);
                    this->dimensiune--;
                    return true;
                }
                i = this->urmator[i];
            }
        }
    }
    return false;
}

int MDO::dim() const {
    //Complexitatea: Teta(1)
	return this->dimensiune;
}

bool MDO::vid() const {
    //Complexitatea: Teta(1)
	if (this->dimensiune == 0)
        return true;
    return false;
}

IteratorMDO MDO::iterator() const {
	return IteratorMDO(*this);
}

MDO::~MDO() {
	/* de adaugat */
}

int MDO::aloca() {
    //Complexitate: Teta(1);
    int i = this->primLiber;
    this->primLiber = this->urmator[this->primLiber];
    return i;
}

void MDO::dealoca(int pozitie) {
    //Complexitate: Teta(1);
    this->urmator[pozitie] = this->primLiber;
    this->primLiber = pozitie;
}

int MDO::creeazaNod(TElem element) {
    //Complexitate: Teta(1);
    int pozitie = aloca();
    this->urmator[pozitie] = -1;
    this->anterior[pozitie] = -1;
    this->elemente[pozitie] = element;
    return pozitie;
}


TValoare MDO::ceaMaiFrecventaValoare() const{
    //Complexitate: Teta(n^2), unde n e numarul de elemente din vectorul de valori format
    vector <TValoare> vector_valori;
    if (this->vid())
        return NULL_TVALOARE;
    else {
        IteratorMDO iterator(*this);
        while (iterator.valid()) {
            TElem element = iterator.element();
            vector_valori.push_back(element.second);
            iterator.urmator();
        }
        int maxim_aparitii = 0;
        TValoare val_max;
        for (int i = 0; i < vector_valori.size() - 1; i++) {
            int contor = 1;
            for (int j = i + 1; j < vector_valori.size(); j++)
                if (vector_valori[j] == vector_valori[i])
                    contor++;
            if (contor > maxim_aparitii) {
                maxim_aparitii = contor;
                val_max = vector_valori[i];
            }
        }
        return val_max;
    }
}
