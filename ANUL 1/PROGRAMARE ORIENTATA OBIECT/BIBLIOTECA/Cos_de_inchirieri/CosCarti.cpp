//
// Created by Karina Kentsch on 4/22/2023.
//

#include "CosCarti.h"
#include "random"

void CosCarti::adauga_cos(const Carte &carte) {
    this->cos_inchirieri.push_back(carte);
}

void CosCarti::goleste_cos() {
    this->cos_inchirieri.clear();
}

void CosCarti::genereaza_cos(const vector<Carte> &lista, int numar_carti) {
    std::mt19937 mt{ std::random_device{}() };
    std::uniform_int_distribution<> dist(0,lista.size()-1);
    while(numar_carti != 0){
        int indice_random = dist(mt);
        adauga_cos(lista[indice_random]);
        numar_carti--;
    }
}

const vector<Carte> &CosCarti::get_cos() const{
    return this->cos_inchirieri;
}

const int CosCarti::size_cos() const{
    return this->cos_inchirieri.size();
}
