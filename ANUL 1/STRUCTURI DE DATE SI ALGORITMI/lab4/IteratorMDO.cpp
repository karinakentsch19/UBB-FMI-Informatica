#include "IteratorMDO.h"
#include "MDO.h"

IteratorMDO::IteratorMDO(const MDO& d) : dict(d){
	//Complexitatea: Teta(1)
    this->curent = d.prim;
}

void IteratorMDO::prim(){
    //Complexitatea: Teta(1)
    this->curent = dict.prim;
}

void IteratorMDO::urmator(){
    //Complexitatea: Teta(1)
	if (!valid())
        throw exception();
    this->curent = dict.urmator[this->curent];
}

bool IteratorMDO::valid() const{
    //Complexitatea: Teta(1)
	if (this->curent == -1)
        return false;
    return true;
}

TElem IteratorMDO::element() const{
    //Complexitatea: Teta(1)
    if (!valid())
        throw exception();
    return dict.elemente[this->curent];
}


