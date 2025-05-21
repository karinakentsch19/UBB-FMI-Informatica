//
// Created by Karina Kentsch on 6/16/2023.
//

#ifndef PRODUSE_SERVICEPRODUS_H
#define PRODUSE_SERVICEPRODUS_H
#include "../repository/RepoProdus.h"
#include "../validation/Validator.h"

class ServiceProdus {
private:
    RepoProdus lista_produse;
    Validator validator;
public:
    ServiceProdus(RepoProdus lista_repo, Validator validator): lista_produse{lista_repo}, validator{validator}{}

    /**
     * Returneaza lugnimea listei
     * @return lungime
     */
    const int get_lungime_service() const;

    /**
     * Returneaza lista de produse
     * @return lista
     */
    const vector<Produs> get_all_service() const;

    /**
     * Adauga un produs in lista
     * @param id int
     * @param nume string
     * @param tip string
     * @param pret double
     */
    void adauga_produs_service(int id, string nume, string tip, double pret);

    /**
     * Sterge dupa id un produs
     * @param id int
     */
    void sterge_dupa_id_service(int id);

    /**
     * Returneaza un vector de produse care au pretul mai mic egal cu pretul dat
     * @param pret double
     * @return vector produse
     */
    vector<Produs>  filtru_produse_pret_mai_mic_egal_cu_pret_dat(double pret);
};


#endif //PRODUSE_SERVICEPRODUS_H
