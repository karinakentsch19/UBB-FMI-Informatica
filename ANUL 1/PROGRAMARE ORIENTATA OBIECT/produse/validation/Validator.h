//
// Created by Karina Kentsch on 6/16/2023.
//

#ifndef PRODUSE_VALIDATOR_H
#define PRODUSE_VALIDATOR_H
#include "../domain/Produs.h"
#include "../errors/ValidationError.h"

class Validator{
public:
    Validator(){}

    /**
     * Valideaza un pruds
     * id strict pozitiv
     * nume si tip nevide
     * pret intre 1.0 si 100.0
     * @param produs Produs
     * @return true sau arunca exceptie daca exista erori
     */
    bool valideaza_produs(Produs produs){
        string erori;
        if (produs.get_id() <= 0)
            erori += "Id invalid\n";
        if (produs.get_nume().empty())
            erori += "Nume invalid\n";
        if (produs.get_tip().empty())
            erori += "Tip invalid\n";
        if (produs.get_pret() <= 1.0 || produs.get_pret() >= 100.0)
            erori += "Pret invalid\n";
        if (!erori.empty())
            throw ValidationError(erori);
        return true;
    }
};

#endif //PRODUSE_VALIDATOR_H
