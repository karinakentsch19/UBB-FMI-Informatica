//
// Created by Karina Kentsch on 6/16/2023.
//

#ifndef PRODUSE_PRODUS_H
#define PRODUSE_PRODUS_H
#include "string"

using std::string;

class Produs {
private:
    int id;
    string nume, tip;
    double pret;
public:
    Produs(int id, string nume, string tip, double pret): id{id}, nume{nume}, tip{tip}, pret{pret}{}

    /**
     * Return id
     * @return id
     */
    const int get_id() const;

    /**
     * Return nume
     * @return nume
     */
    const string get_nume() const;

    /**
     * Return tip
     * @return tip
     */
    const string get_tip() const;

    /**
     * Return pret
     * @return pret
     */
    const double get_pret() const;
};


#endif //PRODUSE_PRODUS_H
