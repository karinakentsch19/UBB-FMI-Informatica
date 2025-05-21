//
// Created by Karina Kentsch on 6/16/2023.
//

#include "ServiceProdus.h"

const int ServiceProdus::get_lungime_service() const {
    return lista_produse.get_lungime_repo();
}

const vector<Produs> ServiceProdus::get_all_service() const {
    return lista_produse.get_all_repo();
}

void ServiceProdus::adauga_produs_service(int id, string nume, string tip, double pret) {
    Produs produs{id, nume, tip, pret};
    if (validator.valideaza_produs(produs))
        lista_produse.adauga_produs_repo(produs);
}

void ServiceProdus::sterge_dupa_id_service(int id) {
    lista_produse.sterge_produs_dupa_id_repo(id);
}

vector<Produs> ServiceProdus::filtru_produse_pret_mai_mic_egal_cu_pret_dat(double pret) {
    vector<Produs> lista_filtrata;
    for (const auto& produs: lista_produse.get_all_repo())
        if (produs.get_pret() <= pret)
            lista_filtrata.push_back(produs);
    return lista_filtrata;
}
