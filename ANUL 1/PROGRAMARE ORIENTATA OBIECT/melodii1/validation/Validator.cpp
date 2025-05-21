//
// Created by Karina Kentsch on 6/14/2023.
//

#include "Validator.h"
#include "../errors/ValidationError.h"

bool Validator::valideaza_melodie(const Melodie& melodie) const {
    string erori;
    if (melodie.get_id() < 0)
        erori += "Id invalid\n";

    if (melodie.get_titlu().empty())
        erori += "Titlu invalid\n";

    if (melodie.get_artist().empty())
        erori += "Artist invalid\n";

    if (melodie.get_rank() < 0 || melodie.get_rank() > 10)
        erori += "Rank invalid\n";

    if (!erori.empty())
        throw ValidationError(erori);
    return true;
}
