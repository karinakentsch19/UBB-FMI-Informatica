//
// Created by Karina Kentsch on 5/4/2023.
//

#ifndef LAB6_7_BIBLIOTECA_BASEREPO_H
#define LAB6_7_BIBLIOTECA_BASEREPO_H
#include "../domain/carte.h"
#include "vector"
using std::vector;

class BaseRepo {
public:
    virtual void adauga_carte_repo(const Carte& carte) = 0;
    virtual void sterge_carte_dupa_id_repo(int id) = 0;
    virtual void modificare_carte_repo(const Carte& carte_noua) = 0;
    virtual const Carte& cauta_carte_dupa_id_repo(int id) const = 0;
    virtual const vector <Carte>& get_all_repo() const = 0;
    virtual int get_lungime_lista_repo() const = 0;
    virtual ~BaseRepo() = default;
};


#endif //LAB6_7_BIBLIOTECA_BASEREPO_H
