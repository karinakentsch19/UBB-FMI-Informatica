//
// Created by Karina Kentsch on 3/31/2023.
//

#include "carte.h"

#include <utility>

Carte::Carte(string titlu, string gen, string autor, int an_aparitie, int id) {
    this->titlu = std::move(titlu);
    this->gen = std::move(gen);
    this->autor = std::move(autor);
    this->an_aparitie = an_aparitie;
    this->id = id;
}

int Carte::get_id() const {
    return this->id;
}

string Carte::get_titlu() const {
    return this->titlu;
}

string Carte::get_gen() const {
    return this->gen;
}

string Carte::get_autor() const {
    return this->autor;
}

int Carte::get_an_aparitie() const {
    return this->an_aparitie;
}

void Carte::set_titlu(string titlu_nou) {
    this->titlu = std::move(titlu_nou);
}

void Carte::set_gen(string gen_nou) {
    this->gen = std::move(gen_nou);
}

void Carte::set_autor(string autor_nou) {
    this->autor = std::move(autor_nou);
}

void Carte::set_an_aparitie(int an_aparitie_nou) {
    this->an_aparitie = an_aparitie_nou;
}

bool Carte::operator==(const Carte &c2) const {
    if (this->id == c2.id)
        return true;
    return false;
}

void Carte::operator=(const Carte &c) {
    this->id = c.get_id();
    this->titlu = c.get_titlu();
    this->autor = c.get_autor();
    this->gen = c.get_gen();
    this->an_aparitie = c.get_an_aparitie();
}


