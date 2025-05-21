//
// Created by Karina Kentsch on 6/16/2023.
//

#ifndef PRODUSE_REPOPRODUS_H
#define PRODUSE_REPOPRODUS_H
#include "../domain/Produs.h"
#include "vector"

using std::vector;

class RepoProdus {
private:
    vector<Produs> lista_produse;
    string nume_fisier;

    /**
     * Descarca din fisier
     */
    void load_from_file();

    /**
     * Incarca in fisier continutul listei
     */
    void store_to_file();

public:
    RepoProdus(string nume_fisier): nume_fisier{nume_fisier}{
        load_from_file();
    }

    /**
     * Return lungime
     * @return lungime lista
     */
    const int get_lungime_repo() const;

    /**
     * Return lista
     * @return lista
     */
    const vector<Produs> get_all_repo() const;

    /**
     * Adauga un produs in lista
     * @param produs Produs
     */
    void adauga_produs_repo(Produs produs);

    /**
     * Sterge un produs din lista dupa id
     * @param id int
     */
    void sterge_produs_dupa_id_repo(int id);
};


#endif //PRODUSE_REPOPRODUS_H
