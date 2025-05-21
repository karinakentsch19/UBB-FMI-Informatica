//
// Created by Karina Kentsch on 4/28/2023.
//

#include "RepositoryBibliotecaFile.h"
#include "fstream"
#include "stdio.h"
#include "sstream"

using std::ofstream;
using std::ifstream;
using std::remove;
using std::getline;
using std::stringstream;

void RepositoryBibliotecaFile::store_to_file() const {
    remove(this->file.c_str());
    ofstream fout(this->file);
    for(auto& carte:RepositoryBiblioteca::get_all_repo())
        fout << carte.get_id() << "," << carte.get_titlu() << "," << carte.get_autor() << "," << carte.get_gen() << "," << carte.get_an_aparitie() << "\n";
    fout.close();
}

void RepositoryBibliotecaFile::load_from_file() {
    ifstream fin(this->file);
    string linie;
    while(getline(fin, linie)){
        int id, an_aparitie;
        string titlu, autor, gen;
        string parametru;

        stringstream string_in(linie);

        getline(string_in, parametru, ',');
        id = stoi(parametru);

        getline(string_in, parametru, ',');
        titlu = parametru;

        getline(string_in, parametru, ',');
        autor = parametru;

        getline(string_in, parametru, ',');
        gen = parametru;

        getline(string_in, parametru, ',');
        an_aparitie = stoi(parametru);

        Carte carte{titlu, gen, autor, an_aparitie, id};
        RepositoryBiblioteca::adauga_carte_repo(carte);
    }
    fin.close();
}

void RepositoryBibliotecaFile::adauga_carte_repo(const Carte &carte) {
    RepositoryBiblioteca::adauga_carte_repo(carte);
    store_to_file();
}

void RepositoryBibliotecaFile::sterge_carte_dupa_id_repo(int id) {
    RepositoryBiblioteca::sterge_carte_dupa_id_repo(id);
    store_to_file();
}

void RepositoryBibliotecaFile::modificare_carte_repo(const Carte &carte_noua) {
    RepositoryBiblioteca::modificare_carte_repo(carte_noua);
    store_to_file();
}
