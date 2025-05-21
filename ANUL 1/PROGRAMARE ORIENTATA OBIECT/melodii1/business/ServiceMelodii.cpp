//
// Created by Karina Kentsch on 6/14/2023.
//

#include "ServiceMelodii.h"
#include "../errors/ServiceError.h"

const int ServiceMelodii::get_lungime_service() const {
    return lista_melodii.get_lungime_repo();
}

const vector<Melodie> ServiceMelodii::get_all_service() const {
    return lista_melodii.get_all_repo();
}

void ServiceMelodii::adauga_melodie_service(int id, string titlu, string artist, int rank) {
    Melodie melodie{id, titlu, artist, rank};
    if(validator.valideaza_melodie(melodie))
        lista_melodii.adauga_melodie_repo(melodie);
}

void ServiceMelodii::modifica_melodie_service(int id, string titlu_nou, int rank_nou) {
    Melodie melodie_noua{id, titlu_nou, "ana", rank_nou};
    if (validator.valideaza_melodie(melodie_noua))
        lista_melodii.modifica_melodie_repo(melodie_noua);
}

void ServiceMelodii::sterge_dupa_id_service(int id) {
    //cautam artistul cu id-ul dat
    string artist;
    for (const auto& melodie: lista_melodii.get_all_repo())
        if (melodie.get_id() == id)
            artist = melodie.get_artist();

    //numaram cate melodii au artistul respectiv
    int contor = 0;
    for (const auto& melodie: lista_melodii.get_all_repo())
        if (melodie.get_artist() == artist)
            contor++;

    if (contor > 1)
        lista_melodii.sterge_melodie_dupa_id_repo(id);
    else
        throw ServiceError("Ultima melodie\n");
}

int ServiceMelodii::nr_melodii_cu_rank_dat(int rank) {
    int cont = 0;
    for (const auto& melodie: lista_melodii.get_all_repo())
        if (melodie.get_rank() == rank)
            cont++;
    return cont;
}
