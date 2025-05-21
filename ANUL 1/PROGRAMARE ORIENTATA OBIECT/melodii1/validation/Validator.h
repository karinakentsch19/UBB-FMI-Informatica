//
// Created by Karina Kentsch on 6/14/2023.
//

#ifndef MELODII1_VALIDATOR_H
#define MELODII1_VALIDATOR_H
#include "../domain/Melodie.h"

class Validator {
public:
    Validator(){}

    /**
     * Valideaza datele
     * id - natural nenul
     * titlu, artist - nevide
     * rank - intre 0 si 10
     * @param melodie Melodie
     */
    bool valideaza_melodie(const Melodie& melodie) const;
};


#endif //MELODII1_VALIDATOR_H
