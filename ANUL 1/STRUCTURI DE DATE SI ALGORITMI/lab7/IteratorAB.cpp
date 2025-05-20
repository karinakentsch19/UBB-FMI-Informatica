#include "AB.h"
#include "IteratorAB.h"

IteratorInordine::IteratorInordine(const AB& _ab):ab(_ab) {
	//complexitate:teta(1)
    curent = ab.radacina;
}

void IteratorInordine::prim(){
    //complexitate:teta(n)
    curent = ab.radacina;
    while(!stiva.empty())
        stiva.pop();
}

bool IteratorInordine::valid(){
    //complexitate:teta(1)
    if (!stiva.empty() || curent != -1)
        return true;
	return false;
}

TElem IteratorInordine::element() {
	//comlexitate: O(h) h - inaltimea arborelui
    if (stiva.empty() && curent == -1)
        throw exception();
	while(curent != -1){
        stiva.push(curent);
        curent = ab.stanga[curent];
    }
    curent = stiva.top();
    stiva.pop();
    return ab.Element[curent];
}

void IteratorInordine::urmator(){
	//complexitate:teta(1)
    if (stiva.empty() && curent == -1)
        throw exception();
    curent = ab.dreapta[curent];
}

IteratorPreordine::IteratorPreordine(const AB& _ab):ab(_ab){
	//Complexitate: Teta(1)
    curent = ab.radacina;
    if (ab.radacina != -1)
        stiva.push(ab.radacina);
}

void IteratorPreordine::prim(){
 	//Complexitate: O(n) - n nr noduri

    curent = ab.radacina;
    while(!stiva.empty())
        stiva.pop();
    if (ab.radacina != -1)
        stiva.push(ab.radacina);
}


bool IteratorPreordine::valid(){
	//complexitate: Teta(1)
    if (!stiva.empty())
        return true;
    return false;
}

TElem IteratorPreordine::element(){
	//comlexitate: Teta(1)
    if (stiva.empty() && curent == -1)
        throw exception();
    else{
        curent = stiva.top();
        stiva.pop();
        return ab.Element[curent];
    }
}

void IteratorPreordine::urmator(){
	//complexitate: Teta(1);
    stiva_anterior.push(curent);

    if (ab.dreapta[curent] != -1)
        stiva.push(ab.dreapta[curent]);
    if (ab.stanga[curent] != -1)
        stiva.push(ab.stanga[curent]);
    if (stiva.empty() && curent == -1)
        throw exception();
}

void IteratorPreordine::k_pasi_inapoi(int k) {
    //Complexitate: O(n)
    for (int i = 0; i < k; i++){
        curent = stiva_anterior.top();
        stiva_anterior.pop();
    }
    while(!stiva.empty())
        stiva.pop();
    if (curent != -1)
        stiva.push(curent);
}

IteratorPostordine::IteratorPostordine(const AB& _ab):ab(_ab){
	//complexitate: O(n)
    curent = ab.radacina;

    while(!stiva.empty() || curent != -1){
        while(curent != -1){
            stiva.push({curent, 0});
            curent = ab.stanga[curent];
        }
        pair<int, int> elem = stiva.top();
        stiva.pop();
        curent = elem.first;
        if (elem.second == 0){
            stiva.push({curent, 1});
            curent = ab.dreapta[curent];
        }
        else{
            coada.push(curent);
            curent = -1;
        }
    }
}

void IteratorPostordine::prim(){
    //complexitate: teta(n)
    curent = ab.radacina;
    while(!stiva.empty())
        stiva.pop();
    while(!coada.empty())
        coada.pop();
    while(!stiva.empty() || curent != -1){
        while(curent != -1){
            stiva.push({curent, 0});
            curent = ab.stanga[curent];
        }
        pair<int, int> elem = stiva.top();
        stiva.pop();
        curent = elem.first;
        if (elem.second == 0){
            stiva.push({curent, 1});
            curent = ab.dreapta[curent];
        }
        else{
            coada.push(curent);
            curent = -1;
        }
    }
}


bool IteratorPostordine::valid(){
    //complexitate: teta(1)
	if (!coada.empty())
        return true;
    return false;
}

TElem IteratorPostordine::element(){
    //complexitate: teta(1)
    if (coada.empty())
        throw exception();
    int elem = coada.front();
    return ab.Element[elem];
}

void IteratorPostordine::urmator(){
    //complexitate: O(1)
    coada.pop();
}

IteratorLatime::IteratorLatime(const AB& _ab):ab(_ab){
    //complexitate: teta(1)
	curent = ab.radacina;
    if (ab.radacina != -1)
        coada.push(ab.radacina);
}

void IteratorLatime::prim(){
	//complexitate: teta(n)
    curent = ab.radacina;
    while(!coada.empty())
        coada.pop();
    if (ab.radacina != -1)
        coada.push(ab.radacina);
}


bool IteratorLatime::valid(){
	//complexitate: teta(1)
    if (coada.empty())
        return false;
	return true;
}

TElem IteratorLatime::element(){
	//complexitate: teta(1)
    if (coada.empty() && curent == -1)
        throw exception();
    else{
        curent = coada.front();
        coada.pop();
        return ab.Element[curent];
    }
}

void IteratorLatime::urmator(){
	//complexitate: teta(1)
    if (ab.stanga[curent] != -1)
        coada.push(ab.stanga[curent]);
    if (ab.dreapta[curent] != -1)
        coada.push(ab.dreapta[curent]);
    if (coada.empty() && curent == -1)
        throw exception();
}
