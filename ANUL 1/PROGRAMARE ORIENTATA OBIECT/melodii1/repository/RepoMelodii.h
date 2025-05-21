//
// Created by Karina Kentsch on 6/14/2023.
//

#ifndef MELODII1_REPOMELODII_H
#define MELODII1_REPOMELODII_H
#include "../domain/Melodie.h"
#include "vector"

using std::vector;

class RepoMelodii {
private:
    vector <Melodie> lista_melodii;
    string nume_fisier;
    /**
     * Descarca din fisier
     */
    void load_from_file();

    /**
     * Incarca in fisier
     */
    void store_to_file();

public:
    RepoMelodii(string nume_fisier): nume_fisier{nume_fisier}{
        load_from_file();
    }

    /**
     * Return lungime lista
     * @return lungime
     */
    const int get_lungime_repo() const;

    /**
     * Returneaza lista
     * @return lista
     */
    const vector<Melodie> get_all_repo() const;

    /**
     * Adauga o melodie
     * @param melodie Melodie
     */
    void adauga_melodie_repo(Melodie melodie);

    /**
     * Sterge o melodie cu id ul dat
     * @param id int
     */
    void sterge_melodie_dupa_id_repo(int id);

    /**
     * Modifica titlul si rankul melodiei cu acelasi id ca melodia noua
     * @param melodie_noua Melodie
     */
    void modifica_melodie_repo(Melodie melodie_noua);
};


#endif //MELODII1_REPOMELODII_H
