//
// Created by Karina Kentsch on 6/14/2023.
//

#ifndef MELODII1_SERVICEMELODII_H
#define MELODII1_SERVICEMELODII_H
#include "../repository/RepoMelodii.h"
#include "../validation/Validator.h"

class ServiceMelodii {
private:
    RepoMelodii lista_melodii;
    Validator validator;

public:
    ServiceMelodii(RepoMelodii lista_repo, Validator validator): lista_melodii{lista_repo}, validator{validator}{}

    /**
     * Return lungime
     * @return lungime
     */
    const int get_lungime_service() const;

    /**
     * return lista
     * @return lista
     */
    const vector<Melodie> get_all_service() const;

    /**
     * adauga o melodie in lista
     * @param id int
     * @param titlu string
     * @param artist string
     * @param rank int
     */
    void adauga_melodie_service(int id, string titlu, string artist, int rank);

    /**
     * modifica melodia cu id ul dat
     * @param id int
     * @param titlu_nou string
     * @param rank string
     */
    void modifica_melodie_service(int id, string titlu_nou, int rank_nou);

    /**
     * Sterge o melodie dupa id
     * Daca melodia cu id dat este ultima melodie a artistului se arunca exceptie si nu se va sterge melodia
     * @param id int
     */
    void sterge_dupa_id_service(int id);

    /**
     * Returneaza numarul de melodii care au rank dat
     * @param rank int
     * @return contor
     */
    int nr_melodii_cu_rank_dat(int rank);
};


#endif //MELODII1_SERVICEMELODII_H
