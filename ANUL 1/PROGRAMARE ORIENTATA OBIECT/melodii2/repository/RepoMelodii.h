//
// Created by Karina Kentsch on 6/15/2023.
//

#ifndef MELODII2_REPOMELODII_H
#define MELODII2_REPOMELODII_H
#include "vector"
#include "../domain/Melodie.h"

using std::vector;

class RepoMelodii {
private:
    vector <Melodie> lista_melodii;
    string nume_fisier;

    /**
     * Descarca datele din fisier
     */
    void load_from_file();

    /**
     * Incarca datele listei in fisier
     */
    void store_to_file();

public:
    RepoMelodii(string nume_fisier): nume_fisier{nume_fisier}{
        load_from_file();
    }

    /**
     * Returneaza lungimea listei
     * @return lungime lista
     */
    const int get_lungime_repo() const;

    /**
     * Returneaza lista
     * @return lista de melodii
     */
    const vector<Melodie> get_all_repo() const;

    /**
     * Adauga o melodie in lista
     * @param melodie Melodie
     */
    void adauga_melodie_repo(Melodie melodie);

    /**
     * Sterge o melodie din lista dupa id
     * @param id int
     */
    void sterge_melodie_dupa_id_repo(int id);

};


#endif //MELODII2_REPOMELODII_H
