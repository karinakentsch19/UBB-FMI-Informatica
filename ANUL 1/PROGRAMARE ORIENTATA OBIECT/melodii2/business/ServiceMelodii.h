//
// Created by Karina Kentsch on 6/15/2023.
//

#ifndef MELODII2_SERVICEMELODII_H
#define MELODII2_SERVICEMELODII_H
#include "../repository/RepoMelodii.h"
#include "../validation/Validator.h"

class ServiceMelodii {
private:
    RepoMelodii lista_melodii;
    Validator validator;

public:
    ServiceMelodii(RepoMelodii lista_repo, Validator validator): lista_melodii{lista_repo}, validator{validator}{}

    /**
     * Returneaza lungimea
     * @return lungime
     */
    const int get_lungime_service() const;

    /**
     * Returneaza lista
     * @return lista
     */
    const vector<Melodie> get_all_service() const;

    /**
     * Adauga o melodie in lista
     * @param titlu string
     * @param artist string
     * @param gen string
     */
    void adauga_melodie_service(string titlu, string artist, string gen);

    /**
     * Sterge o melodie dupa id din lista
     * @param id int
     */
    void sterge_dupa_id_service(int id);

    int numar_melodii_pt_gen_dat(string gen);
};


#endif //MELODII2_SERVICEMELODII_H
