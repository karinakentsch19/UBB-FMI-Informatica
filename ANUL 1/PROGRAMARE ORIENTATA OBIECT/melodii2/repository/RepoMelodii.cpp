//
// Created by Karina Kentsch on 6/15/2023.
//

#include "RepoMelodii.h"
#include "fstream"
#include "cstring"
#include "../errors/RepoError.h"
#include "algorithm"

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
        string gen = parametru;

        Melodie melodie{id, titlu, artist, gen};
        lista_melodii.push_back(melodie);
    }
    fin.close();
}

void RepoMelodii::store_to_file() {
    std::ofstream fout(nume_fisier);
    sort(lista_melodii.begin(), lista_melodii.end(), [](Melodie m1, Melodie m2){
        return m1.get_artist() < m2.get_artist();
    });
    for (const auto& melodie: lista_melodii)
        fout << melodie.get_id() << "," << melodie.get_titlu() << "," << melodie.get_artist() << "," << melodie.get_gen() << "\n";
    fout.close();
}

const int RepoMelodii::get_lungime_repo() const {
    return lista_melodii.size();
}

const vector<Melodie> RepoMelodii::get_all_repo() const {
    return lista_melodii;
}

void RepoMelodii::adauga_melodie_repo(Melodie melodie) {
    for (const auto& m: lista_melodii)
        if (m.get_id() == melodie.get_id())
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
