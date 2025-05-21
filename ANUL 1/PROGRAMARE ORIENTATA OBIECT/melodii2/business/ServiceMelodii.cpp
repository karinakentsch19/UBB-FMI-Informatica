//
// Created by Karina Kentsch on 6/15/2023.
//

#include "ServiceMelodii.h"

const int ServiceMelodii::get_lungime_service() const {
    return lista_melodii.get_lungime_repo();
}

const vector<Melodie> ServiceMelodii::get_all_service() const {
    return lista_melodii.get_all_repo();
}

void ServiceMelodii::adauga_melodie_service(string titlu, string artist, string gen) {
    //aflare id
    int id = 0;
    for (auto& mel: lista_melodii.get_all_repo())
        if (mel.get_id() > id)
            id = mel.get_id();
    id++;
    Melodie melodie{id,titlu, artist, gen};
    if (validator.valideaza_melodie(melodie))
        lista_melodii.adauga_melodie_repo(melodie);
}

void ServiceMelodii::sterge_dupa_id_service(int id) {
    lista_melodii.sterge_melodie_dupa_id_repo(id);
}

int ServiceMelodii::numar_melodii_pt_gen_dat(string gen) {
    int cont = 0;
    for (auto& melodie: lista_melodii.get_all_repo())
        if (melodie.get_gen() == gen)
            cont++;
    return cont;
}
