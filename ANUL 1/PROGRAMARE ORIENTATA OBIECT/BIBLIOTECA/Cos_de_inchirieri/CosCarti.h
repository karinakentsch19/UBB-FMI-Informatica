//
// Created by Karina Kentsch on 4/22/2023.
//

#ifndef LAB6_7_BIBLIOTECA_COSCARTI_H
#define LAB6_7_BIBLIOTECA_COSCARTI_H
#include "vector"
#include "../domain/carte.h"

using std::vector;

class CosCarti {
private:
    vector <Carte> cos_inchirieri;
public:

    /**
     * Adauga o carte in cosul de inchirieri
     * @param carte Carte
     */
    void adauga_cos(const Carte& carte);

    /**
     * Sterge toate cartile din cosul de inchirieri
     */
    void goleste_cos();

    /**
     * Introduce in cos un numar de carti aleatorii din lista data
     * @param lista vector <Carte>
     * @param numar_carti int
     */
    void genereaza_cos(const vector <Carte>& lista, int numar_carti);

    /**
     * Returneaza cosul de inchirieri
     * @return cos
     */
    const vector <Carte>& get_cos() const;

    /**
     * Returneaza numarul de carti din cos
     * @return size cos
     */
    const int size_cos() const;
};


#endif //LAB6_7_BIBLIOTECA_COSCARTI_H
