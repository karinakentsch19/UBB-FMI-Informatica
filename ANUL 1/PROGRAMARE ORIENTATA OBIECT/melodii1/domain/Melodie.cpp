//
// Created by Karina Kentsch on 6/14/2023.
//

#include "Melodie.h"

const int Melodie::get_id() const {
    return id;
}

const string Melodie::get_titlu() const {
    return titlu;
}

const string Melodie::get_artist() const {
    return artist;
}

const int Melodie::get_rank() const {
    return rank;
}

void Melodie::set_titlu(string titlu_nou) {
    titlu = titlu_nou;
}

void Melodie::set_rank(int rank_nou) {
    rank = rank_nou;
}
