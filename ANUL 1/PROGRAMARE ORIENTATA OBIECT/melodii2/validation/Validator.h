//
// Created by Karina Kentsch on 6/15/2023.
//

#ifndef MELODII2_VALIDATOR_H
#define MELODII2_VALIDATOR_H
#include "../domain/Melodie.h"
#include "../errors/ValidationError.h"

class Validator{
public:
    Validator(){}

    /**
     * Valideaza o melodie
     * id strict pozitiv
     * titlu, artist nevide
     * gen trebuie sa fie unul din : pop, rock, folk, disco
     * @param melodie Meldoie
     * @return true
     * Arunca exceptie daca exista erori
     */
    bool valideaza_melodie(Melodie melodie) const{
        string erori;
        if (melodie.get_id() <= 0)
            erori += "Id invalid\n";
        if (melodie.get_titlu().empty())
            erori += "Titlu invalid\n";
        if (melodie.get_artist().empty())
            erori += "Artist invalid\n";
        if (melodie.get_gen() != "pop" && melodie.get_gen() != "rock" && melodie.get_gen() != "folk" && melodie.get_gen() != "disco")
            erori += "Gen invalid\n";
        if (!erori.empty())
            throw ValidationError(erori);
        return true;
    }
};

#endif //MELODII2_VALIDATOR_H
