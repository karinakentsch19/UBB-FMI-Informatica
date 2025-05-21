//
// Created by Karina Kentsch on 6/14/2023.
//

#include "RepoMelodii.h"
#include "fstream"
#include "../errors/RepoError.h"
#include <cstring>
#include "algorithm"

using std::sort;

void RepoMelodii::load_from_file() {
    std::ifstream fin(nume_fisier);
    lista_melodii.clear();
    char linie[1000];
    while(fin.getline(linie,1000)){
        char* parametru = strtok(linie, ",");
        int id = atoi(parametru);

        parametru = strtok(NULL, ",");
        string titlu = parametru;

        parametru = strtok(NULL, ",");
        string artist = parametru;

        parametru = strtok(NULL, ",");
        int rank = atoi(parametru);

        Melodie melodie{id, titlu, artist, rank};
        lista_melodii.push_back(melodie);
    }
    fin.close();
}

void RepoMelodii::store_to_file(){
    std::ofstream fout(nume_fisier);
    sort(lista_melodii.begin(), lista_melodii.end(), [](const Melodie& m1, const Melodie& m2){
        return m1.get_rank() < m2.get_rank();
    });
    for (const auto& melodie: lista_melodii)
        fout << melodie.get_id() << "," << melodie.get_titlu() << "," << melodie.get_artist() << "," << melodie.get_rank() << "\n";
    fout.close();
}

const int RepoMelodii::get_lungime_repo() const {
    return lista_melodii.size();
}

const vector<Melodie> RepoMelodii::get_all_repo() const {
    return lista_melodii;
}

void RepoMelodii::adauga_melodie_repo(Melodie melodie) {
    for (const auto& melodie1: lista_melodii)
        if (melodie1.get_id() == melodie.get_id())
            throw RepoError("Melodie existenta\n");
    lista_melodii.push_back(melodie);
    store_to_file();
}

void RepoMelodii::sterge_melodie_dupa_id_repo(int id) {
    auto iterator = lista_melodii.begin();
    while(iterator != lista_melodii.end() && iterator->get_id() != id)
        iterator++;
    if (iterator == lista_melodii.end())
        throw RepoError("Melodie inexistenta\n");
    else
        lista_melodii.erase(iterator);
    store_to_file();
}

void RepoMelodii::modifica_melodie_repo(Melodie melodie_noua) {
    for (auto& melodie: lista_melodii)
        if (melodie.get_id() == melodie_noua.get_id()){
            melodie.set_titlu(melodie_noua.get_titlu());
            melodie.set_rank(melodie_noua.get_rank());
            store_to_file();
            return;
        }
    throw RepoError("Melodie inexistenta\n");
}
