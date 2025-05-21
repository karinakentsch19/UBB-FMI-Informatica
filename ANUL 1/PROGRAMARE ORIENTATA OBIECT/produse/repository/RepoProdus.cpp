//
// Created by Karina Kentsch on 6/16/2023.
//

#include "RepoProdus.h"
#include "fstream"
#include "algorithm"
#include "cstring"
#include "../errors/RepoError.h"

using std::sort;

void RepoProdus::load_from_file() {
    std::ifstream fin(nume_fisier);
    lista_produse.clear();
    char linie[1000];
    while(fin.getline(linie, 1000)){
        char* parametru = strtok(linie, ",");
        int id = atoi(parametru);

        parametru = strtok(NULL, ",");
        string nume = parametru;

        parametru = strtok(NULL, ",");
        string tip = parametru;

        parametru = strtok(NULL, ",");
        double pret = atof(parametru);

        Produs produs{id, nume, tip, pret};
        lista_produse.push_back(produs);
    }
    fin.close();
}

void RepoProdus::store_to_file() {
    std::ofstream fout(nume_fisier);
    sort(lista_produse.begin(), lista_produse.end(), [](const Produs& p1, const Produs& p2){
        return p1.get_pret() < p2.get_pret();
    });
    for (const auto& produs: lista_produse)
        fout << produs.get_id() << "," << produs.get_nume() << "," << produs.get_tip() << "," << produs.get_pret() << "\n";
    fout.close();
}

const int RepoProdus::get_lungime_repo() const {
    return lista_produse.size();
}

const vector<Produs> RepoProdus::get_all_repo() const {
    return lista_produse;
}

void RepoProdus::adauga_produs_repo(Produs produs) {
    for (const auto& p: lista_produse)
        if (p.get_id() == produs.get_id())
            throw RepoError("Produs existent\n");
    lista_produse.push_back(produs);
    store_to_file();
}

void RepoProdus::sterge_produs_dupa_id_repo(int id) {
    auto iterator = lista_produse.begin();
    while(iterator != lista_produse.end() && iterator->get_id() != id)
        iterator++;
    if (iterator == lista_produse.end())
        throw RepoError("Produs inexistent\n");
    else
        lista_produse.erase(iterator);
    store_to_file();
}
