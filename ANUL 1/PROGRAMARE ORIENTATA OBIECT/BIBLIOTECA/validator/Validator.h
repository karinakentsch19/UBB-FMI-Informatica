//
// Created by Karina Kentsch on 3/31/2023.
//

#ifndef LAB6_7_BIBLIOTECA_VALIDATOR_H
#define LAB6_7_BIBLIOTECA_VALIDATOR_H


#include "../domain/carte.h"

class Validator {
public:
    Validator() = default;
    /**
     * Valideaza o carte
     * Titlul, autorul si genul nu pot fi vide
     * Anul aparitiei si id-ul trebuie sa fie un numar natural
     * @param carte Carte
     * @return true - daca atributele cartii sunt corecte
     * @throw ValidationError "Titlu invalid\n", "Autor invalid\n", "Gen invalid\n", "An aparitie invalid\n", "Id invalid\n"
     */
    bool valideaza_carte(const Carte& carte) const;
};


#endif //LAB6_7_BIBLIOTECA_VALIDATOR_H
