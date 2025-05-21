//
// Created by Karina Kentsch on 3/31/2023.
//

#include "Validator.h"
#include "../errors/ValidationError.h"

bool Validator::valideaza_carte(const Carte& carte) const {
    string erori;
    if (carte.get_titlu() == "")
        erori += "Titlu invalid\n";
    if (carte.get_gen() == "")
        erori += "Gen invalid\n";
    if (carte.get_autor() == "")
        erori += "Autor invalid\n";
    if (carte.get_an_aparitie() < 0)
        erori += "An aparitie invalid\n";
    if (carte.get_id() < 0)
        erori += "Id invalid\n";

    if (erori.size() == 0)
        return true;
    else
        throw ValidationError(erori);
}
